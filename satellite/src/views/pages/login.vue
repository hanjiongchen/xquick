<template>
  <div class="aui-wrapper aui-page__login">
    <div class="aui-content__wrapper">
      <main class="aui-content">
        <div class="login-header">
          <h2 class="login-brand">{{ $t('brand.full') }}</h2>
        </div>
        <div class="login-body">
          <h3 class="login-title">{{ $t('login.title') }}</h3>
          <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" status-icon @keyup.enter.native="dataFormSubmitHandle()">
            <el-form-item prop="username">
              <el-input v-model="dataForm.username" :placeholder="$t('login.username')">
                <span slot="prefix" class="el-input__icon">
                  <svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-user"/></svg>
                </span>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="dataForm.password" type="password" :placeholder="$t('login.password')">
                <span slot="prefix" class="el-input__icon">
                  <svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-lock"/></svg>
                </span>
              </el-input>
            </el-form-item>
            <el-form-item prop="captcha" v-if="captchaEnable">
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-input v-model="dataForm.captcha" :placeholder="$t('login.captcha')">
                    <span slot="prefix" class="el-input__icon">
                      <svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-safetycertificate"/></svg>
                    </span>
                  </el-input>
                </el-col>
                <el-col :span="10" class="login-captcha">
                  <el-image :src="captchaPath" @click="getCaptcha()">
                    <div slot="placeholder" class="image-slot">
                      <i class="el-icon-loading"/>
                    </div>
                  </el-image>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">{{ $t('login.title') }}</el-button>
            </el-form-item>
          </el-form>
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
import { getUUID } from '@/utils'
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
      // 是否启用验证码,从接口获取配置
      captchaEnable: false,
      // 登录类型
      type: 10,
      captchaPath: '',
      dataForm: {
        username: '',
        password: '',
        uuid: '',
        captcha: '',
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
          { required: this.captchaEnable, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    // 获取登录配置
    this.getLoginConfig()
  },
  methods: {
    // 获取登录配置
    getLoginConfig () {
      this.$http.get(`/auth/loginConfig?type=${this.type}`).then(({ data: res }) => {
        this.formLoading = false
        if (res.code !== 0) {
          return this.$message.error(res.code + ':' + res.msg)
        } else {
          this.captchaEnable = res.data.captcha
          if (this.captchaEnable) {
            this.getCaptcha()
          }
        }
      }).catch(resp => {
        this.formLoading = false
      })
    },
    // 获取验证码
    getCaptcha () {
      this.dataForm.uuid = getUUID()
      this.captchaPath = `${window.SITE_CONFIG['apiURL']}/auth/captcha?uuid=${this.dataForm.uuid}`
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
