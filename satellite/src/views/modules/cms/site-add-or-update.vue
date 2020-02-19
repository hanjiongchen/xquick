<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="编码" prop="code">
          <el-input v-model="dataForm.code" placeholder="编码"></el-input>
      </el-form-item>
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item label="标题" prop="title">
          <el-input v-model="dataForm.title" placeholder="标题"></el-input>
      </el-form-item>
          <el-form-item label="描述" prop="description">
          <el-input v-model="dataForm.description" placeholder="描述"></el-input>
      </el-form-item>
          <el-form-item label="网址" prop="domain">
          <el-input v-model="dataForm.domain" placeholder="网址"></el-input>
      </el-form-item>
          <el-form-item label="LOGO" prop="logo">
          <el-input v-model="dataForm.logo" placeholder="LOGO"></el-input>
      </el-form-item>
          <el-form-item label="版权信息" prop="copyright">
          <el-input v-model="dataForm.copyright" placeholder="版权信息"></el-input>
      </el-form-item>
          <el-form-item label="关键词" prop="keywords">
          <el-input v-model="dataForm.keywords" placeholder="关键词"></el-input>
      </el-form-item>
          <el-form-item label="图片" prop="imgs">
          <el-input v-model="dataForm.imgs" placeholder="图片"></el-input>
      </el-form-item>
          <el-form-item label="状态" prop="status">
          <el-input v-model="dataForm.status" placeholder="状态"></el-input>
      </el-form-item>
          <el-form-item label="租户id" prop="tenantId">
          <el-input v-model="dataForm.tenantId" placeholder="租户id"></el-input>
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
        dataFormSaveURL: `/cms/site/save`,
        dataFormUpdateURL: `/cms/site/update`,
        dataFormInfoURL: `/cms/site/info?id=`
      },
      dataForm: {
        code: '',
        name: '',
        title: '',
        description: '',
        domain: '',
        logo: '',
        copyright: '',
        keywords: '',
        imgs: '',
        status: ''
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
        title: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        status: [
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
