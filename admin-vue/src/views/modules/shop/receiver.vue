<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-shop__receiver}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item class="small-item">
          <el-input v-model="dataForm.userName" placeholder="用户" clearable/>
        </el-form-item>
        <el-form-item class="small-item">
          <el-input v-model="dataForm.consignee" placeholder="收件人" clearable/>
        </el-form-item>
        <el-form-item class="small-item">
          <el-input v-model="dataForm.mobile" placeholder="联系电话" clearable/>
        </el-form-item>
        <el-form-item class="small-item">
          <el-input v-model="dataForm.address" placeholder="详细地址" clearable/>
        </el-form-item>
        <el-form-item class="small-item">
          <el-select v-model="dataForm.defaultItem" placeholder="默认项" clearable>
            <el-option label="否" :value="0"/>
            <el-option label="是" :value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:receiver:export')">
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:receiver:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:receiver:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="userName" label="用户" header-align="center" align="center" width="120"></el-table-column>
        <el-table-column prop="regionName" label="区域名称" header-align="center" align="center" min-width="100"></el-table-column>
        <el-table-column prop="address" label="详细门牌号" header-align="center" align="center" min-width="120"/>
        <el-table-column prop="consignee" label="收件人" header-align="center" align="center" width="120"/>
        <el-table-column prop="mobile" label="收件人手机号" header-align="center" align="center" width="120"/>
        <el-table-column prop="defaultItem" label="默认项" header-align="center" align="center" width="80">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.defaultItem === 0" type="danger">否</el-tag>
            <el-tag v-else-if="scope.row.defaultItem === 1" type="success">是</el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('shop:receiver:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('shop:receiver:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
      <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"/>
    </div>
  </el-card>
</template>

<script>
import mixinListModule from '@/mixins/list-module'
import AddOrUpdate from './receiver-add-or-update'
export default {
  mixins: [mixinListModule],
  components: { AddOrUpdate },
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/shop/receiver/page',
        getDataListIsPage: true,
        exportURL: '/shop/receiver/export',
        deleteURL: '/shop/receiver/delete',
        deleteBatchURL: '/shop/receiver/deleteBatch',
        deleteIsBatch: true
      },
      dataForm: {
        userName: '',
        userId: '',
        defaultItem: '',
        regionName: '',
        regionCode: '',
        address: '',
        consignee: '',
        mobile: ''
      }
    }
  }
}
</script>
