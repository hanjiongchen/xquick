<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-sys__region}">
      <el-form :inline="true" :model="dataForm">
        <el-form-item>
          <el-input v-model="dataForm.name" placeholder="输入名称" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button @click="queryDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('sys:region:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('sys:region:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" style="width: 100%;" row-key="id" lazy :load="load">
        <el-table-column prop="name" label="区域名称" header-align="center" align="left"/>
        <el-table-column prop="id" label="区域编码" header-align="center" align="center"/>
        <el-table-column prop="code" label="区域邮编" header-align="center" align="center"/>
        <el-table-column prop="levelName" label="区域级别" header-align="center" align="center"/>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('sys:region:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
              <!-- 只有末级可以删除 -->
            <el-button v-if="$hasPermission('sys:region:delete')  && scope.row.hasChildren === false" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
         v-if="mixinViewModuleOptions.getDataListIsPage"
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
    </div>
  </el-card>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import AddOrUpdate from './region-add-or-update'
export default {
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getDataListURL: '/sys/region/list',
        getDataListIsPage: false,
        exportURL: '/sys/region/export',
        deleteURL: '/sys/region/delete',
        deleteIsBatch: true
      },
      dataForm: {
        id: '',
        pid: 0,
        withChildNum: true // 带上子节点数量
      }
    }
  },
  components: {
    AddOrUpdate
  },
  methods: {
    // 获取list之前的操作
    beforeGetDataList () {
      if (this.dataForm.name != null && this.dataForm.name !== '') {
        this.dataForm.pid = null
      } else {
        this.dataForm.pid = 0
      }
      return true
    },
    // list信息获取成功
    onGetListSuccess (res) {
      this.dataList = this.mixinViewModuleOptions.getDataListIsPage ? res.data.list : res.data
      this.total = this.mixinViewModuleOptions.getDataListIsPage ? res.data.total : 0
      for (let itm of this.dataList) {
        itm.hasChildren = itm.childNum > 0
      }
    },
    // 树节点展开懒加载，通过id获取该id下一级的所有节点
    load (tree, treeNode, resolve) {
      this.$http.get('/sys/region/list?withChildNum=true&pid=' + tree.id).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        // 解析children
        for (let itm of res.data) {
          itm.hasChildren = itm.childNum > 0
        }
        resolve(res.data)
      }).catch(() => {
      })
    }
  }
}
</script>
