<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item label="图标" prop="logo">
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
          <el-form-item label="联系电话" prop="tel">
          <el-input v-model="dataForm.tel" placeholder="联系电话"></el-input>
      </el-form-item>
          <el-form-item prop="sort" :label="$t('dept.sort')">
            <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" :label="$t('dept.sort')"/>
          </el-form-item>
        <el-form-item label="状态" prop="status">
            <el-select v-model="dataForm.status" placeholder="请选择" class="w-percent-100">
                <el-option
                        v-for="item in statusOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
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
import mixinBaseModule from '@/mixins/base-module'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'

export default {
  mixins: [mixinBaseModule, mixinFormModule],
  components: { ImageViewer },
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/store/save`,
        dataFormUpdateURL: `/shop/store/update`,
        dataFormInfoURL: `/shop/store/info?id=`
      },
      dataForm: {
        name: '',
        logo: '',
        tel: '',
        sort: '',
        status: '',
        content: ''
      },
      statusOptions: [{
        value: 0,
        label: '未审核'
      }, {
        value: 1,
        label: '已审核'
      }]
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
        this.setUploadUrl()
        this.uploadFileList = []
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
      this.setUploadFileList(this.dataForm.logo)
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      this.dataForm.logo = this.getUploadFileString()
      this.dataFormSubmitParam = this.dataForm
      return true
    }
  }
}
</script>
