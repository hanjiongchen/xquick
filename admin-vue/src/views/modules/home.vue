<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-home" v-loading="loading">
      <!-- 全局数量 -->
      <el-row :gutter="40" class="panel-group">
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="goRouter('cms-article')">
            <div class="card-panel-icon-wrapper icon-people">
              <i class="card-panel-icon el-icon-office-building"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">文章数量</div>
              <count-to :start-val="0" :end-val="homeCount.articleCount" :duration="2600" class="card-panel-num" />
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="goRouter('b-line')">
            <div class="card-panel-icon-wrapper icon-message">
              <i class="card-panel-icon el-icon-position"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">线路数量</div>
              <count-to :start-val="0" :end-val="homeCount.lineCount" :duration="3000" class="card-panel-num" />
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="goRouter('b-line-order')">
            <div class="card-panel-icon-wrapper icon-money">
              <i class="card-panel-icon el-icon-money"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">乘车次数</div>
              <count-to :start-val="0" :end-val="homeCount.lineOrderCount" :duration="3200" class="card-panel-num" />
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="goRouter('uc-user')">
            <div class="card-panel-icon-wrapper icon-shopping">
              <i class="card-panel-icon el-icon-user"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">用户数量</div>
              <count-to :start-val="0" :end-val="homeCount.userCount" :duration="3600" class="card-panel-num" />
            </div>
          </div>
        </el-col>
      </el-row>
      <!-- 图表 -->
      <el-row :gutter="32">
        <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
            <ve-pie :title="chart1Title" :data="homeCount.lineOrderPieData" :settings="chart1Settings"/>
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
            <ve-line :title="chart2Title" :data="homeCount.lineOrderDateLineData" :settings="chart2Settings"/>
          </div>
        </el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script>
import CountTo from 'vue-count-to'
import VeLine from 'v-charts/lib/line.common'
import VePie from 'v-charts/lib/pie.common'
import 'echarts/lib/component/title'

export default {
  data () {
    return {
      loading: true,
      // 统计数据
      homeCount: {
        articleCount: 0,
        userCount: 0
      },
      chart1Settings: { },
      chart1Title: {
        text: '线路定票占比',
        left: 'center',
        bottom: 20
      },
      chart2Settings: { },
      chart2Title: {
        text: '通勤定票数走势',
        left: 'center',
        bottom: 20
      }
    }
  },
  components: {
    CountTo,
    VeLine,
    VePie
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
      this.$http.get('/b/index/count').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        this.homeCount = res.data
      }).catch(() => {
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .panel-group {
    margin-top: 18px;

    .card-panel-col {
      margin-bottom: 32px;
    }

    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-people {
          background: #40c9c6;
        }

        .icon-message {
          background: #36a3f7;
        }

        .icon-money {
          background: #f4516c;
        }

        .icon-shopping {
          background: #34bfa3
        }
      }

      .icon-people {
        color: #40c9c6;
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-money {
        color: #f4516c;
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }

  @media (max-width:550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }
</style>
