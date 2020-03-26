<template>
  <el-card>
      <el-tabs tab-position="left" v-model="step" @tab-click="tabClickHandle" :before-leave="beforeTabLeaveHandle">
          <el-tab-pane name="1" label="基本信息">
              <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
                  <el-form-item label="类型" prop="type">
                      <el-radio-group v-model="dataForm.type" disabled>
                          <el-radio :label="1">商品</el-radio>
                          <el-radio :label="2">积分兑换</el-radio>
                      </el-radio-group>
                  </el-form-item>
                  <el-row>
                      <el-col :span="12">
                          <el-form-item label="供应商" prop="supplierId">
                              <el-autocomplete
                                      class="w-percent-100"
                                      value-key="name"
                                      v-model="dataForm.supplierName"
                                      :fetch-suggestions="getSupplierList"
                                      placeholder="请选择供应商"
                                      @select="item => dataForm.supplierId = item.id"/>
                          </el-form-item>
                      </el-col>
                      <el-col :span="12">
                          <el-form-item label="品牌" prop="brandId">
                              <el-select v-model="dataForm.brandId" filterable placeholder="请选择品牌" class="w-percent-100">
                                  <el-option v-for="item in brandList" :key="item.id" :label="item.name" :value="item.id"/>
                              </el-select>
                          </el-form-item>
                      </el-col>
                  </el-row>
                  <el-row>
                      <el-col :span="12">
                          <el-form-item label="分类" prop="categoryId">
                              <el-cascader v-model="categorySelected" :options="spuCategoryList" clearable
                                           :props="{ emitPath: false, checkStrictly: false, value: 'id', label: 'name'}"
                                           @change="(value) => this.dataForm.categoryId = value" class="w-percent-100"/>
                          </el-form-item>
                      </el-col>
                      <el-col :span="12">
                          <el-form-item prop="sort" :label="$t('base.sort')">
                              <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" :max="9999" :label="$t('base.sort')"/>
                          </el-form-item>
                      </el-col>
                  </el-row>
                  <el-row>
                      <el-col :span="12">
                          <el-form-item label="名称" prop="name">
                              <el-input v-model="dataForm.name" placeholder="输入名称"/>
                          </el-form-item>
                      </el-col>
                      <el-col :span="12">
                          <el-form-item label="编号" prop="sn">
                              <el-input v-model="dataForm.sn" placeholder="编号"/>
                          </el-form-item>
                      </el-col>
                  </el-row>
                  <el-row>
                      <el-col :span="12">
                          <el-form-item label="市场价" prop="marketPrice">
                              <el-input-number v-model="dataForm.marketPrice" placeholder="输入市场价" controls-position="right" :min="0" :max="99999" :precision="2" :step="1" class="w-percent-100"/>
                          </el-form-item>
                      </el-col>
                      <el-col :span="12">
                          <el-form-item label="销售价" prop="salePrice">
                              <el-input-number v-model="dataForm.salePrice" placeholder="输入销售价" controls-position="right" :min="0" :max="99999" :precision="2" :step="1" class="w-percent-100"/>
                          </el-form-item>
                      </el-col>
                  </el-row>
                  <el-row>
                      <el-col :span="8">
                          <el-form-item label="物流" prop="delivery">
                              <el-radio-group v-model="dataForm.delivery" size="small">
                                  <el-radio-button :label="1">需要</el-radio-button>
                                  <el-radio-button :label="0">不需要</el-radio-button>
                              </el-radio-group>
                          </el-form-item>
                      </el-col>
                      <el-col :span="8">
                          <el-form-item label="上架" prop="marketable">
                              <el-radio-group v-model="dataForm.marketable" size="small">
                                  <el-radio-button :label="1">上架</el-radio-button>
                                  <el-radio-button :label="0">下架</el-radio-button>
                              </el-radio-group>
                          </el-form-item>
                      </el-col>
                      <el-col :span="8">
                          <el-form-item label="置顶" prop="top">
                              <el-radio-group v-model="dataForm.top" size="small">
                                  <el-radio-button :label="1">置顶</el-radio-button>
                                  <el-radio-button :label="0">不置顶</el-radio-button>
                              </el-radio-group>
                          </el-form-item>
                      </el-col>
                  </el-row>
                  <el-form-item label="标题" prop="title">
                      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
                  </el-form-item>
                  <el-form-item label="标签,逗号分隔" prop="tags">
                      <el-input v-model="dataForm.tags" placeholder="标签,逗号分隔"></el-input>
                  </el-form-item>
            </el-form>
              <div style="text-align: center;">
                  <el-button type="warning" @click="visible = false">{{ $t('reset') }}</el-button>
                  <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
              </div>
          </el-tab-pane>
          <el-tab-pane name="2" label="图文详情">
              <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
                  <el-form-item prop="content" label="图文详情">
                      <!-- 富文本编辑器, 容器 -->
                      <div id="J_quillEditor"></div>
                      <!-- 自定义上传图片功能 (使用element upload组件) -->
                      <el-upload
                              :action="uploadUrl"
                              :show-file-list="false"
                              :before-upload="beforeImageUpload"
                              :on-success="uploadEditorSuccessHandle"
                              style="display: none;">
                          <el-button ref="uploadBtn" type="primary" size="small">{{ $t('uploadButton') }}</el-button>
                      </el-upload>
                  </el-form-item>
              </el-form>
              <div style="text-align: center;">
                  <el-button type="warning" @click="resetForm">{{ $t('reset') }}</el-button>
                  <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
              </div>
          </el-tab-pane>
          <el-tab-pane name="3" label="参数管理">配置管理</el-tab-pane>
          <el-tab-pane name="4" label="规格管理">角色管理</el-tab-pane>
      </el-tabs>
      <!-- 弹窗, 图片查看 -->
      <image-viewer :z-index="imageViewerZIndex" :url-list="imageViewerPreviewSrcList" ref="imageViewer" v-show="imageViewerVisible" :on-close="closeImageViewerHandle"/>
  </el-card>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinFormModule from '@/mixins/form-module'
import mixinQuillModule from '@/mixins/quill-module'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'
import { removeEmptyChildren } from '@/utils'

export default {
  inject: ['refresh'],
  mixins: [mixinBaseModule, mixinFormModule, mixinQuillModule],
  components: { ImageViewer },
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/spu/save`,
        dataFormUpdateURL: `/shop/spu/update`,
        dataFormInfoURL: `/shop/spu/info?id=`
      },
      // 当前激活tab
      step: '1',
      // 品牌列表
      brandList: [],
      // 分类列表
      spuCategoryList: [],
      // 已选中分类
      categorySelected: [],
      dataForm: {
        id: '',
        storeId: '',
        brandId: '',
        categoryId: '',
        supplierId: '',
        supplierName: '',
        sort: '',
        sn: '',
        delivery: 1,
        marketable: 1,
        top: 1,
        type: 1,
        name: '',
        title: '',
        tags: '',
        marketPrice: 0,
        salePrice: 0,
        attrs: '',
        specs: '',
        status: '',
        hits: '',
        imgs: '',
        content: '',
        score: ''
      }
    }
  },
  activated () {
    let queryId = this.$route.query.id
    let queryStep = this.$route.query.step || '1'
    if (this.dataForm.id !== queryId || this.step !== queryStep) {
      // 参数发生了变化
      if (!queryId && queryStep > 1) {
        this.$message.error(this.$t('addneedstep'))
        return
      } else {
        this.dataForm.id = queryId
        this.step = queryStep
      }
      // 根据id刷新tab名称
      let tab = this.$store.state.contentTabs.filter(item => item.name === this.$route.name)[0]
      if (tab) {
        tab.title = queryId ? '编辑商品' : '新增商品'
      }
      // 根据step刷新数据
      if (this.step === '1') {
        Promise.all([
          this.getBrandList(''),
          // this.getSupplierList(''),
          this.getSpuCategoryList('')
        ]).then(() => {
          this.init()
        })
      }
    }
  },
  computed: {
    dataRule () {
      return {
        storeId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        brandId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        supplierId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        sort: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        sn: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        delivery: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        marketable: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        top: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        type: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        title: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        tags: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        marketPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        salePrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        attrs: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        specs: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        status: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        hits: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        imgs: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        score: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.init()
  },
  methods: {
    init () {
      this.formLoading = true
      this.visible = true
      this.$nextTick(() => {
        this.resetForm()
        this.initFormData()
      })
    },
    // 跳转步骤
    onStep (result) {
      if (!this.dataForm.id && result !== 1) {
        this.$message.error('保存基本信息后才能跳转步骤')
        return false
      }
      if (result === 100) {
        this.step = Number.parseInt(this.step) + 1
      } else if (result === -100) {
        this.step = Number.parseInt(this.step) - 1
      } else {
        this.step = result
      }
      this.$router.replace({ name: this.$route.name, query: { id: this.dataForm.id, step: this.step } })
      this.refresh()
    },
    tabClickHandle (tab, event) {
      console.log(tab, event)
      this.onStep(Number.parseInt(tab.name))
    },
    beforeTabLeaveHandle (activeName) {
      if (!this.dataForm.id && activeName !== '1') {
        this.$message.error('请保存基本信息')
        return false
      } else {
        return true
      }
    },
    // 表单提交成功
    onFormSubmitSuccess (res) {
      // 跳到下一步
      this.onStep(100)
      // 弹出提示框
      this.$message({
        message: '保存成功',
        type: 'success',
        duration: 500,
        onClose: () => {}
      })
    },
    // 品牌列表
    getBrandList (name) {
      return this.$http.get(`/shop/brand/list?name=` + name).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.brandList = res.data
      }).catch(() => {
      })
    },
    // 供应商列表
    getSupplierList (name, callback) {
      return this.$http.get(`/shop/supplier/list?name=` + name).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        callback(res.data)
      }).catch(() => {
      })
    },
    // 供应商列表
    getSpuCategoryList (name) {
      return this.$http.get(`/shop/spuCategory/tree?name=` + name).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.spuCategoryList = removeEmptyChildren(res.data)
      }).catch(() => {
      })
    }
  }
}
</script>
