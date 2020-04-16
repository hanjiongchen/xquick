<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-form-item label="订单状态" prop="skuId">
                <el-select v-model="dataForm.status" filterable placeholder="请选择订单状态" class="w-percent-100">
                    <el-option label="待付款" :value="0"/>
                    <el-option label="待发货" :value="1"/>
                    <el-option label="待收货" :value="2"/>
                    <el-option label="待评价" :value="3"/>
                    <el-option label="退款" :value="-1"/>
                    <el-option label="售后" :value="-2"/>
                </el-select>
            </el-form-item>
            <el-row v-for="(product, index) in dataForm.products" :key="index" :prop="'product.' + index + '.value'">
                <el-col :span="8">
                    <el-form-item label="商品" prop="spuId">
                        <el-select v-model="product.spuId" filterable placeholder="请选择商品" class="w-percent-100" @change="getSkuList('',index)">
                            <el-option v-for="item in spuList" :key="item.id" :label="item.name" :value="item.id">
                                <span style="float: left">{{ item.name }}</span>
                                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sn }}</span>
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="规格" prop="skuId">
                        <el-select v-model="product.skuId" filterable placeholder="请选择商品规格" class="w-percent-100"  @change="getSkuInfo(product.skuId,index)">
                            <el-option v-for="item in product.skuList" :key="item.id" :label="item.name" :value="item.id">
                                <span style="float: left">{{ item.name }}</span>
                                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sn }}</span>
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="数量" prop="num">
                        <el-input-number v-model="product.num" placeholder="输入数量" controls-position="right" :min="1" :max="99999999" class="w-percent-100"/>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="单价" prop="price">
                        <el-input v-model="product.price" placeholder="商品单价"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-button @click.prevent="addStop(index + 1)" style="margin-left: 10px;" type="text">添加</el-button>
                    <el-button @click.prevent="removeStop(stop)" style="margin-left: 10px;color:#f56c6c;" type="text" v-if="index !== 0">删除</el-button>
                </el-col>
            </el-row>

            <!--<el-form-item label="请选择优惠券" prop="preferentialid">
                <el-select v-model="dataForm.spuId" filterable placeholder="请选择商品" class="w-percent-100" @change="getSkuList('')">
                    <el-option v-for="item in spuList" :key="item.id" :label="item.name" :value="item.id">
                        <span style="float: left">{{ item.name }}</span>
                        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sn }}</span>
                    </el-option>
                </el-select>
            </el-form-item>-->

            <el-row>

                <el-col :span="6">
                    <el-form-item label="配送费用" prop="freightPrice">
                        <el-input v-model="dataForm.freightPrice" placeholder="配送费用"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="优惠券减免" prop="couponPrice">
                        <el-input v-model="dataForm.couponPrice" placeholder="优惠券减免"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="用户积分减免" prop="integralPrice">
                        <el-input v-model="dataForm.integralPrice" placeholder="用户积分减免"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="团购优惠价减免" prop="grouponPrice">
                        <el-input v-model="dataForm.grouponPrice" placeholder="团购优惠价减免"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="商品总费用" prop="goodsPrice">
                        <el-input v-model="dataForm.goodsPrice" placeholder="订单费用"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="订单费用" prop="orderPrice">
                        <el-input v-model="dataForm.orderPrice" placeholder="订单费用"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="实付费用" prop="actualPrice">
                        <el-input v-model="dataForm.actualPrice" placeholder="实付费用"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="用户订单留言" prop="message">
                <el-input type="textarea" v-model="dataForm.message" placeholder="用户订单留言"></el-input>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="是否确认出库" prop="ischeckout">
                        <!--<el-input v-model="dataForm.ischeckout" placeholder="是否确认出库"></el-input>-->
                        <el-select v-model="dataForm.ischeckout" filterable placeholder="请选择出库状态" class="w-percent-100">
                            <el-option label="未出库" :value="0"/>
                            <el-option label="已出库" :value="1"/>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="checkdate" label="确认出库时间">
                        <el-date-picker v-model="dataForm.checkdate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="确认出库时间" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="快递名称" prop="expressname">
                        <el-input v-model="dataForm.expressname" placeholder="快递名称"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="快递单号" prop="expressno">
                        <el-input v-model="dataForm.expressno" placeholder="快递单号"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item prop="expressdelivedate" label="快递寄出日期">
                        <el-date-picker v-model="dataForm.expressdelivedate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="快递寄出日期" style="width: 100%"/>
                    </el-form-item>
                </el-col>
            </el-row>

            <!--<el-form-item label="选择用户" prop="userId">
                <el-select
                        v-model="value"
                        multiple
                        filterable
                        remote
                        reserve-keyword
                        placeholder="请输入关键词"
                        :remote-method="remoteMethod"
                        :loading="loading">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>-->
            <el-form-item label="请选择用户" prop="userId">
                <el-select v-model="dataForm.userId" filterable placeholder="请选择用户" class="w-percent-100" @change="getAddressList('')">
                    <el-option v-for="item in userList" :key="item.id" :label="item.username+'   '+item.mobile" :value="item.id">
                        <span style="float: left">{{ item.username }}</span>
                        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.mobile }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="请选择收货地址" prop="receiverId">
                <el-select v-model="dataForm.receiverId" filterable placeholder="请选择收货地址" class="w-percent-100" @change="getReceiveInfo(dataForm.receiverId)">
                    <el-option v-for="item in addressList" :key="item.id" :label="item.address" :value="item.id">
                        <span style="float: left">{{ item.address }}</span>
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="收件人" prop="receiverConsignee">
                <el-input v-model="dataForm.receiverConsignee" placeholder="收件人"></el-input>
            </el-form-item>
            <el-form-item label="收件人手机号" prop="receiverMobile">
                <el-input v-model="dataForm.receiverMobile" placeholder="收件人手机号"></el-input>
            </el-form-item>
            <el-form-item label="收件人地址" prop="receiverAddress">
                <el-input v-model="dataForm.receiverAddress" placeholder="收件人地址"></el-input>
            </el-form-item>
            <!--<el-form-item label="订单关闭时间" prop="endTime">
                <el-input v-model="dataForm.endTime" placeholder="订单关闭时间"></el-input>
            </el-form-item>-->
        </el-form>
        <template slot="footer">
            <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
            <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
        </template>
    </el-dialog>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinFormModule from '@/mixins/form-module'
export default {
  mixins: [mixinBaseModule, mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/order/save`,
        dataFormUpdateURL: `/shop/order/update`,
        dataFormInfoURL: `/shop/order/info?id=`
      },
      options: [],
      value: [],
      list: [],
      spuList: [],
      skuList: [],
      userList: [],
      addressList: [],
      dataForm: {
        id: '',
        status: '',
        no: '',
        userId: '',
        message: '',
        productIds: '',
        productCategory: '',
        productSkus: '',
        productNames: '',
        productAmount: '',
        productRemarks: '',
        productCovers: '',
        productPrices: '',
        goodsPrice: '',
        freightPrice: '',
        couponPrice: '',
        integralPrice: '',
        grouponPrice: '',
        orderPrice: '',
        actualPrice: '',
        prepayId: '',
        payTime: '',
        expressno: '',
        expressdelivedate: '',
        expressname: '',
        remarkmgr: '',
        checkdate: '',
        ischeckout: '',
        preferentialid: '',
        receiverId: '',
        receiverConsignee: '',
        receiverMobile: '',
        receiverAddress: '',
        endTime: '',
        products: [{}]
      }
    }
  },
  computed: {
    dataRule () {
      return {
        status: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        no: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        userId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        message: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productIds: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productCategory: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productSkus: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productNames: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productAmount: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productRemarks: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productCovers: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        productPrices: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        goodsPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        freightPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        couponPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        integralPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        grouponPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        orderPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        actualPrice: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        prepayId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        payTime: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        expressno: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        expressdelivedate: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        expressname: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        remarkmgr: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        checkdate: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        ischeckout: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        preferentialid: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        receiverId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        receiverConsignee: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        receiverMobile: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        receiverAddress: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        endTime: [
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
        Promise.all([
          this.getUserList('')
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
    getSkuList (search, item) {
      // 将原来二级菜单中的默认值清空
      return this.$http.get(`/shop/sku/list?limit=20&search=` + search + `&spuId=` + this.dataForm.products[item].spuId).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.dataForm.products[item].skuList = res.data
        this.$forceUpdate()
      }).catch(() => {
      })
    },
    // 插入站点
    addStop (index) {
      // 插入指定位置
      this.dataForm.products.splice(index, 0, {
        value: '',
        time: ''
      })
    },
    // 删除站点
    removeStop (item) {
      const index = this.dataForm.products.indexOf(item)
      if (index !== -1) {
        this.dataForm.products.splice(index, 1)
      }
    },
    // 获取规格详细信息并设置商品单价
    getSkuInfo (skuId, index) {
      const aa = this.dataForm.products[index].skuList
      for (var i = 0; i < aa.length; i++) {
        console.log(aa[i].id + '  ' + skuId)
        if (aa[i].id === skuId) {
          this.dataForm.products[index].price = aa[i].salePrice
        }
      }
    },
    // 获取用户列表
    getUserList (search) {
      return this.$http.get(`/uc/user/list?limit=20&search=` + search).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.userList = res.data
      }).catch(() => {
      })
    },
    // 根据用户选择，获取对应地址
    getAddressList (search) {
      // 将原来二级菜单中的默认值清空
      return this.$http.get(`/shop/receiver/list?limit=20&search=` + search + `&userId=` + this.dataForm.userId).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.addressList = res.data
      }).catch(() => {
      })
    },
    getReceiveInfo (search) {
      const aa = this.addressList
      for (var i = 0; i < aa.length; i++) {
        console.log(aa[i].id + '  ' + search)
        if (aa[i].id === search) {
          this.dataForm.receiverConsignee = aa[i].consignee
          this.dataForm.receiverMobile = aa[i].mobile
          this.dataForm.receiverAddress = aa[i].regionName + aa[i].address
        }
      }
    }
  }
}
</script>
