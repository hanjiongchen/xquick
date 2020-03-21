<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="spu id" prop="spuId">
          <el-input v-model="dataForm.spuId" placeholder="spu id"></el-input>
      </el-form-item>
          <el-form-item label="sku id" prop="skuId">
          <el-input v-model="dataForm.skuId" placeholder="sku id"></el-input>
      </el-form-item>
          <el-form-item label="类型 0 入库 1 出库" prop="type">
          <el-input v-model="dataForm.type" placeholder="类型 0 入库 1 出库"></el-input>
      </el-form-item>
          <el-form-item label="入库数量" prop="inQty">
          <el-input v-model="dataForm.inQty" placeholder="入库数量"></el-input>
      </el-form-item>
          <el-form-item label="出库数量" prop="outQty">
          <el-input v-model="dataForm.outQty" placeholder="出库数量"></el-input>
      </el-form-item>
          <el-form-item label="出入库后库存" prop="stock">
          <el-input v-model="dataForm.stock" placeholder="出入库后库存"></el-input>
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
        dataFormSaveURL: `/shop/stockLog/save`,
        dataFormUpdateURL: `/shop/stockLog/update`,
        dataFormInfoURL: `/shop/stockLog/info?id=`
      },
      dataForm: {
        id: '',
        spuId: '',
        skuId: '',
        type: '',
        inQty: '',
        outQty: '',
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
        skuId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        type: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        inQty: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        outQty: [
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
