<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-msg__mail-tpl">
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
        <el-form-item v-if="$hasPermission('msg:mailTpl:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('msg:mailTpl:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"/>
        <el-table-column prop="code" :label="$t('base.code')" header-align="center" align="center" min-width="120"/>
        <el-table-column prop="name" :label="$t('base.name')" header-align="center" align="center" min-width="150"/>
        <el-table-column prop="title" :label="$t('base.title')" header-align="center" align="center"/>
        <el-table-column prop="content" :label="$t('base.content')" header-align="center" align="center" class-name="nowrap">
          <template slot-scope="scope">
            <el-link type="primary" @click="htmlViewHandle(scope.row.content)" :underline="false">{{ scope.row.content }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="senderUsername" label="发件人" header-align="center" align="center"/>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('msg:mailLog:save')" type="text" size="small" @click="sendHandle(scope.row.code, scope.row.titleParam, scope.row.contentParam)">{{ $t('base.send') }}</el-button>
            <el-button v-if="$hasPermission('msg:mailTpl:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('msg:mailTpl:delete')"  type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
      <!-- 弹窗, 发送邮件 -->
      <send v-if="sendVisible" ref="send"/>
    </div>
  </el-card>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinListModule from '@/mixins/list-module'
import AddOrUpdate from './mail-tpl-add-or-update'
import Send from './mail-send'
export default {
  mixins: [mixinBaseModule, mixinListModule],
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/msg/mailTpl/page',
        getDataListIsPage: true,
        deleteURL: '/msg/mailTpl/delete',
        deleteIsBatch: true
      },
      dataForm: {
        name: ''
      },
      sendVisible: false
    }
  },
  components: {
    AddOrUpdate,
    Send
  },
  methods: {
    // 发送邮件
    sendHandle (tplCode, titleParam, contentParam) {
      this.sendVisible = true
      this.$nextTick(() => {
        this.$refs.send.dataForm.tplCode = tplCode
        this.$refs.send.dataForm.titleParam = titleParam
        this.$refs.send.dataForm.contentParam = contentParam
        this.$refs.send.init()
      })
    }
  }
}
</script>
