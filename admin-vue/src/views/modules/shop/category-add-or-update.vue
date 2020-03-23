<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
       <el-form-item label="大类" prop="pid">
           <el-select v-model="dataForm.pid" placeholder="选择店铺" class="w-percent-100">
               <el-option v-for="item in pidList" :key="item.id" :label="item.name" :value="item.id"/>
           </el-select>
      </el-form-item>
       <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item prop="sort" :label="$t('dept.sort')">
            <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" :label="$t('dept.sort')"/>
        </el-form-item>
      <el-form-item prop="logo" label="图标">
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
      <el-form-item label="描述" prop="content">
          <el-input v-model="dataForm.content" placeholder="描述" type="textarea"></el-input>
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
        dataFormSaveURL: `/shop/category/save`,
        dataFormUpdateURL: `/shop/category/update`,
        dataFormInfoURL: `/shop/category/info?id=`
      },
      // 一级列表
      pidList: [],
      dataForm: {
        id: '',
        pid: '0',
        name: '',
        logo: '',
        sort: '',
        content: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        pid: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
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
      this.formLoading = true
      this.visible = true
      this.$nextTick(() => {
        this.resetForm()
        this.pidList = []
        this.initUpload()
        Promise.all([
          this.getPidList()
        ]).then(() => {
          this.initFormData()
        })
      })
    },
    // 获取一级列表
    getPidList () {
      return this.$http.get('/shop/category/list?pid=0').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.pidList = res.data
      }).catch(() => {
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
      // 置空
      this.menuSelected = ['0']
      res.data.parentMenuList.forEach(item => this.menuSelected.push(item.id))
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
