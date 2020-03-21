<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="商铺id" prop="storeId">
          <el-input v-model="dataForm.storeId" placeholder="商铺id"></el-input>
      </el-form-item>
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item label="描述" prop="content">
          <el-input v-model="dataForm.content" placeholder="描述"></el-input>
      </el-form-item>
          <el-form-item label="类型" prop="type">
          <el-input v-model="dataForm.type" placeholder="类型"></el-input>
      </el-form-item>
          <el-form-item label="有效期开始" prop="validStartTime">
          <el-input v-model="dataForm.validStartTime" placeholder="有效期开始"></el-input>
      </el-form-item>
          <el-form-item label="有效期结束" prop="validEndTime">
          <el-input v-model="dataForm.validEndTime" placeholder="有效期结束"></el-input>
      </el-form-item>
          <el-form-item label="状态 0 未激活 1 已激活" prop="status">
          <el-input v-model="dataForm.status" placeholder="状态 0 未激活 1 已激活"></el-input>
      </el-form-item>
          <el-form-item label="是否可以积分兑换" prop="pointExchangeEnable">
          <el-input v-model="dataForm.pointExchangeEnable" placeholder="是否可以积分兑换"></el-input>
      </el-form-item>
          <el-form-item label="兑换积分" prop="pointExchange">
          <el-input v-model="dataForm.pointExchange" placeholder="兑换积分"></el-input>
      </el-form-item>
          <el-form-item label="当前数量" prop="stock">
          <el-input v-model="dataForm.stock" placeholder="当前数量"></el-input>
      </el-form-item>
          <el-form-item label="最大商品价格" prop="maxPrice">
          <el-input v-model="dataForm.maxPrice" placeholder="最大商品价格"></el-input>
      </el-form-item>
          <el-form-item label="最大sku数量" prop="maxQty">
          <el-input v-model="dataForm.maxQty" placeholder="最大sku数量"></el-input>
      </el-form-item>
          <el-form-item label="最小商品价格" prop="minPrice">
          <el-input v-model="dataForm.minPrice" placeholder="最小商品价格"></el-input>
      </el-form-item>
          <el-form-item label="最小sku数量" prop="minQty">
          <el-input v-model="dataForm.minQty" placeholder="最小sku数量"></el-input>
      </el-form-item>
          <el-form-item label="价格计算表达式" prop="priceExpress">
          <el-input v-model="dataForm.priceExpress" placeholder="价格计算表达式"></el-input>
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
        dataFormSaveURL: `/shop/coupon/save`,
        dataFormUpdateURL: `/shop/coupon/update`,
        dataFormInfoURL: `/shop/coupon/info?id=`
      },
      dataForm: {
        id: '',
        storeId: '',
        name: '',
        content: '',
        type: '',
        validStartTime: '',
        validEndTime: '',
        status: '',
        pointExchangeEnable: '',
        pointExchange: '',
        stock: '',
        maxPrice: '',
        maxQty: '',
        minPrice: '',
        minQty: '',
        priceExpress: '',
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
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        content: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        type: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        validStartTime: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        validEndTime: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        status: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        pointExchangeEnable: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        pointExchange: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        stock: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        maxPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        maxQty: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        minPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        minQty: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        priceExpress: [
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
        this.initFormData()
      })
    }
  }
}
</script>
