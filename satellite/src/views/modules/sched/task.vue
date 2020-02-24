<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-sched__task">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.name" :placeholder="$t('base.name')" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('sched:task:save')" type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('sched:task:delete')" type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('sched:task:pause')" type="danger" @click="pauseHandle()">{{ $t('schedule.pauseBatch') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('sched:task:resume')" type="danger" @click="resumeHandle()">{{ $t('schedule.resumeBatch') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('sched:task:run')" type="danger" @click="runHandle()">{{ $t('schedule.runBatch') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('sched:taskLog:info')" type="success" @click="logHandle()">{{ $t('schedule.log') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table
        v-loading="dataListLoading"
        :data="dataList"
        border
        @selection-change="dataListSelectionChangeHandle"
        @sort-change="dataListSortChangeHandle"
        style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="name" :label="$t('base.name')" header-align="center" align="center"></el-table-column>
        <el-table-column prop="param" :label="$t('base.param')" header-align="center" align="center"></el-table-column>
        <el-table-column prop="cron" label="cron" header-align="center" align="center"></el-table-column>
        <el-table-column prop="remark" :label="$t('base.remark')" header-align="center" align="center"></el-table-column>
        <el-table-column prop="status" :label="$t('base.status')" sortable="custom" header-align="center" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1" size="small">启用</el-tag>
            <el-tag v-else size="small" type="danger">停用</el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('sched:task:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('sched:task:pause')" type="text" size="small" @click="pauseHandle(scope.row.id)">{{ $t('schedule.pause') }}</el-button>
            <el-button v-if="$hasPermission('sched:task:resume')" type="text" size="small" @click="resumeHandle(scope.row.id)">{{ $t('schedule.resume') }}</el-button>
            <el-button v-if="$hasPermission('sched:task:run')" type="text" size="small" @click="runHandle(scope.row.id)">{{ $t('schedule.run') }}</el-button>
            <el-button v-if="$hasPermission('sched:task:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="limit"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="pageSizeChangeHandle"
        @current-change="pageCurrentChangeHandle">
      </el-pagination>
      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
      <!-- 弹窗, 日志列表 -->
      <log v-if="logVisible" ref="log"></log>
    </div>
  </el-card>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import AddOrUpdate from './task-add-or-update'
import Log from './task-log'

export default {
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getDataListURL: '/sched/task/page',
        getDataListIsPage: true,
        deleteURL: '/sched/task/delete',
        deleteIsBatch: true
      },
      dataForm: {
        name: ''
      },
      logVisible: false
    }
  },
  components: {
    AddOrUpdate,
    Log
  },
  methods: {
    // 暂停
    pauseHandle (id) {
      if (!id && this.dataListSelections.length <= 0) {
        return this.$message({
          message: this.$t('prompt.deleteBatch'),
          type: 'warning',
          duration: 500
        })
      }
      this.$confirm(this.$t('prompt.info', { 'handle': this.$t('schedule.pause') }), this.$t('prompt.title'), {
        confirmButtonText: this.$t('confirm'),
        cancelButtonText: this.$t('cancel'),
        type: 'warning'
      }).then(() => {
        this.$http.put('/sched/task/pause', id ? [id] : this.dataListSelections.map(item => item.id)).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.$message({
            message: this.$t('prompt.success'),
            type: 'success',
            duration: 500,
            onClose: () => {
              this.getDataList()
            }
          })
        }).catch(() => {})
      }).catch(() => {})
    },
    // 恢复
    resumeHandle (id) {
      if (!id && this.dataListSelections.length <= 0) {
        return this.$message({
          message: this.$t('prompt.deleteBatch'),
          type: 'warning',
          duration: 500
        })
      }
      this.$confirm(this.$t('prompt.info', { 'handle': this.$t('schedule.resume') }), this.$t('prompt.title'), {
        confirmButtonText: this.$t('confirm'),
        cancelButtonText: this.$t('cancel'),
        type: 'warning'
      }).then(() => {
        this.$http.put('/sched/task/resume', id ? [id] : this.dataListSelections.map(item => item.id)).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.$message({
            message: this.$t('prompt.success'),
            type: 'success',
            duration: 500,
            onClose: () => {
              this.getDataList()
            }
          })
        }).catch(() => {})
      }).catch(() => {})
    },
    // 执行
    runHandle (id) {
      if (!id && this.dataListSelections.length <= 0) {
        return this.$message({
          message: this.$t('prompt.deleteBatch'),
          type: 'warning',
          duration: 500
        })
      }
      this.$confirm(this.$t('prompt.info', { 'handle': this.$t('schedule.run') }), this.$t('prompt.title'), {
        confirmButtonText: this.$t('confirm'),
        cancelButtonText: this.$t('cancel'),
        type: 'warning'
      }).then(() => {
        this.$http.put('/sched/task/run', id ? [id] : this.dataListSelections.map(item => item.id)).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.$message({
            message: this.$t('prompt.success'),
            type: 'success',
            duration: 500,
            onClose: () => {
              this.getDataList()
            }
          })
        }).catch(() => {})
      }).catch(() => {})
    },
    // 日志列表
    logHandle () {
      this.logVisible = true
      this.$nextTick(() => {
        this.$refs.log.init()
      })
    }
  }
}
</script>
