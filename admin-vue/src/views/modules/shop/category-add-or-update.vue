<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="父id" prop="pid">
          <el-input v-model="dataForm.pid" placeholder="父id"></el-input>
      </el-form-item>
          <el-form-item label="店铺id" prop="storeId">
          <el-input v-model="dataForm.storeId" placeholder="店铺id"></el-input>
      </el-form-item>
          <el-form-item label="店铺编码" prop="storeCode">
          <el-input v-model="dataForm.storeCode" placeholder="店铺编码"></el-input>
      </el-form-item>
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item label="logo" prop="logo">
          <el-input v-model="dataForm.logo" placeholder="logo"></el-input>
      </el-form-item>
          <el-form-item label="排序" prop="sort">
          <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
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
        dataFormSaveURL: `/shop/category/save`,
        dataFormUpdateURL: `/shop/category/update`,
        dataFormInfoURL: `/shop/category/info?id=`
      },
      dataForm: {
        id: '',
        pid: '',
        storeId: '',
        storeCode: '',
        name: '',
        logo: '',
        sort: '',
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
        pid: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        storeId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        storeCode: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        logo: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        sort: [
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
