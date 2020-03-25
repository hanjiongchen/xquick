<template>
    <el-drawer :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" size="50%" :wrapperClosable="false" :close-on-press-escape="false" custom-class="drawer">
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
                <el-input v-model="dataForm.descr" placeholder="描述"></el-input>
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
                <el-tag class="multi-tags" :key="item" v-for="item in keywords" closable :disable-transitions="false" @close="keywords.splice(keywords.indexOf(item), 1)">{{ item }}</el-tag>
                <el-input class="input-new-tag" v-if="tagInputVisible" v-model="tagInputValue" ref="tagInput" size="small" @keyup.enter.native="saveTagInputHandle" @blur="saveTagInputHandle"/>
                <el-button v-else class="button-new-tag" size="small" @click="showTagInput">+ 添加</el-button>
            </el-form-item>
            <el-form-item label="图片" prop="imgs">
                <el-input v-model="dataForm.imgs" placeholder="图片"></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-select v-model="dataForm.status" placeholder="选择状态" class="w-percent-100">
                    <el-option
                            v-for="item in statusOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <div class="drawer__footer">
            <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
            <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
        </div>
    </el-drawer>
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
      statusOptions: [{
        value: 0,
        label: '下线'
      }, {
        value: 1,
        label: '上线'
      }],
      tagInputVisible: false,
      tagInputValue: '',
      keywords: [],
      dataForm: {
        code: '',
        name: '',
        title: '',
        descr: '',
        domain: '',
        logo: '',
        copyright: '',
        keywords: '',
        imgs: '',
        status: 1
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
        this.keywords = []
        this.tagInputVisible = false
        this.tagInputValue = ''
        this.resetForm()
        this.initFormData()
      })
    },

    // 表单提交之前的操作
    beforeDateFormSubmit () {
      this.dataForm.keywords = this.keywords.join(',')
      this.dataFormSubmitParam = this.dataForm
      return true
    },

    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      // 分割关键词
      if (this.dataForm.keywords) {
        this.keywords = this.dataForm.keywords.split(',').filter(item => item !== '')
      }
    },

    showTagInput () {
      this.tagInputVisible = true
      this.$nextTick(() => {
        this.$refs.tagInput.$refs.input.focus()
      })
    },

    saveTagInputHandle () {
      let inputValue = this.tagInputValue
      if (inputValue) {
        inputValue = inputValue.trim()
        if (!inputValue) {
          this.$message.error('不允许为空')
        } else if (inputValue.indexOf(',') > -1) {
          this.$message.error('不允许出现逗号')
        } else if (this.keywords.indexOf(inputValue) > -1) {
          this.$message.error('不允许重复添加')
        } else {
          this.keywords.push(inputValue)
        }
      }
      this.tagInputVisible = false
      this.tagInputValue = ''
    }
  }
}
</script>
