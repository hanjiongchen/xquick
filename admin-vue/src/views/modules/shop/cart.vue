<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-shop__cart}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.id" placeholder="id" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:cart:export')">
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:cart:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:cart:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="id" label="id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="userId" label="会员id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="spuId" label="spu id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="skuId" label="sku id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="storeId" label="商铺id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="qty" label="数量" header-align="center" align="center"></el-table-column>
        <el-table-column prop="status" label="状态0 未下单 1 已下单" header-align="center" align="center"></el-table-column>
        <el-table-column prop="createId" label="创建者" header-align="center" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" header-align="center" align="center"></el-table-column>
        <el-table-column prop="updateId" label="更新者" header-align="center" align="center"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" header-align="center" align="center"></el-table-column>
        <el-table-column prop="deleted" label="删除标记" header-align="center" align="center"></el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('shop:cart:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('shop:cart:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
import AddOrUpdate from './cart-add-or-update'
export default {
  mixins: [mixinListModule],
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/shop/cart/page',
        getDataListIsPage: true,
        exportURL: '/shop/cart/export',
        deleteURL: '/shop/cart/delete',
        deleteIsBatch: true
      },
      dataForm: {
        id: ''
      }
    }
  },
  components: {
    AddOrUpdate
  }
}
</script>
