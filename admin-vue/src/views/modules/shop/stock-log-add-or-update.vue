<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-form-item label="类型" prop="type">
                <el-radio-group v-model="dataForm.type" size="small">
                    <el-radio :label="0">入库</el-radio>
                    <el-radio :label="1">出库</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="商品" prop="spuId">
                <el-select v-model="dataForm.spuId" filterable placeholder="请选择商品" class="w-percent-100" @select="getSkuList('')">
                    <el-option v-for="item in spuList" :key="item.id" :label="item.name" :value="item.id">
                        <span style="float: left">{{ item.name }}</span>
                        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sn }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="规格" prop="skuId">
                <el-select v-model="dataForm.skuId" filterable placeholder="请选择商品规格" class="w-percent-100">
                    <el-option v-for="item in skuList" :key="item.id" :label="item.name" :value="item.id">
                        <span style="float: left">{{ item.name }}</span>
                        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sn }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="入库数量" prop="inQty" v-if="dataForm.type === 0">
                <el-input-number v-model="dataForm.inQty" placeholder="输入入库数量" controls-position="right" :min="1" :max="99999999" class="w-percent-100"/>
            </el-form-item>
            <el-form-item label="出库数量" prop="outQty" v-if="dataForm.type === 1">
                <el-input-number v-model="dataForm.outQty" placeholder="输入出库数量" controls-position="right" :min="1" :max="99999999" class="w-percent-100"/>
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
      spuList: [],
      skuList: [],
      dataForm: {
        id: '',
        spuId: '',
        skuId: '',
        type: 0,
        inQty: '',
        outQty: ''
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
        Promise.all([
          this.getSpuList('')
        ]).then(() => {
          this.initFormData()
        })
      })
    },
    // 商品列表
    getSpuList (search) {
      return this.$http.get(`/shop/spu/list?limit=20&search=` + search).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.spuList = res.data
      }).catch(() => {
      })
    },
    // 规格列表
    getSkuList (search) {
      return this.$http.get(`/shop/spu/list?limit=20&search=` + search + `&spuId=` + this.dataForm.spuId).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.skuList = res.data
      }).catch(() => {
      })
    }
  }
}
</script>
