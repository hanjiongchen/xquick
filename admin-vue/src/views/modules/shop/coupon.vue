<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-shop__coupon}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.storeName" placeholder="店铺" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="dataForm.type" clearable placeholder="类型">
            <el-option label="满减券" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="dataForm.status" clearable placeholder="状态">
            <el-option label="未激活" value="0"></el-option>
            <el-option label="已激活" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:coupon:export')">
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:coupon:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column prop="storeName" label="商铺" header-align="center" align="center"></el-table-column>
        <el-table-column prop="name" label="名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="type" label="类型" header-align="center" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type === 1" size="small" type="success">满减券</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="validStartTime" label="有效期开始" header-align="center" align="center"></el-table-column>
        <el-table-column prop="validEndTime" label="有效期结束" header-align="center" align="center"></el-table-column>
        <el-table-column prop="status" label="状态" header-align="center" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 0" size="small" type="info">未激活</el-tag>
            <el-tag v-else-if="scope.row.status === 1" size="small" type="success">已激活</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="pointExchangeEnable" label="是否可以积分兑换" header-align="center" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 0" size="small" type="info">否</el-tag>
            <el-tag v-else-if="scope.row.status === 1" size="small" type="success">是</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="pointExchange" label="兑换所需积分" header-align="center" align="center"></el-table-column>
        <el-table-column prop="stock" label="当前数量" header-align="center" align="center"></el-table-column>
        <el-table-column prop="content" label="描述" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('shop:coupon:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('shop:coupon:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
import AddOrUpdate from './coupon-add-or-update'
export default {
  mixins: [mixinListModule],
  components: { AddOrUpdate },
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/shop/coupon/page',
        getDataListIsPage: true,
        exportURL: '/shop/coupon/export',
        deleteURL: '/shop/coupon/delete',
        deleteIsBatch: false
      },
      dataForm: {
        name: '',
        type: '',
        status: '',
        storeName: ''
      }
    }
  }
}
</script>
