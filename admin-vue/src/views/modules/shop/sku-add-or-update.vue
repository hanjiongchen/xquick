<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="商品id" prop="spuId">
          <el-input v-model="dataForm.spuId" placeholder="商品id"></el-input>
      </el-form-item>
          <el-form-item label="商品图片,空则使用procut表的imgs" prop="spuImgs">
          <el-input v-model="dataForm.spuImgs" placeholder="商品图片,空则使用procut表的imgs"></el-input>
      </el-form-item>
          <el-form-item label="市场价" prop="marketPrice">
          <el-input v-model="dataForm.marketPrice" placeholder="市场价"></el-input>
      </el-form-item>
          <el-form-item label="销售价" prop="salePrice">
          <el-input v-model="dataForm.salePrice" placeholder="销售价"></el-input>
      </el-form-item>
          <el-form-item label="是否默认项" prop="isDefault">
          <el-input v-model="dataForm.isDefault" placeholder="是否默认项"></el-input>
      </el-form-item>
          <el-form-item label="当前库存" prop="stock">
          <el-input v-model="dataForm.stock" placeholder="当前库存"></el-input>
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
        dataFormSaveURL: `/shop/sku/save`,
        dataFormUpdateURL: `/shop/sku/update`,
        dataFormInfoURL: `/shop/sku/info?id=`
      },
      dataForm: {
        id: '',
        spuId: '',
        spuImgs: '',
        marketPrice: '',
        salePrice: '',
        isDefault: '',
        stock: '',
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
        spuId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        spuImgs: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        marketPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        salePrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        isDefault: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        stock: [
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
