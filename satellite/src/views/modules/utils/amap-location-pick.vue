<template>
    <el-dialog title="位置选择" :visible.sync="visible" append-to-body :close-on-click-modal="false" :close-on-press-escape="false" :fullscreen="fullscreen" custom-class="el-dialog-nopadding">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <div class="amap-page-container">
                <el-amap-search-box ref="searchbox" class="search-box" :search-option="searchOption" :on-search-result="onSearchResult"/>
                <el-amap vid="amap-location-pick" :zoom="14" :center="mapCenter" :events="mapEvents" :showIndoorMap="false" style="margin-top: -45px;">
                    <el-amap-marker :position="dataForm.position" :visible="true" :draggable="true"/>
                </el-amap>
            </div>
            <el-row style="margin: 20px">
                <el-col :span="12">
                    <el-form-item label="区域" prop="regionNm">
                        <el-input v-model="dataForm.regionNm" placeholder="区域" disabled/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="位置" prop="address">
                        <el-input v-model="dataForm.address" placeholder="位置" maxlength="100" show-word-limit/>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
            <el-button type="info" @click="setFullScreen">{{ fullscreen ? '缩小':'放大' }}</el-button>
            <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
        </div>
    </el-dialog>
</template>

<script>
import mixinFormModule from '@/mixins/form-module'

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 对话框是否全屏
      fullscreen: false,
      // 搜索控制
      searchOption: {
        city: '宁波',
        citylimit: true
      },
      // 父页面key,当父页面多个pick的时候用于定位发起者
      key: '',
      mapCenter: [],
      dataForm: {
        regionNm: '',
        regionCd: '',
        address: '',
        position: [120.210649, 30.246071]
      },
      mapEvents: {
        click: (e) => {
          this.dataForm.position = [e.lnglat.lng, e.lnglat.lat]
          // 解析地址
          this.geoCoder()
        }
      }
    }
  },
  computed: {
    dataRule () {
      return {
        /* regionNm: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ], */
        address: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.formLoading = true
      this.visible = true
      this.fullscreen = false
      this.$nextTick(() => {
        // 清空搜索框
        document.getElementsByClassName('search-box-wrapper')[0].getElementsByTagName('input')[0].value = ''
        // 地图中心点移到marker
        this.mapCenter = this.dataForm.position
        this.formLoading = false
      })
    },
    setFullScreen () {
      this.fullscreen = !this.fullscreen
    },
    // 搜索回调函数
    onSearchResult (pois) {
      // 取第一个作为结果集的中心点
      if (pois && pois.length > 0) {
        this.mapCenter = [pois[0].lng, pois[0].lat]
        this.dataForm.position = [pois[0].lng, pois[0].lat]
        // 解析地址
        this.geoCoder()
      } else {
        this.$message.error('未找到搜索结果')
      }
    },
    // 解析经纬度地址
    geoCoder () {
      let self = this
      window.AMap.plugin('AMap.Geocoder', function () {
        new window.AMap.Geocoder({}).getAddress(self.dataForm.position, function (status, result) {
          if (status === 'complete' && result.info === 'OK') {
            // 获取省市区
            let regionNm = [result.regeocode.addressComponent.province, result.regeocode.addressComponent.city, result.regeocode.addressComponent.district]
            let adcode = result.regeocode.addressComponent.adcode
            let provinceCode = adcode.substring(0, 2) + '0000'
            let citycode = adcode.substring(0, 4) + '00'
            let regionCd = [provinceCode, citycode, result.regeocode.addressComponent.adcode]
            self.dataForm.regionNm = regionNm.join(',')
            self.dataForm.regionCd = regionCd.join(',')
            self.dataForm.address = result.regeocode.addressComponent.township + result.regeocode.addressComponent.street + result.regeocode.addressComponent.streetNumber
          } else {
            self.$message.error('地址解析失败:' + result)
          }
        })
      })
    },
    dataFormSubmitHandle () {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) {
          return false
        }
        // 验证通过,提交表单
        this.$emit('onLocationInfoResult', this.dataForm, this.key)
        this.visible = false
      })
    }
  }
}
</script>

<style>
.amap-page-container {
    width: 100%;
    height: 500px;
}

.search-box {
    top: 30px;
    left: 30px;
}
</style>
