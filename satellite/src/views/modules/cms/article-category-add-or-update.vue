<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="名称" prop="name">
                        <el-input v-model="dataForm.name" placeholder="名称" maxlength="100"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item :label="$t('base.sort')" prop="sort">
                        <el-input-number controls-position="right" :min="0" v-model="dataForm.sort" :placeholder="$t('base.remark')" class="w-percent-100"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item prop="imgs" :label="$t('base.cover')">
                <el-upload
                        :class="{hide:uploadFileList.length >= 1}"
                        :before-upload="beforeImageUpload"
                        :on-success="uploadSuccessHandle"
                        :on-error="uploadErrorHandle"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList"
                        :on-preview="uploadPreviewHandle"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-remove="uploadRemoveHandle"
                        :action="uploadUrl">
                    <i class="el-icon-plus"/>
                </el-upload>
            </el-form-item>
            <el-form-item :label="$t('base.remark')" prop="remark">
                <el-input v-model="dataForm.remark" :placeholder="$t('base.remark')" type="textarea" maxlength="500"/>
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
        dataFormSaveURL: `/cms/articleCategory/save`,
        dataFormUpdateURL: `/cms/articleCategory/update`,
        dataFormInfoURL: `/cms/articleCategory/info?id=`
      },
      dataForm: {
        id: '',
        name: '',
        sort: '',
        remark: '',
        imgs: '',
        status: 1
      }
    }
  },
  computed: {
    dataRule () {
      return {
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        sort: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.visible = true
      this.formLoading = true
      this.$nextTick(() => {
        this.setUploadUrl()
        this.uploadFileList = []
        this.$refs['dataForm'].resetFields()
        this.initFormData()
      })
    },
    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      // 赋值图片
      this.setUploadFileList(this.dataForm.imgs)
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      this.dataForm.imgs = this.getUploadFileString()
      this.dataFormSubmitParam = this.dataForm
      return true
    }
  }
}
</script>
