<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="会员id" prop="userId">
          <el-input v-model="dataForm.userId" placeholder="会员id"></el-input>
      </el-form-item>
          <el-form-item label="spu id" prop="spuId">
          <el-input v-model="dataForm.spuId" placeholder="spu id"></el-input>
      </el-form-item>
          <el-form-item label="sku id" prop="skuId">
          <el-input v-model="dataForm.skuId" placeholder="sku id"></el-input>
      </el-form-item>
          <el-form-item label="商铺id" prop="storeId">
          <el-input v-model="dataForm.storeId" placeholder="商铺id"></el-input>
      </el-form-item>
          <el-form-item label="数量" prop="qty">
          <el-input v-model="dataForm.qty" placeholder="数量"></el-input>
      </el-form-item>
          <el-form-item label="状态0 未下单 1 已下单" prop="status">
          <el-input v-model="dataForm.status" placeholder="状态0 未下单 1 已下单"></el-input>
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
        dataFormSaveURL: `/shop/cart/save`,
        dataFormUpdateURL: `/shop/cart/update`,
        dataFormInfoURL: `/shop/cart/info?id=`
      },
      dataForm: {
        id: '',
        userId: '',
        spuId: '',
        skuId: '',
        storeId: '',
        qty: '',
        status: '',
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
        userId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        spuId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        skuId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        storeId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        qty: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        status: [
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
