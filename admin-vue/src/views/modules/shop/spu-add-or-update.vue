<template>
  <div>
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="店铺id" prop="storeId">
          <el-input v-model="dataForm.storeId" placeholder="店铺id"></el-input>
      </el-form-item>
          <el-form-item label="品牌id" prop="brandId">
          <el-input v-model="dataForm.brandId" placeholder="品牌id"></el-input>
      </el-form-item>
          <el-form-item label="分类id" prop="categoryId">
          <el-input v-model="dataForm.categoryId" placeholder="分类id"></el-input>
      </el-form-item>
          <el-form-item label="供应商id" prop="supplierId">
          <el-input v-model="dataForm.supplierId" placeholder="供应商id"></el-input>
      </el-form-item>
          <el-form-item label="排序" prop="sort">
          <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>
          <el-form-item label="编号" prop="sn">
          <el-input v-model="dataForm.sn" placeholder="编号"></el-input>
      </el-form-item>
          <el-form-item label="是否需要物流" prop="delivery">
          <el-input v-model="dataForm.delivery" placeholder="是否需要物流"></el-input>
      </el-form-item>
          <el-form-item label="是否上架" prop="marketable">
          <el-input v-model="dataForm.marketable" placeholder="是否上架"></el-input>
      </el-form-item>
          <el-form-item label="是否置顶" prop="top">
          <el-input v-model="dataForm.top" placeholder="是否置顶"></el-input>
      </el-form-item>
          <el-form-item label="类型 1 商品 2 积分兑换 3 赠品" prop="type">
          <el-input v-model="dataForm.type" placeholder="类型 1 商品 2 积分兑换 3 赠品"></el-input>
      </el-form-item>
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item label="标题" prop="title">
          <el-input v-model="dataForm.title" placeholder="标题"></el-input>
      </el-form-item>
          <el-form-item label="标签,逗号分隔" prop="tags">
          <el-input v-model="dataForm.tags" placeholder="标签,逗号分隔"></el-input>
      </el-form-item>
          <el-form-item label="市场价" prop="marketPrice">
          <el-input v-model="dataForm.marketPrice" placeholder="市场价"></el-input>
      </el-form-item>
          <el-form-item label="售价" prop="salePrice">
          <el-input v-model="dataForm.salePrice" placeholder="售价"></el-input>
      </el-form-item>
          <el-form-item label="属性,不会影响价格、数量等业务" prop="attrs">
          <el-input v-model="dataForm.attrs" placeholder="属性,不会影响价格、数量等业务"></el-input>
      </el-form-item>
          <el-form-item label="规格,与sku关联" prop="specs">
          <el-input v-model="dataForm.specs" placeholder="规格,与sku关联"></el-input>
      </el-form-item>
          <el-form-item label="状态" prop="status">
          <el-input v-model="dataForm.status" placeholder="状态"></el-input>
      </el-form-item>
          <el-form-item label="点击数" prop="hits">
          <el-input v-model="dataForm.hits" placeholder="点击数"></el-input>
      </el-form-item>
          <el-form-item label="图片" prop="imgs">
          <el-input v-model="dataForm.imgs" placeholder="图片"></el-input>
      </el-form-item>
          <el-form-item label="图文内容" prop="content">
          <el-input v-model="dataForm.content" placeholder="图文内容"></el-input>
      </el-form-item>
          <el-form-item label="评分" prop="score">
          <el-input v-model="dataForm.score" placeholder="评分"></el-input>
      </el-form-item>
                </el-form>
    <template slot="footer">
      <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
    </template>
  </div>
</template>

<script>
import mixinFormModule from '@/mixins/form-module'
export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/spu/save`,
        dataFormUpdateURL: `/shop/spu/update`,
        dataFormInfoURL: `/shop/spu/info?id=`
      },
      dataForm: {
        id: '',
        storeId: '',
        brandId: '',
        categoryId: '',
        supplierId: '',
        sort: '',
        sn: '',
        delivery: '',
        marketable: '',
        top: '',
        type: '',
        name: '',
        title: '',
        tags: '',
        marketPrice: '',
        salePrice: '',
        attrs: '',
        specs: '',
        status: '',
        hits: '',
        imgs: '',
        content: '',
        score: '',
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
        content: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        score: [
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
    }
  }
}
</script>
