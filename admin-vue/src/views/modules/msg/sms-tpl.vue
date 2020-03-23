<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-msg__sms-tpl}">
      <el-form :inline="true" :model="dataForm">
        <el-form-item class="small-item">
          <el-input v-model="dataForm.code" :placeholder="$t('base.code')" clearable/>
        </el-form-item>
        <el-form-item class="small-item">
          <el-input v-model="dataForm.name" :placeholder="$t('base.name')" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button @click="queryDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('msg:smsTpl:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('msg:smsTpl:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"/>
        <el-table-column prop="code" :label="$t('base.code')" header-align="center" align="center" width="180"/>
        <el-table-column prop="name" :label="$t('base.name')" header-align="center" align="center" width="150"/>
        <el-table-column prop="platform" label="平台" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.platform === 'aliyun'">阿里云</el-tag>
            <el-tag v-else-if="scope.row.platform === 'juhe'">聚合</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="config" label="平台配置" header-align="center" align="center" min-width="250" class-name="nowrap">
          <template slot-scope="scope">
            <el-link type="primary" @click="jsonViewHandle(scope.row.config)" :underline="false">{{ scope.row.config }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="短信内容" header-align="center" align="center" class-name="nowrap">
          <template slot-scope="scope">
            <el-link type="primary" @click="textViewHandle(scope.row.content)" :underline="false">{{ scope.row.content }}</el-link>
          </template>
        </el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('msg:smsLog:save')" type="text" size="small" @click="sendHandle(scope.row.code, scope.row.param)">{{ $t('base.send') }}</el-button>
            <el-button v-if="$hasPermission('msg:smsTpl:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('msg:smsTpl:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
      <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"/>
      <!-- 弹窗, 发送短信 -->
      <send v-if="sendVisible" ref="send" @refreshDataList="getDataList"/>
    </div>
  </el-card>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinListModule from '@/mixins/list-module'

import AddOrUpdate from './sms-tpl-add-or-update'
import Send from './sms-send'

export default {
  mixins: [mixinBaseModule, mixinListModule],
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/msg/smsTpl/page',
        getDataListIsPage: true,
        exportURL: '/msg/smsTpl/export',
        deleteURL: '/msg/smsTpl/delete',
        deleteBatchURL: '/msg/smsTpl/deleteBatch',
        deleteIsBatch: true
      },
      dataForm: {
        id: ''
      },
      // 短信发送
      sendVisible: false
    }
  },
  components: {
    AddOrUpdate,
    Send
  },
  methods: {
    // 发送短信
    sendHandle (tplCode, param) {
      this.sendVisible = true
      this.$nextTick(() => {
        this.$refs.send.dataForm.tplCode = tplCode
        this.$refs.send.init()
      })
    }
  }
}
</script>
