<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-ba__exam-user}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.id" placeholder="id" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('ba:examUser:export')">
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('ba:examUser:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('ba:examUser:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="userId" label="用户id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="subjectType" label="检测类型 1 成人检测 2 孩子检测" header-align="center" align="center"></el-table-column>
        <el-table-column prop="result" label="检测结果" header-align="center" align="center"></el-table-column>
        <el-table-column prop="parentName" label="家长名字" header-align="center" align="center"></el-table-column>
        <el-table-column prop="childName" label="小孩名字" header-align="center" align="center"></el-table-column>
        <el-table-column prop="childClass" label="小孩年级" header-align="center" align="center"></el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('ba:examUser:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('ba:examUser:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-if="mixinListModuleOptions.getDataListIsPage"
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
    </div>
  </el-card>
</template>

<script>
import mixinListModule from '@/mixins/list-module'
import AddOrUpdate from './exam-user-add-or-update'

export default {
  mixins: [mixinListModule],
  components: { AddOrUpdate },
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/ba/examUser/page',
        getDataListIsPage: true,
        exportURL: '/ba/examUser/export',
        deleteURL: '/ba/examUser/delete',
        deleteIsBatch: true
      },
      dataForm: {
        id: ''
      }
    }
  }
}
</script>
