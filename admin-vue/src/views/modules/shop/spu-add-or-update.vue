<template>
    <el-card>
        <el-tabs tab-position="left" v-model="step" @tab-click="tab => this.onStep(tab.name)" :before-leave="() => !!this.dataForm.id">
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
                    <el-form-item label="规格类型" prop="specType">
                        <el-radio-group v-model="dataForm.specType" size="small" disabled>
                            <el-radio-button :label="0">单规格</el-radio-button>
                            <el-radio-button :label="1">多规格</el-radio-button>
                        </el-radio-group>
                        <br/>
                        <span style="color: red">注意:规格类型和规格内容保存后无法修改,规格留空表示不启用</span>
                    </el-form-item>
                    <el-row v-if="dataForm.specType === 0">
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
                            <el-form-item label="在架" prop="marketable">
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
                    <el-form-item label="标签" prop="tags">
                        <el-tag class="multi-tags" :key="item" v-for="item in tags" closable :disable-transitions="false" @close="tags.splice(tags.indexOf(item), 1)">{{ item }}</el-tag>
                        <el-input class="input-new-tag" v-if="tagInputVisible" v-model="tagInputValue" ref="tagInput" size="small" @keyup.enter.native="saveTagInputHandle" @blur="saveTagInputHandle"/>
                        <el-button v-else class="button-new-tag" size="small" @click="showTagInput">+ 添加</el-button>
                    </el-form-item>
                </el-form>
                <div style="text-align: center;">
                    <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('save') }}</el-button>
                </div>
            </el-tab-pane>
            <el-tab-pane name="2" label="商品图片" v-if="!!dataForm.id">
                <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm">
                    <el-form-item prop="imgs">
                        <span>图片建议尺寸800*400,大小限制2MB内</span>
                        <el-upload
                                :before-upload="beforeImageUpload"
                                :on-success="uploadSuccessHandle"
                                list-type="picture-card"
                                :limit="4"
                                :accept="acceptImageFormat"
                                :file-list="uploadFileList"
                                :on-preview="uploadPreviewHandle"
                                :multiple="false"
                                :on-exceed="uploadExceedHandle"
                                :on-remove="uploadRemoveHandle"
                                :action="uploadUrl">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </el-form-item>
                </el-form>
                <div style="text-align: center;">
                    <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('save') }}</el-button>
                </div>
            </el-tab-pane>
            <el-tab-pane name="3" label="详情介绍" v-if="!!dataForm.id">
                <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm">
                    <el-form-item prop="content">
                        <!-- 富文本编辑器, 容器 -->
                        <div id="J_quillEditor"/>
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
                    <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('save') }}</el-button>
                </div>
            </el-tab-pane>
            <el-tab-pane name="4" label="参数管理" v-if="!!dataForm.id">
                <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="70px">
                    <el-row v-for="(attrGroup, index) in dataForm.attrGroups" :key="index" :prop="'attrGroup.' + index + '.value'">
                        <el-col :span="6">
                            <el-form-item :label="'参数组' + (index + 1)">
                                <el-input v-model="attrGroup.name" placeholder="参数组名称" maxlength="50"/>
                                <el-button @click.prevent="addAttrGroup(index + 1)" style="margin-left: 10px;" type="text">添加参数组</el-button>
                                <el-button @click.prevent="removeAttrGroup(attrGroup)" style="margin-left: 10px;color:#f56c6c;" type="text">删除参数组</el-button>
                            </el-form-item>
                        </el-col>
                        <el-col :span="18">
                            <el-row v-for="(attr, attrIndex) in attrGroup.items" :key="attrIndex" :prop="'attr.' + attrIndex + '.value'">
                                <el-col :span="9">
                                    <el-form-item :label="'参数' + (attrIndex + 1)">
                                        <el-input v-model="attr.name" placeholder="参数名" maxlength="50"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="9">
                                    <el-form-item label="参数值">
                                        <el-input v-model="attr.value" placeholder="参数值" maxlength="50"/>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6">
                                    <el-button @click.prevent="addAttr(attrGroup, attrIndex + 1)" style="margin-left: 10px;" type="text">添加参数</el-button>
                                    <el-button @click.prevent="removeAttr(attrGroup, attr)" style="margin-left: 10px;color:#f56c6c;" type="text" v-if="attrGroup.items.length > 1">删除参数</el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-form>
                <div style="text-align: center;">
                    <el-button type="success" @click="addAttrGroup(dataForm.attrGroups.length + 1)">添加参数组</el-button>
                    <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('save') }}</el-button>
                </div>
            </el-tab-pane>
            <el-tab-pane name="5" label="规格管理" v-if="!!dataForm.id && dataForm.specType === 1">暂不支持多规格</el-tab-pane>
        </el-tabs>
        <!-- 弹窗, 图片查看 -->
        <image-viewer :z-index="imageViewerZIndex" :url-list="imageViewerPreviewSrcList" ref="imageViewer" v-show="imageViewerVisible" :on-close="closeImageViewerHandle"/>
    </el-card>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinMultiTagsModule from '@/mixins/multi-tags-module'
import mixinFormModule from '@/mixins/form-module'
import mixinQuillModule from '@/mixins/quill-module'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'
import { removeEmptyChildren } from '@/utils'

export default {
  inject: ['refresh'],
  mixins: [mixinBaseModule, mixinMultiTagsModule, mixinFormModule, mixinQuillModule],
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
        attrGroups: [],
        specs: '',
        specType: 0,
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
      this.init()
    }
  },
  computed: {
    dataRule () {
      var validateContent = (rule, value, callback) => {
        if (this.quillEditor.getLength() <= 1) {
          return callback(new Error(this.$t('validate.required')))
        }
        callback()
      }
      return {
        storeId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        specType: [
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
        ],
        content: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' },
          { validator: validateContent, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.formLoading = true
      this.visible = true
      this.$nextTick(() => {
        if (this.step === '1') {
          // 详情
          this.tags = []
          this.tagInputVisible = false
          this.tagInputValue = ''
          this.resetForm()
          Promise.all([
            this.getBrandList(''),
            // this.getSupplierList(''),
            this.getSpuCategoryList('')
          ]).then(() => {
            this.initFormData()
          })
        } else if (this.step === '2') {
          // 图片
          this.initUpload()
          this.initFormData()
        } else if (this.step === '3') {
          // 详情
          this.resetForm()
          this.quillEditorHandle()
          this.initUpload()
          this.initFormData()
        } else if (this.step === '4') {
          // 参数
          this.initFormData()
        }
      })
    },
    // 跳转步骤
    onStep (result) {
      if (!this.dataForm.id) {
        this.$message.error('基本信息保存后才能切换')
        return false
      }
      if (result === 100) {
        // 下一步
        this.step = String(Number.parseInt(this.step) + 1)
      } else if (result === -100) {
        // 上一步
        this.step = String(Number.parseInt(this.step) - 1)
      } else {
        this.step = String(result)
      }
      this.$router.replace({ name: this.$route.name, query: { id: this.dataForm.id, step: this.step } })
      // 跳转后刷新一下，激活activated
      this.refresh()
    },
    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      if (this.step === '1') {
        // 详情
        // 分割关键词
        if (this.dataForm.tags) {
          this.tags = this.dataForm.tags.split(',').filter(item => item !== '')
        }
      } else if (this.step === '2') {
        // 图片
        // 赋值图片
        this.setUploadFileList(this.dataForm.imgs)
      } else if (this.step === '3') {
        // 详情
        // set富文本编辑器
        this.quillEditor.root.innerHTML = this.dataForm.content
      } else if (this.step === '4') {
        // 参数管理
        if (this.dataForm.attrs) {
          this.dataForm.attrGroups = JSON.parse(this.dataForm.attrs)
        } else {
          this.dataForm.attrGroups = []
        }
      }
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      if (this.step === '1') {
        this.dataForm.tags = this.tags.join(',')
      } else if (this.step === '2') {
        // 图片
        this.dataForm.imgs = this.getUploadFileString()
      } else if (this.step === '3') {
        // 图文详情
        this.dataForm.content = this.quillEditor.root.innerHTML
      } else if (this.step === '4') {
        // 参数管理
        this.dataForm.attrs = JSON.stringify(this.dataForm.attrGroups)
      }
      this.dataFormSubmitParam = this.dataForm
      return true
    },
    // 表单提交成功
    onFormSubmitSuccess (res) {
      // 跳到下一步
      this.dataForm.id = res.data.id
      this.onStep(100)
      // 弹出提示框
      this.$message({
        message: '保存成功',
        type: 'success',
        duration: 500,
        onClose: () => {
        }
      })
    },
    // 插入参数组
    addAttrGroup (index) {
      // 插入指定位置
      this.dataForm.attrGroups.splice(index, 0, { 'name': '', 'items': [ { 'name': '', 'value': '' } ] })
    },
    // 删除参数组
    removeAttrGroup (item) {
      const index = this.dataForm.attrGroups.indexOf(item)
      if (index !== -1) {
        this.dataForm.attrGroups.splice(index, 1)
      }
    },
    // 插入参数
    addAttr (attrGroup, index) {
      // 插入指定位置
      attrGroup.items.splice(index, 0, { 'name': '', 'value': '' })
    },
    // 删除参数
    removeAttr (attrGroup, item) {
      const index = attrGroup.items.indexOf(item)
      if (index !== -1) {
        attrGroup.items.splice(index, 1)
      }
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
