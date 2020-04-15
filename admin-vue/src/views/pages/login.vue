<template>
  <div class="aui-wrapper aui-page__login">
    <div class="aui-content__wrapper">
      <main class="aui-content">
        <div class="login-header">
          <h2 class="login-brand">{{ sysCfg.loginTitle }}</h2>
        </div>
        <div class="login-body">
          <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" status-icon :validate-on-rule-change="false" @keyup.enter.native="dataFormSubmitHandle()">
            <el-form-item>
              <el-radio-group v-model="dataForm.type" size="small" @change="typeChangeHandle">
                <el-radio-button v-for="item in loginCfg.channels" :key="item.type" :label="item.type" :value="item.type" v-if="item.enable">{{ item.title }}</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <!-- 帐号密码登录 -->
            <template v-if="dataForm.type === 10">
              <el-form-item prop="username">
                <el-input v-model="dataForm.username" prefix-icon="el-icon-user" :placeholder="$t('login.username')"/>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="dataForm.password" prefix-icon="el-icon-lock" :placeholder="$t('login.password')" show-password/>
              </el-form-item>
              <el-form-item prop="captcha" v-if="loginChannelCfg.captcha">
                <el-input v-model="dataForm.captcha" prefix-icon="el-icon-c-scale-to-original" :placeholder="$t('login.captcha')" maxlength="8">
                  <el-tooltip slot="append" effect="dark" content="点击刷新图形验证码" placement="right">
                    <el-image :src="captchaImage" @click="getCaptcha()" style="width: 90px;">
                      <div slot="placeholder" class="image-slot"><i class="el-icon-loading"/></div>
                    </el-image>
                  </el-tooltip>
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">{{ $t('login.title') }}</el-button>
              </el-form-item>
            </template>
            <!-- 手机号登录 -->
            <template v-else-if="dataForm.type === 30">
              <el-form-item prop="mobile">
                <el-input v-model="dataForm.mobile" placeholder="手机号" prefix-icon="el-icon-mobile-phone" maxlength="11" minlength="11" class="input-with-select">
                  <template slot="prepend">+86</template>
                </el-input>
              </el-form-item>
              <el-form-item prop="smsCode">
                <el-input v-model="dataForm.smsCode" placeholder="短信验证码" prefix-icon="el-icon-message" maxlength="6" minlength="4">
                  <el-button slot="append" @click="smsCodeSendHandle()" :disabled="smsSendTimeout < 60">{{ smsSendTimeout !== 60 ? smsSendTimeout + '秒后重发' : '发送验证码' }}</el-button>
                </el-input>
              </el-form-item>
              <el-form-item prop="captcha" v-if="loginChannelCfg.captcha">
                <el-input v-model="dataForm.captcha" prefix-icon="el-icon-c-scale-to-original" :placeholder="$t('login.captcha')">
                  <el-tooltip slot="append" effect="dark" content="点击刷新图形验证码" placement="right">
                    <el-image :src="captchaImage" @click="getCaptcha()" style="width: 90px;">
                      <div slot="placeholder" class="image-slot"><i class="el-icon-loading"/></div>
                    </el-image>
                  </el-tooltip>
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">{{ $t('login.title') }}</el-button>
              </el-form-item>
            </template>
            <!-- 微信登录 -->
            <template v-else-if="dataForm.type === 40">
              未开放
            </template>
          </el-form>
          <div>
            <router-link :to="{ name: 'register' }" v-if="loginCfg.register">
              <el-link :underline="false" type="info" style="float: left;">{{ $t('register') }}</el-link>
            </router-link>
            <router-link :to="{ name: 'forgetPassword' }" v-if="loginCfg.forgetPassword">
              <el-link :underline="false" type="info" style="float: right;">{{ $t('forgetPassword') }}</el-link>
            </router-link>
          </div>
        </div>
        <div class="login-footer" v-html="sysCfg.copyright"/>
      </main>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import mixinFormModule from '@/mixins/form-module'

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        // 登录接口
        dataFormParamEncrypt: false,
        dataFormSaveURL: '/auth/login'
      },
      dataFormMode: 'save',
      // 系统配置
      sysCfg: {},
      // 全局登录配置
      loginCfg: {
        forgetPassword: false,
        register: false
      },
      // 当前登录渠道配置
      loginChannelCfg: {
        captcha: false
      },
      // 短信发送倒计时
      smsSendTimeout: 60,
      // 验证码图片
      captchaImage: '',
      dataForm: {
        username: '',
        password: '',
        mobile: '',
        mobileArea: '86',
        smsCode: '',
        uuid: '',
        captcha: '',
        // 登录类型
        type: 0
      }
    }
  },
  computed: {
    dataRule () {
      return {
        username: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        password: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        captcha: [
          { required: this.loginChannelCfg.captcha, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        smsCode: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    // 获取配置项
    this.getParamCfg()
  },
  methods: {
    // 切换登录类型
    typeChangeHandle () {
      // 赋值当前渠道配置
      this.loginChannelCfg = this.loginCfg.channels.filter(item => item.type === this.dataForm.type)[0].cfg
      // 清空校验
      this.$refs['dataForm'].clearValidate()
    },
    // 获取系统配置
    getParamCfg () {
      this.$http.get(`/sys/param/getContentByCodes?codes=SYS_CFG,LOGIN_CFG_ADMIN`).then(({ data: res }) => {
        this.formLoading = false
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        } else {
          // 复制显示配置
          this.sysCfg = res.data.SYS_CFG
          Cookies.set('title', this.sysCfg.title)
          Cookies.set('titleAbbr', this.sysCfg.titleAbbr)
          document.title = this.sysCfg.title
          // 赋值全局登录配置
          this.loginCfg = res.data.LOGIN_CFG_ADMIN
          // 找到第一个enable的登录渠道
          this.loginChannelCfg = this.loginCfg.channels.filter(item => item.enable)[0].cfg
          // 赋值类型
          this.dataForm.type = this.loginChannelCfg.type
          // 获取验证码
          if (this.loginChannelCfg.captcha) {
            this.getCaptcha()
          }
        }
      }).catch(resp => {
        this.formLoading = false
      })
    },
    // 获取验证码
    getCaptcha () {
      this.$http.get(`/captcha/base64?width=110&height=40`).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        } else {
          this.captchaImage = res.data.image
          this.dataForm.uuid = res.data.uuid
        }
      })
    },
    // 发送短信验证码
    smsCodeSendHandle () {
      if (this.smsSendTimeout < 60) {
        return
      }
      this.formLoading = true
      this.$refs['dataForm'].validateField('mobile', (errorMessage) => {
        if (errorMessage) {
          this.formLoading = false
          return false
        }
        this.$http.post(`/auth/sendSmsCode`, { 'mobile': this.dataForm.mobile, 'tplCode': 'CODE_LOGIN' }).then(({ data: res }) => {
          if (res.code !== 0) {
            this.$message.error(res.toast)
          } else {
            this.$message.success('短信发送成功')
            // 开始倒计时
            this.smsSendTimeout = 60
            const timer = window.setInterval(() => {
              if (this.smsSendTimeout-- <= 0) {
                this.smsSendTimeout = 60
                window.clearInterval(timer)
              }
            }, 1000)
          }
          this.formLoading = false
        }).catch(resp => {
          this.formLoading = false
        })
      })
    },
    // 表单提交失败
    onFormSubmitError (res) {
      // 刷新验证码
      if (this.loginChannelCfg.captcha) {
        this.getCaptcha()
      }
      this.$message.error(res.toast)
    },
    // 表单提交成功
    onFormSubmitSuccess (res) {
      // 将token保存到cookie
      Cookies.set('token', res.data.token)
      // 跳转到home
      this.$router.replace({ name: 'home' })
    }
  }
}
</script>
