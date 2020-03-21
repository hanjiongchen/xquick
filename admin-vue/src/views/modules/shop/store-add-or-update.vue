<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="编码" prop="code">
          <el-input v-model="dataForm.code" placeholder="编码"></el-input>
      </el-form-item>
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item label="logo" prop="logo">
          <el-input v-model="dataForm.logo" placeholder="logo"></el-input>
      </el-form-item>
          <el-form-item label="联系电话" prop="tel">
          <el-input v-model="dataForm.tel" placeholder="联系电话"></el-input>
      </el-form-item>
          <el-form-item label="类型" prop="type">
          <el-input v-model="dataForm.type" placeholder="类型"></el-input>
      </el-form-item>
          <el-form-item label="排序" prop="sort">
          <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>
          <el-form-item label="状态0 未审核 1 已审核" prop="status">
          <el-input v-model="dataForm.status" placeholder="状态0 未审核 1 已审核"></el-input>
      </el-form-item>
          <el-form-item label="介绍" prop="content">
          <el-input v-model="dataForm.content" placeholder="介绍"></el-input>
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
        dataFormSaveURL: `/shop/store/save`,
        dataFormUpdateURL: `/shop/store/update`,
        dataFormInfoURL: `/shop/store/info?id=`
      },
      dataForm: {
        id: '',
        code: '',
        name: '',
        logo: '',
        tel: '',
        type: '',
        sort: '',
        status: '',
        content: '',
        createId: '',
        createTime: '',
        updateId: '',
        updateTime: '',
        deleted: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        code: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        logo: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        tel: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        type: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        sort: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        status: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        content: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        createId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        updateId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        updateTime: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        deleted: [
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
