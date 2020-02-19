<template>
  <el-dialog :visible.sync="visible" title="App版本号配置" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="paramDataForm" :rules="dataRule" ref="dataForm" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="版本号" prop="version">
            <el-input-number v-model="paramDataForm.version" placeholder="版本号" controls-position="right" :min="0" :max="999999" class="w-percent-100"/>
          </el-form-item>
        </el-col>
        <el-col :span="12" prop="versionName">
          <el-form-item label="版本名称">
            <el-input v-model="paramDataForm.versionName" placeholder="版本名称" maxlength="50" show-word-limit/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="链接地址" prop="url">
        <el-upload
                :before-upload="beforeApkUpload"
                :on-success="uploadSuccessHandle"
                accept=".apk"
                :file-list="uploadFileList"
                :on-preview="openFileHandle"
                :multiple="false"
                :on-exceed="uploadExceedHandle"
                :on-remove="uploadRemoveHandle"
                :action="uploadUrl">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传apk文件</div>
        </el-upload>
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
import { beforeApkUpload } from '@/utils/upload'

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormUpdateURL: `/sys/param/update`,
        dataFormInfoURL: `/sys/param/info?id=`
      },
      dataForm: {
        id: '',
        code: '',
        remark: '',
        content: ''
      },
      paramDataForm: {
        version: '',
        versionName: '',
        url: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        version: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        versionName: [
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
        this.initUpload()
        this.resetForm()
        this.initFormData()
      })
    },
    // 图片上传前检查
    beforeApkUpload (file) {
      beforeApkUpload(file)
    },
    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      // json序列化content
      this.paramDataForm = JSON.parse(this.dataForm.content)
      // 赋值apk文件
      this.setUploadFileList(this.paramDataForm.url)
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      this.paramDataForm.url = this.getUploadFileString()
      if (!this.paramDataForm.url) {
        this.$message.error('链接地址不能为空')
        return false
      }
      // 将form转为content的json
      this.dataForm.content = JSON.stringify(this.paramDataForm)
      this.dataFormSubmitParam = this.dataForm
      return true
    }
  }
}
</script>
