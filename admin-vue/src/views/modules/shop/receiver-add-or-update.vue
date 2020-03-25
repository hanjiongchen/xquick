<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-form-item label="用户id" prop="userId">
                <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
            </el-form-item>
            <el-form-item label="区域" prop="regionName">
                <el-input v-model="dataForm.regionName" placeholder="区域名称"></el-input>
            </el-form-item>
            <el-form-item label="详细门牌号" prop="address">
                <el-input v-model="dataForm.address" placeholder="详细门牌号">
                    <el-button slot="append" icon="el-icon-map-location" @click="mapLocationPickVisible = true"></el-button>
                    <amap-location-pick v-if="mapLocationPickVisible"></amap-location-pick>
                </el-input>
            </el-form-item>
            <el-form-item label="收件人" prop="consignee">
                <el-input v-model="dataForm.consignee" placeholder="收件人"></el-input>
            </el-form-item>
            <el-form-item label="邮编" prop="zipCode">
                <el-input v-model="dataForm.zipCode" placeholder="邮编"></el-input>
            </el-form-item>
            <el-form-item label="收件人手机号" prop="mobile">
                <el-input v-model="dataForm.mobile" placeholder="收件人手机号"></el-input>
            </el-form-item>
            <el-form-item label="默认项" prop="defaultItem">
                <el-input v-model="dataForm.defaultItem" placeholder="默认项"></el-input>
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
import AmapLocationPick from '@/components/amap-location-pick'

export default {
  mixins: [mixinFormModule],
  components: { AmapLocationPick },
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/receiver/save`,
        dataFormUpdateURL: `/shop/receiver/update`,
        dataFormInfoURL: `/shop/receiver/info?id=`
      },
      // 位置选择是否可见
      mapLocationPickVisible: false,
      dataForm: {
        id: '',
        userId: '',
        regionName: '',
        regionCode: '',
        address: '',
        consignee: '',
        zipCode: '',
        mobile: '',
        defaultItem: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        userId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        regionName: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        regionCode: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        address: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        consignee: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        zipCode: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        defaultItem: [
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
    /**
    * 挑选地址
    */
    pickAddressHandle () {
      console.log('pickAddressHandle')
    }
  }
}
</script>
