<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-shop__category}">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:category:export')">
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:category:save')">
          <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('shop:category:delete')">
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="logo" label="logo" header-align="center" align="center">
          <template slot-scope="scope">
            <el-image v-if="scope.row.logo" lazy class="table-img" :src="scope.row.logo.split(',')[0]" @click="imageViewerHandle(scope.row.logo.split(','))" fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="介绍" header-align="center" align="center"></el-table-column>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('shop:category:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">{{ $t('update') }}</el-button>
            <el-button v-if="$hasPermission('shop:category:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
import mixinViewModule from '@/mixins/view-module'
import mixinListModule from '@/mixins/list-module'
import AddOrUpdate from './category-add-or-update'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'

export default {
  mixins: [mixinListModule, mixinViewModule],
  data () {
    return {
      mixinListModuleOptions: {
        getDataListURL: '/shop/category/page',
        getDataListIsPage: true,
        exportURL: '/shop/category/export',
        deleteURL: '/shop/category/deleteBatch',
        deleteIsBatch: true
      },
      dataForm: {
        id: ''
      }
    }
  },
  components: {
    AddOrUpdate,
    ImageViewer
  }
}
</script>
