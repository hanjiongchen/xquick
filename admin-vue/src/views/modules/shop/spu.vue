<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-shop__spu}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.id" placeholder="id" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:spu:export')">
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:spu:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:spu:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="sn" label="编号" header-align="center" align="center"></el-table-column>
        <el-table-column prop="marketable" label="上架" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.delivery === 0" type="danger">下架</el-tag>
            <el-tag v-else-if="scope.row.marketable === 1" type="success">上架</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="top" label="置顶" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.top === 0" type="danger">不置顶</el-tag>
            <el-tag v-else-if="scope.row.top === 1" type="success">置顶</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type === 1">商品</el-tag>
            <el-tag v-else-if="scope.row.type === 2" type="success">积分兑换</el-tag>
            <el-tag v-else-if="scope.row.type === 3" type="danger">赠品</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" header-align="center" align="center" width="100"></el-table-column>
        <el-table-column prop="delivery" label="物流" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.delivery === 0" type="danger">不需要</el-tag>
            <el-tag v-else-if="scope.row.delivery === 1" type="success">需要</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="marketPrice" label="市场价" header-align="center" align="center"></el-table-column>
        <el-table-column prop="salePrice" label="售价" header-align="center" align="center"></el-table-column>
        <el-table-column prop="title" label="标题" header-align="center" align="center"></el-table-column>
        <el-table-column prop="tags" label="标签,逗号分隔" header-align="center" align="center"></el-table-column>
        <el-table-column prop="attrs" label="属性" header-align="center" align="center"></el-table-column>
        <el-table-column prop="specs" label="规格" header-align="center" align="center"></el-table-column>
        <el-table-column prop="status" label="状态" header-align="center" align="center"></el-table-column>
        <el-table-column prop="imgs" label="图片" header-align="center" align="center"></el-table-column>
        <el-table-column prop="score" label="评分" header-align="center" align="center"></el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('shop:spu:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('shop:spu:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
    </div>
  </el-card>
</template>

<script>
import mixinListModule from '@/mixins/list-module'
export default {
  mixins: [mixinListModule],
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/shop/spu/page',
        getDataListIsPage: true,
        exportURL: '/shop/spu/export',
        deleteURL: '/shop/spu/delete',
        deleteBatchURL: '/shop/spu/deleteBatch',
        deleteIsBatch: true
      },
      dataForm: {
        name: '',
        top: '',
        status: '',
        delivery: '',
        marketable: '',
        title: ''
      }
    }
  },
  methods: {
    // 新增/修改
    addOrUpdateHandle (id) {
      this.$router.push({ name: 'shop-spu-add-or-update', query: { id: id }, meta: { title: id ? '课程edit' : '课程add', isTab: true, isDynamic: true } })
    }
  }
}
</script>
