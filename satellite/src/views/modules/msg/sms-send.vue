<template>
  <el-dialog :visible.sync="visible" title="发送短信" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
      <el-form-item prop="mobile" :label="$t('base.mobile')">
        <el-input v-model="dataForm.mobile" :placeholder="$t('base.mobile')"/>
      </el-form-item>
      <el-form-item prop="param" :label="$t('base.param')">
        <el-input v-model="dataForm.param" :placeholder="$t('base.param')" type="textarea"/>
      </el-form-item>
    </el-form>
    <template slot="footer">
      <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
    </template>
  </el-dialog>
</template>

<script>
import mixinFormModule from '@/mixins/form-module'
import { isMobile } from '@/utils/validate'

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/msg/smsLog/send`
      },
      paramJson: null,
      dataForm: {
        param: '',
        tplCode: '',
        mobile: ''
      }
    }
  },
  computed: {
    dataRule () {
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          return callback(new Error(this.$t('validate.format', { 'attr': this.$t('base.mobile') })))
        }
        callback()
      }
      return {
        mobile: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' },
          { validator: validateMobile, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.formLoading = true
      this.visible = true
      this.$nextTick(() => {
        this.resetForm()
        this.initFormData()
      })
    }
  }
}
</script>
