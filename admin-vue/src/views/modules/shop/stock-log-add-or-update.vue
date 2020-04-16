<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-form-item label="类型" prop="type">
                <el-radio-group v-model="dataForm.type" size="small">
                    <el-radio :label="0">入库</el-radio>
                    <el-radio :label="1">出库</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="商品规格" prop="skuId">
                <el-input v-model="dataForm.showName" placeholder="选择商品规格" readonly>
                    <sku-pick class="small-button" slot="append" :spuId="dataForm.spuId" v-on:onSkuPicked="onSkuPicked"/>
                </el-input>
            </el-form-item>
            <el-divider v-if="dataForm.skuId">当前库存{{ currentStock }}</el-divider>
            <el-form-item label="入库数量" prop="inQty" v-if="dataForm.type === 0">
                <el-input-number v-model="dataForm.inQty" placeholder="输入入库数量" controls-position="right" :min="1" :max="99999999" class="w-percent-100"/>
            </el-form-item>
            <el-form-item label="出库数量" prop="outQty" v-if="dataForm.type === 1">
                <el-input-number v-model="dataForm.outQty" placeholder="输入出库数量" controls-position="right" :min="1" :max="currentStock" class="w-percent-100"/>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input v-model="dataForm.remark" placeholder="输入备注" type="textarea"/>
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
import SkuPick from './sku-pick'

export default {
  mixins: [mixinFormModule],
  components: { SkuPick },
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/stockLog/save`,
        dataFormUpdateURL: `/shop/stockLog/update`,
        dataFormInfoURL: `/shop/stockLog/info?id=`
      },
      // 当前库存
      currentStock: 0,
      dataForm: {
        id: '',
        spuId: '',
        spuName: '',
        skuId: '',
        skuName: '',
        type: 0,
        inQty: '',
        outQty: '',
        remark: '',
        showName: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
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
    },
    // 选中sku
    onSkuPicked (result) {
      if (result) {
        this.dataForm.skuId = result.sku.id
        this.dataForm.skuName = result.sku.name
        this.dataForm.spuName = result.spu.name
        this.dataForm.spuId = result.spu.id
        this.dataForm.showName = result.spu.name + ` ` + result.sku.name
        this.currentStock = result.sku.stock
      }
    }
  }
}
</script>
