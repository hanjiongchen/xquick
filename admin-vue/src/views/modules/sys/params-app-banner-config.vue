<template>
    <el-dialog :visible.sync="visible" title="App跑马灯配置" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="paramDataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-form-item label="版本号">
                <el-input-number v-model="paramDataForm.version" placeholder="版本号" controls-position="right" :min="0" :max="999999" class="w-percent-100"/>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="第1张图文字" prop="remark1">
                        <el-input v-model="paramDataForm.remark1" placeholder="第1张图文字" maxlength="100" show-word-limit/>
                    </el-form-item>
                </el-col>
                <el-col :span="12" prop="url1">
                    <el-form-item label="第1张图链接">
                        <el-input v-model="paramDataForm.url1" placeholder="第1张图链接" maxlength="500" show-word-limit/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="第1张图" prop="img1">
                <el-upload
                        :before-upload="beforeImageUpload"
                        :on-success="(res, file, fileList) => {res.code !== 0 ? this.$message.error(res.toast) : this.uploadFileList1 = fileList}"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList1"
                        :on-preview="uploadPreviewHandle"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-remove="(file, fileList) => { this.uploadFileList1 = fileList}"
                        :action="uploadUrl">
                    <i class="el-icon-plus"></i>
                </el-upload>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="第2张图文字" prop="remark2">
                        <el-input v-model="paramDataForm.remark2" placeholder="第2张图文字" maxlength="100" show-word-limit/>
                    </el-form-item>
                </el-col>
                <el-col :span="12" prop="url2">
                    <el-form-item label="第2张图链接">
                        <el-input v-model="paramDataForm.url2" placeholder="第2张图链接" maxlength="500" show-word-limit/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="第2张图" prop="img2">
                <el-upload
                        :before-upload="beforeImageUpload"
                        :on-success="(res, file, fileList) => {res.code !== 0 ? this.$message.error(res.toast) : this.uploadFileList2 = fileList}"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList2"
                        :on-preview="uploadPreviewHandle"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-remove="(file, fileList) => { this.uploadFileList2 = fileList}"
                        :action="uploadUrl">
                    <i class="el-icon-plus"></i>
                </el-upload>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="第3张图文字" prop="remark3">
                        <el-input v-model="paramDataForm.remark3" placeholder="第3张图文字" maxlength="100" show-word-limit/>
                    </el-form-item>
                </el-col>
                <el-col :span="12" prop="url3">
                    <el-form-item label="第3张图链接">
                        <el-input v-model="paramDataForm.url3" placeholder="第3张图链接" maxlength="500" show-word-limit/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="第3张图" prop="img1">
                <el-upload
                        :before-upload="beforeImageUpload"
                        :on-success="(res, file, fileList) => {res.code !== 0 ? this.$message.error(res.toast) : this.uploadFileList3 = fileList}"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList3"
                        :on-preview="uploadPreviewHandle"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-remove="(file, fileList) => { this.uploadFileList3 = fileList}"
                        :action="uploadUrl">
                    <i class="el-icon-plus"></i>
                </el-upload>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="第4张图文字" prop="remark4">
                        <el-input v-model="paramDataForm.remark4" placeholder="第4张图文字" maxlength="100" show-word-limit/>
                    </el-form-item>
                </el-col>
                <el-col :span="12" prop="url4">
                    <el-form-item label="第4张图链接">
                        <el-input v-model="paramDataForm.url4" placeholder="第4张图链接" maxlength="500" show-word-limit/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="第4张图" prop="img1">
                <el-upload
                        :before-upload="beforeImageUpload"
                        :on-success="(res, file, fileList) => {res.code !== 0 ? this.$message.error(res.toast) : this.uploadFileList4 = fileList}"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList4"
                        :on-preview="uploadPreviewHandle"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-remove="(file, fileList) => { this.uploadFileList4 = fileList}"
                        :action="uploadUrl">
                    <i class="el-icon-plus"></i>
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

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormUpdateURL: `/sys/param/update`,
        dataFormInfoURL: `/sys/param/info?id=`
      },
      // 传图
      uploadFileList1: [],
      uploadFileList2: [],
      uploadFileList3: [],
      uploadFileList4: [],
      dataForm: {
        id: '',
        code: '',
        remark: '',
        content: '',
        contentJson: ''
      },
      paramDataForm: {
        version: '',
        img1: '',
        remark1: '',
        url1: '',
        img2: '',
        remark2: '',
        url2: '',
        img3: '',
        remark3: '',
        url3: '',
        img4: '',
        remark4: '',
        url4: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        version: [
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
        this.uploadFileList1 = []
        this.uploadFileList2 = []
        this.uploadFileList3 = []
        this.uploadFileList4 = []
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
      // 赋值文件地址
      if (this.dataForm.contentJson && this.dataForm.contentJson.list) {
        const listSize = this.dataForm.contentJson.list.length
        if (listSize > 0) {
          const item = this.dataForm.contentJson.list[0]
          this.uploadFileList1.push({ url: item.img, name: item.img })
          this.paramDataForm.img1 = item.img
          this.paramDataForm.remark1 = item.remark
          this.paramDataForm.url1 = item.url
        }
        if (listSize > 1) {
          const item = this.dataForm.contentJson.list[1]
          this.uploadFileList2.push({ url: item.img, name: item.img })
          this.paramDataForm.img2 = item.img
          this.paramDataForm.remark2 = item.remark
          this.paramDataForm.url2 = item.url
        }
        if (listSize > 2) {
          const item = this.dataForm.contentJson.list[2]
          this.uploadFileList3.push({ url: item.img, name: item.img })
          this.paramDataForm.img3 = item.img
          this.paramDataForm.remark3 = item.remark
          this.paramDataForm.url3 = item.url
        }
        if (listSize > 3) {
          const item = this.dataForm.contentJson.list[3]
          this.uploadFileList4.push({ url: item.img, name: item.img })
          this.paramDataForm.img4 = item.img
          this.paramDataForm.remark4 = item.remark
          this.paramDataForm.url4 = item.url
        }
      }
      this.paramDataForm.version = this.dataForm.contentJson.version
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      let files1 = this.getUploadFileString(this.uploadFileList1)
      let files2 = this.getUploadFileString(this.uploadFileList2)
      let files3 = this.getUploadFileString(this.uploadFileList3)
      let files4 = this.getUploadFileString(this.uploadFileList4)
      // 最终保存到数据库的内容，由list和version组成
      const content = {}
      const list = []
      if (!files1) {
        this.$message.error('第一张图不能为空')
        return false
      } else {
        list.push({ img: files1, url: this.paramDataForm.url1, remark: this.paramDataForm.remark1, needLogin: false })
      }
      if (files2) {
        list.push({ img: files2, url: this.paramDataForm.url2, remark: this.paramDataForm.remark2, needLogin: false })
      }
      if (files3) {
        list.push({ img: files3, url: this.paramDataForm.url3, remark: this.paramDataForm.remark3, needLogin: false })
      }
      if (files4) {
        list.push({ img: files4, url: this.paramDataForm.url4, remark: this.paramDataForm.remark4, needLogin: false })
      }
      content.list = list
      content.version = this.paramDataForm.version

      // 将form转为content的json
      this.dataForm.content = JSON.stringify(content)
      this.dataFormSubmitParam = this.dataForm
      return true
    }
  }
}
</script>
