<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-home" v-loading="loading">
      <!-- 全局数量 -->
      <el-row :gutter="40">
        <el-col :xs="12" :sm="12" :lg="6">
          <chart-card :loading="loading" title="总订单量" :total="dataForm.orderCount + '个'">
            <el-tooltip class="item" effect="dark" content="最近一段时间销售额" placement="top" slot="action">
              <i class="el-icon-info"/>
            </el-tooltip>
            <div>
            </div>
            <template slot="footer">今日订单量<span>100</span></template>
          </chart-card>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6">
          <chart-card :loading="loading" title="总会员数" :total="dataForm.userCount + '个'">
            <el-tooltip class="item" effect="dark" content="最近一段时间销售额" placement="top" slot="action">
              <i class="el-icon-info"/>
            </el-tooltip>
            <div>
            </div>
            <template slot="footer">7日新增会员<span>100</span></template>
          </chart-card>
        </el-col>
      </el-row>
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
import ChartCard from '@/components/ChartCard'

export default {
  data () {
    return {
      loading: true,
      // 统计数据
      dataForm: {
        orderCount: '0',
        userCount: '0'
      }
    }
  },
  components: {
    ChartCard
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
