<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-shop__coupon}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.id" placeholder="id" clearable></el-input>
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
        <el-form-item v-if="$hasPermission('shop:coupon:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="id" label="id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="storeId" label="商铺id" header-align="center" align="center"></el-table-column>
        <el-table-column prop="name" label="名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="content" label="描述" header-align="center" align="center"></el-table-column>
        <el-table-column prop="type" label="类型" header-align="center" align="center"></el-table-column>
        <el-table-column prop="validStartTime" label="有效期开始" header-align="center" align="center"></el-table-column>
        <el-table-column prop="validEndTime" label="有效期结束" header-align="center" align="center"></el-table-column>
        <el-table-column prop="status" label="状态 0 未激活 1 已激活" header-align="center" align="center"></el-table-column>
        <el-table-column prop="pointExchangeEnable" label="是否可以积分兑换" header-align="center" align="center"></el-table-column>
        <el-table-column prop="pointExchange" label="兑换积分" header-align="center" align="center"></el-table-column>
        <el-table-column prop="stock" label="当前数量" header-align="center" align="center"></el-table-column>
        <el-table-column prop="maxPrice" label="最大商品价格" header-align="center" align="center"></el-table-column>
        <el-table-column prop="maxQty" label="最大sku数量" header-align="center" align="center"></el-table-column>
        <el-table-column prop="minPrice" label="最小商品价格" header-align="center" align="center"></el-table-column>
        <el-table-column prop="minQty" label="最小sku数量" header-align="center" align="center"></el-table-column>
        <el-table-column prop="priceExpress" label="价格计算表达式" header-align="center" align="center"></el-table-column>
        <el-table-column prop="createId" label="创建者" header-align="center" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" header-align="center" align="center"></el-table-column>
        <el-table-column prop="updateId" label="更新者" header-align="center" align="center"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" header-align="center" align="center"></el-table-column>
        <el-table-column prop="deleted" label="删除标记" header-align="center" align="center"></el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('shop:coupon:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('shop:coupon:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
      <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    </div>
  </el-card>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import AddOrUpdate from './coupon-add-or-update'
export default {
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getDataListURL: '/shop/coupon/page',
        getDataListIsPage: true,
        exportURL: '/shop/coupon/export',
        deleteURL: '/shop/coupon/delete',
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
