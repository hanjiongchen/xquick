<template>
  <el-dialog :visible.sync="visible" title="发送邮件" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
      <el-form-item prop="mailTo" label="收件人">
        <el-input v-model="dataForm.mailTo" placeholder="收件人,多个用逗号分隔"/>
      </el-form-item>
      <el-form-item prop="mailCc" label="抄送">
        <el-input v-model="dataForm.mailCc" placeholder="抄送,多个用逗号分隔"/>
      </el-form-item>
      <el-form-item prop="titleParam" label="标题参数">
        <el-input v-model="dataForm.titleParam" placeholder="标题参数,json格式"/>
      </el-form-item>
      <el-form-item prop="contentParam" label="正文参数">
        <el-input v-model="dataForm.contentParam" placeholder="正文参数,json格式"/>
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

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/msg/mailLog/send`
      },
      dataForm: {
        tplCode: '',
        mailTo: '',
        mailCc: '',
        contentParam: '',
        titleParam: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        mailTo: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
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
