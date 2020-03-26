<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
        <el-row>
            <el-col :span="12">
                <el-form-item label="商铺" prop="storeId">
                    <el-autocomplete
                            class="w-percent-100"
                            value-key="name"
                            v-model="dataForm.storeName"
                            :fetch-suggestions="getStoreList"
                            placeholder="请选择商铺"
                            @select="item => dataForm.storeId = item.id"/>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="dataForm.name" placeholder="名称"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="类型" prop="type">
                    <el-radio-group v-model="dataForm.type">
                        <el-radio-button :label="1">满减券</el-radio-button>
                    </el-radio-group>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="状态" prop="status">
                    <el-radio-group v-model="dataForm.status">
                        <el-radio-button :label="1">已激活</el-radio-button>
                        <el-radio-button :label="0">未激活</el-radio-button>
                    </el-radio-group>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="是否可以积分兑换" prop="pointExchangeEnable">
                    <el-radio-group v-model="dataForm.pointExchangeEnable">
                        <el-radio-button :label="1">是</el-radio-button>
                        <el-radio-button :label="0">否</el-radio-button>
                    </el-radio-group>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="兑换所需积分" prop="pointExchange">
                    <el-input-number controls-position="right" :min="0" v-model="dataForm.pointExchange" placeholder="兑换所需积分" class="w-percent-100"/>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="有效期开始" prop="validStartTime">
            <el-date-picker
                    v-model="dateRange"
                    type="datetimerange"
                    @change="dateRangeChangeHandle"
                    :picker-options="dateRangePickerOptions"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :range-separator="$t('datePicker.range')"
                    :start-placeholder="$t('datePicker.start')"
                    :end-placeholder="$t('datePicker.end')">
            </el-date-picker>
        </el-form-item>
        <el-row>
            <el-col :span="12">
                <el-form-item label="当前数量" prop="stock">
                    <el-input-number controls-position="right" :min="0" v-model="dataForm.stock" placeholder="当前数量" class="w-percent-100"/>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="描述" prop="content">
            <el-input v-model="dataForm.content" placeholder="描述" type="textarea"></el-input>
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
import mixinBaseModule from '@/mixins/base-module'
export default {
  mixins: [mixinFormModule, mixinBaseModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/coupon/save`,
        dataFormUpdateURL: `/shop/coupon/update`,
        dataFormInfoURL: `/shop/coupon/info?id=`
      },
      dateRange: null,
      dataForm: {
        id: '',
        storeId: '',
        name: '',
        content: '',
        type: 1,
        validStartTime: '',
        validEndTime: '',
        status: 0,
        pointExchangeEnable: 0,
        pointExchange: '',
        stock: 0,
        priceExpress: '',
        storeName: ''
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
        ]
      }
    }
  },
  methods: {
    init () {
      this.formLoading = true
      this.visible = true
      this.dateRange = null
      this.$nextTick(() => {
        this.resetForm()
        Promise.all([
          // this.getStoreList()
        ]).then(() => {
          this.initFormData()
        })
      })
    },
    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      // 赋值日期选择器
      this.dateRange = [this.dataForm.validStartTime, this.dataForm.validEndTime]
    },
    // 商铺列表
    getStoreList (name, callback) {
      return this.$http.get(`/shop/store/list?name=` + name).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        callback(res.data)
      }).catch(() => {
      })
    },
    // 时间区间选择器变化
    dateRangeChangeHandle (value) {
      if (value !== null && value.length === 2) {
        this.dataForm.validStartTime = value[0]
        this.dataForm.validEndTime = value[1]
      } else {
        this.dataForm.validStartTime = ''
        this.dataForm.validEndTime = ''
      }
    }
  }
}
</script>
