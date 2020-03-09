<template>
    <el-dialog title="位置查看" :visible.sync="visible" append-to-body :close-on-click-modal="false" :close-on-press-escape="false" :fullscreen="fullscreen" custom-class="el-dialog-nopadding">
        <div class="amap-page-container">
            <el-amap vid="amap-location-view" :zoom="zoom" :center="marker.position" class="amap-map" :showIndoorMap="false">
                <el-amap-marker vid="amap-marker-location-view" :position="marker.position" :label="marker.address" :events="marker.events" :visible="visible && marker.visible" :draggable="marker.draggable"/>
            </el-amap>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
            <el-button type="primary" @click="setFullScreen">{{ fullscreen ? '缩小':'放大' }}</el-button>
        </div>
    </el-dialog>
</template>

<script>

export default {
  data () {
    return {
      // 对话框是否全屏
      fullscreen: false,
      visible: false,
      zoom: 0,
      marker: {
        position: [120.153576, 30.287459],
        visible: true,
        draggable: false,
        address: ''
      }
    }
  },
  methods: {
    init () {
      this.visible = true
      this.fullscreen = false
      this.zoom = 14
      this.$nextTick(() => {

      })
    },
    setFullScreen () {
      this.fullscreen = !this.fullscreen
    }
  }
}
</script>

<style>
.amap-page-container {
    position: relative;
    width: 100%;
    height: 500px;
}
</style>
