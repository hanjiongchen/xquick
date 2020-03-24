<template>
  <div class="aui-wrapper aui-page__login">
    <div class="aui-content__wrapper">
      <main class="aui-content">
        <div class="login-body">
          <h3 class="login-title">注册</h3>
          <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" status-icon @keyup.enter.native="dataFormSubmitHandle()">
              <el-form-item prop="username">
                <el-input v-model="dataForm.username" prefix-icon="el-icon-user" :placeholder="$t('login.username')"/>
              </el-form-item>
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
              <el-form-item prop="confirmPassword">
                <el-input v-model="dataForm.confirmPassword" type="password" prefix-icon="el-icon-lock" :placeholder="$t('updatePassword.confirmPassword')"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">提交注册</el-button>
              </el-form-item>
          </el-form>
          <div>
              <router-link :to="{ name: 'login' }">
                  <el-link :underline="false" type="info" style="float: right;">已有帐号登录</el-link>
              </router-link>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import mixinFormModule from '@/mixins/form-module'

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        // 登录接口
        dataFormParamEncrypt: false,
        dataFormSaveURL: '/auth/register'
      },
      formLoading: false, // 表单是否加载中
      dataFormMode: 'save',
      // 短信发送倒计时
      smsSendTimeout: 60,
      dataForm: {
        username: '',
        password: '',
        smsCode: '',
        confirmPassword: '',
        mobile: '',
        mobileArea: '86'
      }
    }
  },
  computed: {
    dataRule () {
      let validateConfirmPassword = (rule, value, callback) => {
        if (this.dataForm.password !== value) {
          return callback(new Error(this.$t('updatePassword.validate.confirmPassword')))
        }
        callback()
      }
      return {
        username: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        password: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
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
  methods: {
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
        this.$http.post(`/auth/sendSmsCode`, { 'mobile': this.dataForm.mobile, 'tplCode': 'CODE_REGISTER' }).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.toast)
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
    // 表单提交成功
    onFormSubmitSuccess (res) {
      this.$confirm('密码重置成功, 是否前往登录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        // 跳转到login
        this.$router.replace({ name: 'login' })
      }).catch(() => {
        // 重置form
        this.resetForm()
      })
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
