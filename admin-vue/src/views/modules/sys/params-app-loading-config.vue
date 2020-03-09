<template>
    <el-dialog :visible.sync="visible" title="App启动页配置" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="paramDataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="版本号" prop="version">
                        <el-input-number v-model="paramDataForm.version" placeholder="版本号" controls-position="right" :min="0" :max="999999" class="w-percent-100"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12" prop="url">
                    <el-form-item label="链接地址">
                        <el-input v-model="paramDataForm.url" placeholder="链接地址" maxlength="500" show-word-limit/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="(750x1334)" prop="img_750x1334">
                        <el-upload
                                :before-upload="beforeImageUpload"
                                :on-success="(res, file, fileList) => this.uploadFileList750x1334 = fileList"
                                list-type="picture-card"
                                :limit="1"
                                :accept="acceptImageFormat"
                                :file-list="uploadFileList750x1334"
                                :multiple="false"
                                :on-exceed="uploadExceedHandle"
                                :on-remove="(file, fileList) => this.uploadFileList750x1334 = fileList"
                                :action="uploadUrl">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </el-form-item>
                </el-col>
                <el-col :span="12" prop="url">
                    <el-form-item label="(828x1792)" prop="img_828x1792">
                        <el-upload
                                :before-upload="beforeImageUpload"
                                :on-success="(res, file, fileList) => this.uploadFileList828x1792 = fileList"
                                list-type="picture-card"
                                :limit="1"
                                :accept="acceptImageFormat"
                                :file-list="uploadFileList828x1792"
                                :multiple="false"
                                :on-exceed="uploadExceedHandle"
                                :on-remove="(file, fileList) => this.uploadFileList828x1792 = fileList"
                                :action="uploadUrl">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </el-form-item>
                </el-col>
            </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="(1242x2208)" prop="img_1242x2208">
                <el-upload
                        :before-upload="beforeImageUpload"
                        :on-success="(res, file, fileList) => this.uploadFileList1242x2208 = fileList"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList1242x2208"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-remove="(file, fileList) => this.uploadFileList1242x2208 = fileList"
                        :action="uploadUrl">
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="12" prop="url">
              <el-form-item label="(1125x2436)" prop="img_1125x2436">
                <el-upload
                        :before-upload="beforeImageUpload"
                        :on-success="(res, file, fileList) => this.uploadFileList1125x2436 = fileList"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList1125x2436"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-remove="(file, fileList) => this.uploadFileList1125x2436 = fileList"
                        :action="uploadUrl">
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="(1242x2688)" prop="img_1242x2688">
            <el-upload
                    :before-upload="beforeImageUpload"
                    :on-success="(res, file, fileList) => this.uploadFileList1242x2688 = fileList"
                    list-type="picture-card"
                    :limit="1"
                    :accept="acceptImageFormat"
                    :file-list="uploadFileList1242x2688"
                    :multiple="false"
                    :on-exceed="uploadExceedHandle"
                    :on-remove="(file, fileList) => this.uploadFileList1242x2688 = fileList"
                    :action="uploadUrl">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <template slot="footer">
            <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
            <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
        </template>
        <!-- 弹窗, 图片查看 -->
        <image-viewer :z-index="imageViewerZIndex" :url-list="imageViewerPreviewSrcList" ref="imageViewer" v-show="imageViewerVisible" :on-close="closeImageViewerHandle"/>
    </el-dialog>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinFormModule from '@/mixins/form-module'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'

export default {
  mixins: [mixinBaseModule, mixinFormModule],
  components: { ImageViewer },
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
        img_750x1334: '',
        img_1242x2688: '',
        img_1242x2208: '',
        img_828x1792: '',
        img_1125x2436: '',
        url: ''
      },
      uploadFileList750x1334: [],
      uploadFileList1242x2688: [],
      uploadFileList1242x2208: [],
      uploadFileList828x1792: [],
      uploadFileList1125x2436: []
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
        this.setUploadUrl()
        this.uploadFileList750x1334 = []
        this.uploadFileList1242x2688 = []
        this.uploadFileList1242x2208 = []
        this.uploadFileList828x1792 = []
        this.uploadFileList1125x2436 = []
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
      // json序列化content
      this.paramDataForm = JSON.parse(this.dataForm.content)
      // 赋值文件地址
      this.setImgsToUploadFileList(this.uploadFileList750x1334, this.paramDataForm.img_750x1334)
      this.setImgsToUploadFileList(this.uploadFileList1242x2688, this.paramDataForm.img_1242x2688)
      this.setImgsToUploadFileList(this.uploadFileList1242x2208, this.paramDataForm.img_1242x2208)
      this.setImgsToUploadFileList(this.uploadFileList828x1792, this.paramDataForm.img_828x1792)
      this.setImgsToUploadFileList(this.uploadFileList1125x2436, this.paramDataForm.img_1125x2436)
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      this.paramDataForm.img_750x1334 = this.getUploadFileString(this.uploadFileList750x1334)
      this.paramDataForm.img_1242x2688 = this.getUploadFileString(this.uploadFileList1242x2688)
      this.paramDataForm.img_1242x2208 = this.getUploadFileString(this.uploadFileList1242x2208)
      this.paramDataForm.img_828x1792 = this.getUploadFileString(this.uploadFileList828x1792)
      this.paramDataForm.img_1125x2436 = this.getUploadFileString(this.uploadFileList1125x2436)
      // 将form转为content的json
      this.dataForm.content = JSON.stringify(this.paramDataForm)
      this.dataFormSubmitParam = this.dataForm
      return true
    }
  }
}
</script>
