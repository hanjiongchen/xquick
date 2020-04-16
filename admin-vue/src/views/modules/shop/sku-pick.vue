<template>
    <el-button icon="el-icon-goods" @click="openPickHandle()">
        <el-dialog title="选择商品和规格" :visible.sync="visible"
                   append-to-body modal-append-to-body
                   :close-on-click-modal="false" :close-on-press-escape="false" @close="closeHandle" width="30%">
            <el-form ref="dataForm" label-width="50px">
                <el-form-item label="商品" prop="spuId">
                    <el-select v-model="spu.id" filterable remote :remote-method="getSpuList" placeholder="请选择商品" class="w-percent-100" @change="onSpuChangeHandle">
                        <el-option v-for="item in spuList" :key="item.id" :label="item.name" :value="item.id">
                            <span style="float: left">{{ item.name }}</span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sn }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="规格" prop="skuId">
                    <el-select v-model="sku.id" placeholder="请选择商品规格" class="w-percent-100" @change="onSkuChangeHandle">
                        <el-option v-for="item in skuList" :key="item.id" :label="item.name" :value="item.id">
                            <span style="float: left">{{ item.name }}</span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sn }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
                <el-button type="primary" @click="dataFormSubmitHandle()" :disabled="!sku.id || !spu.id">{{ $t('confirm') }}</el-button>
            </div>
        </el-dialog>
    </el-button>
</template>

<script>
export default {
  name: 'sku-pick',
  // 参数
  props: {
    // 请求码
    requestCode: {
      type: String,
      default: null
    },
    type: {
      type: String,
      default: 'single'
    }
  },
  data () {
    return {
      spuList: [],
      skuList: [],
      // 选中sku当前库存
      currentStock: 0,
      spu: {
        id: ''
      },
      sku: {
        id: ''
      },
      // 是否可见
      visible: false
    }
  },
  methods: {
    openPickHandle () {
      this.visible = true
      this.getSpuList('')
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
    // spu选中事件
    onSpuChangeHandle (id) {
      this.spu = this.spuList.filter(item => item.id === id)[0]
      return this.$http.get(`/shop/sku/list?spuId=` + id).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.skuList = res.data
        if (this.skuList.length > 0) {
          this.sku = this.skuList[0]
        }
      }).catch(() => {
      })
    },
    // sku选中事件
    onSkuChangeHandle (id) {
      this.sku = this.skuList.filter(item => item.id === id)[0]
      if (this.sku) {
        this.currentStock = this.sku.stock
      }
    },
    // 关闭时的回调
    closeHandle () {

    },
    dataFormSubmitHandle () {
      // 验证通过,提交表单
      this.$emit('onSkuPicked', { 'sku': this.sku, 'spu': this.spu }, this.requestCode)
      this.visible = false
    }
  }
}
</script>
