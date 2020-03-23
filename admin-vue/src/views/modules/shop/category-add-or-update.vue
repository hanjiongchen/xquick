<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="父级商品类别" prop="pid">
          <el-cascader v-model="menuSelected" :options="pidList" clearable :props="{ emitPath: false, checkStrictly: true, value: 'id', label: 'name', children: 'children'}"
                       @change="(value) => this.dataForm.pid = value ? value : '0'" class="w-percent-100"/>
      </el-form-item>
          <el-form-item label="店铺" prop="storeId">
          <el-select v-model="dataForm.storeId" placeholder="选择店铺" class="w-percent-100">
              <el-option v-for="item in storeList" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
      </el-form-item>
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item prop="logo" label="logo">
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
          <!--<el-form-item label="排序" prop="sort">
          <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>-->
          <el-form-item label="介绍" prop="content">
          <el-input v-model="dataForm.content" placeholder="介绍"></el-input>
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
import { removeEmptyChildren } from '@/utils'
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
      // 文章分类列表
      storeList: [],
      // 已选中菜单
      menuSelected: ['0'],
      // 父类列表
      pidList: [],
      dataForm: {
        id: '',
        pid: '0',
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
        this.menuSelected = ['0']
        this.pidList = []
        this.setUploadUrl()
        this.uploadFileList = []
        Promise.all([
          this.getStoreList(),
          this.getPidList()
        ]).then(() => {
          this.initFormData()
        })
      })
    },
    // 获取店铺列表
    getStoreList () {
      this.$http.get('/shop/store/list').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.storeList = res.data
        this.formLoading = false
      }).catch(resp => {
        this.formLoading = false
        this.$message.error(this.$t('prompt.apierror') + resp)
      })
    },
    // 获取父类列表
    getPidList () {
      return this.$http.get('/shop/category/tree').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        // 加一个根菜单,用于选择
        this.pidList = [{ 'id': '0', 'name': '根菜单', 'children': removeEmptyChildren(res.data) }]
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
