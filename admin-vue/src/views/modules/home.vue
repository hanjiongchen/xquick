<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-home" v-loading="loading">
      <!-- 全局数量 -->
      <data-rotate :option="countData"/>
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
      countData: {
        span: 8,
        data: [
          {
            count: '0',
            title: '订单数',
            icon: 'el-icon-news',
            color: '#F56C6C'
          }, {
            count: '0',
            title: '商品数',
            icon: 'el-icon-goods',
            color: '#E6A23C'
          }, {
            count: '0',
            title: '用户数',
            icon: 'el-icon-user',
            color: '#67C23A'
          }
        ]
      },
      loading: true
    }
  },
  created () {
    this.getHomeCount()
  },
  methods: {
    // 获取首页统计数据
    getHomeCount () {
      this.$http.get('/shop/home/count').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.countData.data[0].count = res.data.orderCount
        this.countData.data[0].click = () => {
          this.$router.push({ name: 'shop-order' })
        }
        this.countData.data[1].count = res.data.spuCount
        this.countData.data[1].click = () => {
          this.$router.push({ name: 'shop-spu' })
        }
        this.countData.data[2].count = res.data.userCount
        this.countData.data[2].click = () => {
          this.$router.push({ name: 'uc-member' })
        }
      }).catch(() => {
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
