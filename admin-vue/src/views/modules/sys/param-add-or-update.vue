<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm"  label-width="120px">
      <el-form-item prop="code" :label="$t('base.code')">
        <el-input v-model="dataForm.code" :placeholder="$t('base.code')"/>
      </el-form-item>
      <el-form-item prop="content" :label="$t('base.content')">
        <el-input v-model="dataForm.content" :placeholder="$t('base.content')" type="textarea"/>
      </el-form-item>
      <el-form-item prop="remark" :label="$t('base.remark')">
        <el-input v-model="dataForm.remark" :placeholder="$t('base.remark')"/>
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
        dataFormSaveURL: `/sys/param/save`,
        dataFormUpdateURL: `/sys/params/update`,
        dataFormInfoURL: `/sys/param/info?id=`
      },
      dataForm: {
        id: '',
        code: '',
        content: '',
        remark: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        content: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        code: [
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
        this.$refs['dataForm'].resetFields()
        this.initFormData()
      })
    }
  }
}
</script>
