<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-home" v-loading="loading">
      <!-- 全局数量 -->
      <data-rotate :option="option"></data-rotate>
      <!-- 图表 -->
      <el-row :gutter="32">
        <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
          </div>
        </el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script>
import DataRotate from '@/components/data-rotate'

export default {
  components: { DataRotate },
  data () {
    return {
      option: {
        span: 8,
        data: [
          {
            click: function (item) {
              alert(JSON.stringify(item))
            },
            count: '150',
            title: '新订单',
            icon: 'el-icon-warning',
            color: 'rgb(49, 180, 141)'
          }, {
            click: function (item) {
              alert(JSON.stringify(item))
            },
            count: '53%',
            title: '跳出率',
            icon: 'el-icon-view',
            color: '#00a65a'
          }, {
            click: function (item) {
              alert(JSON.stringify(item))
            },
            count: '44',
            title: '用户注册数',
            icon: 'el-icon-setting',
            color: '#f39c12'
          }
        ]
      },
      loading: true,
      // 统计数据
      dataForm: {
        orderCount: '0',
        userCount: '0'
      }
    }
  },
  created () {
    this.getHomeCount()
  },
  methods: {
    goRouter (path) {
      this.$router.push(path)
    },
    // 获取首页统计数据
    getHomeCount () {
      this.$http.get('/shop/home/count').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.dataForm = res.data
      }).catch(() => {
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
