<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-shop__store}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:store:export')">
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:store:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column prop="name" label="名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="logo" label="图标" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-image v-if="scope.row.logo" lazy class="table-img" :src="scope.row.logo.split(',')[0]" @click="imageViewerHandle(scope.row.logo.split(','))" fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column prop="tel" label="联系电话" header-align="center" align="center"></el-table-column>
        <el-table-column prop="sort" label="排序" header-align="center" align="center" width="100"></el-table-column>
        <el-table-column prop="status" label="状态" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 0" size="small" type="info">未审核</el-tag>
            <el-tag v-else-if="scope.row.status === 1" size="small" type="success">已审核</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="介绍" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('shop:store:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('shop:store:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
      <!-- 弹窗, 图片查看 -->
      <image-viewer :z-index="imageViewerZIndex" :url-list="imageViewerPreviewSrcList" ref="imageViewer" v-show="imageViewerVisible" :on-close="closeImageViewerHandle"/>
    </div>
  </el-card>
</template>

<script>
import mixinListModule from '@/mixins/list-module'
import AddOrUpdate from './store-add-or-update'
import mixinBaseModule from '@/mixins/base-module'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'
export default {
  mixins: [mixinListModule, mixinBaseModule],
  components: {
    AddOrUpdate,
    ImageViewer
  },
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/shop/store/page',
        getDataListIsPage: true,
        exportURL: '/shop/store/export',
        deleteURL: '/shop/store/delete',
        deleteIsBatch: false
      },
      dataForm: {
        name: ''
      }
    }
  }
}
</script>
