<template>
  <div class="aui-wrapper aui-page__login">
    <div class="aui-content__wrapper">
      <main class="aui-content">
        <div class="login-header">
          <h2 class="login-brand">{{ $t('brand.full') }}</h2>
        </div>
        <div class="login-body">
          <h3 class="login-title">忘记密码</h3>
          <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" status-icon @keyup.enter.native="dataFormSubmitHandle()">
              <el-form-item prop="mobile">
                <el-input v-model="dataForm.mobile" placeholder="手机号" prefix-icon="el-icon-mobile-phone" maxlength="11" minlength="11" class="input-with-select">
                  <el-select v-model="dataForm.mobileArea" slot="prepend">
                    <el-option value="86" label="86"/>
                  </el-select>
                </el-input>
              </el-form-item>
              <el-form-item prop="sms">
                <el-row :gutter="20">
                  <el-col :span="14">
                    <el-input v-model="dataForm.smsCode" placeholder="短信验证码" prefix-icon="el-icon-message" maxlength="6" minlength="4"/>
                  </el-col>
                  <el-col :span="10" class="login-captcha">
                    <el-button type="primary" @click="smsCodeSendHandle()" class="w-percent-100" :disabled="smsSendTimeout < 60">{{ smsSendTimeout !== 60 ? smsSendTimeout + '秒后重发' : '发送验证码' }}</el-button>
                  </el-col>
                </el-row>
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
                    <el-image :src="captcha.image" @click="getCaptcha()"><div slot="placeholder" class="image-slot"><i class="el-icon-loading"/></div></el-image>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">{{ $t('login.title') }}</el-button>
              </el-form-item>
          </el-form>
          <div>
            <el-link :underline="false" type="info" style="float: left;">注册</el-link>
            <el-link :underline="false" type="info" style="float: right;">已有帐号登录</el-link>
          </div>
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
        dataFormParamEncrypt: false,
        dataFormSaveURL: '/auth/login'
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
        mobile: '',
        mobileArea: '86',
        code: '',
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
        ],
        smsCode: [
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
        this.$http.post(`/auth/sendSmsCode`, { 'mobile': this.dataForm.mobile, 'tplCode': 'LOGIN' }).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.code + ':' + res.msg)
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
      if (this.loginConfig.captcha) {
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

<style>
  .el-select .el-input {
    width: 100px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>
