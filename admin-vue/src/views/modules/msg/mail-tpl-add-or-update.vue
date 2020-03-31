<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-row>
                <el-col :span="12">
                    <el-form-item :label="$t('base.code')" prop="code">
                        <el-input v-model="dataForm.code" :placeholder="$t('base.code')"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="name" :label="$t('base.name')">
                        <el-input v-model="dataForm.name" :placeholder="$t('base.name')"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="senderHost" label="发件Host">
                        <el-input v-model="dataForm.senderHost" placeholder="发件Host"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="senderHostPort" label="发件端口">
                        <el-input-number v-model="dataForm.senderHostPort" controls-position="right" :min="0" :max="65536" label="发件端口" class="w-percent-100"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="senderUsername" label="发件邮箱">
                        <el-input v-model="dataForm.senderUsername" placeholder="发件邮箱"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="senderPassword" label="发件密码">
                        <el-input v-model="dataForm.senderPassword" placeholder="发件密码"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item prop="title" label="邮件标题">
                <el-input v-model="dataForm.title" placeholder="请输入邮件标题"/>
            </el-form-item>
            <el-form-item prop="content" label="邮件内容">
                <quill-editor ref="editorContent"/>
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
import QuillEditor from '@/components/quill-editor'

export default {
  mixins: [mixinFormModule],
  components: { QuillEditor },
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/msg/mailTpl/save`,
        dataFormUpdateURL: `/msg/mailTpl/update`,
        dataFormInfoURL: `/msg/mailTpl/info?id=`
      },
      dataForm: {
        id: '',
        name: '',
        code: '',
        title: '',
        content: '',
        senderHost: '',
        senderHostPort: '',
        senderUsername: '',
        senderPassword: ''
      }
    }
  },
  computed: {
    dataRule () {
      var validateContent = (rule, value, callback) => {
        if (this.$refs.editorContent.getContentLength() <= 1) {
          return callback(new Error(this.$t('validate.required')))
        }
        callback()
      }
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
        content: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' },
          { validator: validateContent, trigger: 'blur' }
        ],
        senderHost: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        senderHostPort: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        senderUsername: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        senderPassword: [
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
        this.quillEditorHandle()
        this.initUpload()
        this.resetForm()
        this.initFormData()
      })
    },
    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      // set富文本编辑器
      this.$refs.editorContent.setInnerHTML(this.dataForm.content)
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      this.dataForm.content = this.$refs.editorContent.getInnerHTML()
      this.dataFormSubmitParam = this.dataForm
      return true
    }
  }
}
</script>
