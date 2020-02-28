<template>
  <div class="aui-wrapper aui-page__login">
    <div class="aui-content__wrapper">
      <main class="aui-content">
        <div class="login-header">
          <h2 class="login-brand">{{ $t('brand.full') }}</h2>
        </div>
        <div class="login-body">
          <!--<h3 class="login-title">{{ $t('login.title') }}</h3>-->
          <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" status-icon @keyup.enter.native="dataFormSubmitHandle()">
            <el-form-item>
              <el-radio-group v-model="dataForm.type" size="small" @change="typeChangeHandle">
                <el-radio-button :label="10">帐号密码登录</el-radio-button>
                <el-radio-button :label="30">手机号登录</el-radio-button>
                <el-radio-button :label="40">微信登录</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <!-- 帐号密码登录 -->
            <template v-if="dataForm.type === 10">
              <el-form-item prop="username">
                <el-input v-model="dataForm.username" prefix-icon="el-icon-user" :placeholder="$t('login.username')"/>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="dataForm.password" type="password" prefix-icon="el-icon-lock" :placeholder="$t('login.password')"/>
              </el-form-item>
              <el-form-item prop="captcha" v-if="loginConfig.captcha">
                <el-row :gutter="20">
                  <el-col :span="14">
                    <el-input v-model="dataForm.captcha" prefix-icon="el-icon-c-scale-to-original" :placeholder="$t('login.captcha')">
                    </el-input>
                  </el-col>
                  <el-col :span="10" class="login-captcha">
                    <el-image :src="captcha.image" @click="getCaptcha()" ><div slot="placeholder" class="image-slot"><i class="el-icon-loading"/></div></el-image>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">{{ $t('login.title') }}</el-button>
              </el-form-item>
            </template>
            <!-- 手机号登录 -->
            <template v-else-if="dataForm.type === 30">
              <el-form-item prop="mobile">
                <el-input v-model="dataForm.mobile" placeholder="手机号" prefix-icon="el-icon-mobile-phone" maxlength="11" minlength="11"/>
              </el-form-item>
              <el-form-item prop="sms">
                <el-row :gutter="20">
                  <el-col :span="14">
                    <el-input v-model="dataForm.sms" placeholder="短信验证码" prefix-icon="el-icon-message" maxlength="6" minlength="4"/>
                  </el-col>
                  <el-col :span="10" class="login-captcha">
                    <el-button type="primary" @click="smsSendHandle()" class="w-percent-100" :disabled="smsSendTimeout < 60">{{ smsSendTimeout !== 60 ? smsSendTimeout + '秒' : '发送验证码' }}</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item prop="captcha" v-if="loginConfig.captcha">
                <el-row :gutter="20">
                  <el-col :span="14">
                    <el-input v-model="dataForm.captcha" prefix-icon="el-icon-c-scale-to-original" :placeholder="$t('login.captcha')">
                    </el-input>
                  </el-col>
                  <el-col :span="10" class="login-captcha">
                    <el-image :src="captcha.image" @click="getCaptcha()"><div slot="placeholder" class="image-slot"><i class="el-icon-loading"/></div></el-image>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">{{ $t('login.title') }}</el-button>
              </el-form-item>
            </template>
            <!-- 微信登录 -->
            <template v-else-if="dataForm.type === 40">
            </template>
          </el-form>
          <div>
            <el-link :underline="false" type="info" style="float: left;">注册</el-link>
            <el-link :underline="false" type="info" style="float: right;">忘记密码</el-link>
          </div>
        </div>
        <div class="login-footer">
          <p>{{ $t('login.copyright') }}<el-link href="#" :underline="false" target="_blank" type="info">{{ $t('brand.owner') }}</el-link></p>
        </div>
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
        dataFormParamEncrypt: true,
        dataFormSaveURL: '/auth/loginEncrypt'
      },
      dataFormMode: 'save',
      // 登录配置,从接口获取配置
      loginConfig: {
        captcha: false
      },
      // 短信发送倒计时
      smsSendTimeout: 60,
      // 验证码信息
      captcha: {
        uuid: '',
        image: ''
      },
      dataForm: {
        username: '',
        password: '',
        uuid: '',
        captcha: '',
        // 登录类型
        type: 10
      }
    }
  },
  computed: {
    // 有验证码时候的验证
    dataRule () {
      return {
        username: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        password: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        captcha: [
          { required: this.loginConfig.captcha, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    // 获取登录配置
    this.getLoginConfig()
  },
  methods: {
    // 登录类型变化
    typeChangeHandle () {
      // 重新获取登录配置信息
      this.getLoginConfig()
      // 清空校验
      this.$refs['dataForm'].clearValidate()
    },
    // 获取登录配置
    getLoginConfig () {
      this.$http.get(`/auth/loginConfig?type=${this.dataForm.type}`).then(({ data: res }) => {
        this.formLoading = false
        if (res.code !== 0) {
          return this.$message.error(res.code + ':' + res.msg)
        } else {
          this.loginConfig = res.data
          if (this.loginConfig.captcha) {
            this.getCaptcha()
          }
        }
      }).catch(resp => {
        this.formLoading = false
      })
    },
    // 获取验证码
    getCaptcha () {
      this.$http.get(`/auth/captcha`).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.code + ':' + res.msg)
        } else {
          this.captcha = res.data
          this.dataForm.uuid = res.data.uuid
        }
      })
    },
    // 发送短信验证码
    smsSendHandle () {
      if (this.smsSendTimeout < 60) {
        return
      }
      this.formLoading = true
      this.$refs['dataForm'].validateField('mobile', (errorMessage) => {
        if (errorMessage) {
          this.formLoading = false
          return false
        }
        this.$http.post(`/auth/sendLoginSms`, { 'mobile': this.dataForm.mobile, 'tplCode': 'LOGIN' }).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.code + ':' + res.msg)
          } else {
            this.$message.success('短信发送成功')
            // 开始倒计时
            const time = setInterval(() => {
              this.smsSendTimeout--
              if (this.smsSendTimeout <= 0) {
                this.smsSendTimeout = 60
                clearInterval(time)
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
      if (this.captchaEnable) {
        this.getCaptcha()
      }
      this.$message.error(res.msg)
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
