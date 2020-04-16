/**
 * sku 选择器 module
 *
 * @author Charles zhangchaoxu@gmail.com
 */
export default {
  data () {
    return {
      spuList: [],
      skuList: []
    }
  },
  methods: {
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
      return this.$http.get(`/shop/sku/list?spuId=` + id).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.skuList = res.data
        if (this.skuList.length > 0) {
          this.dataForm.skuId = this.skuList[0].id
        }
      }).catch(() => {
      })
    },
    // sku选中事件
    onSkuChangeHandle (id) {
      const item = this.skuList.filter(item => item.id === id)[0]
      if (item) {
        this.currentStock = item.stock
      }
    }
  }
}
