<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-msg__sms-log}">
      <el-form :inline="true" :model="dataForm">
        <el-form-item class="small-item">
          <el-input v-model="dataForm.tplCode" placeholder="模板编码" clearable/>
        </el-form-item>
        <el-form-item class="small-item">
          <el-input v-model="dataForm.mobile" placeholder="手机号" minlength="11" maxlength="11" show-word-limit clearable/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="dataForm.content" placeholder="短信内容" clearable/>
        </el-form-item>
        <el-form-item class="small-item">
          <el-select v-model="dataForm.status" :placeholder="$t('base.status')" clearable>
            <el-option :label="$t('success')" :value="1"/>
            <el-option :label="$t('error')" :value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="queryDataList()">{{ $t('query') }}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="info" @click="exportHandle()">{{ $t('export') }}</el-button>
        </el-form-item>
        <el-form-item v-if="$hasPermission('msg:smsLog:delete')" >
          <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"/>
        <el-table-column prop="tplCode" label="模板编码" sortable="custom" header-align="center" align="center" min-width="120"/>
        <el-table-column prop="mobile" label="手机号" header-align="center" align="center" width="120"/>
        <el-table-column prop="content" label="短信内容" header-align="center" align="center" class-name="nowrap">
          <template slot-scope="scope">
            <el-link type="primary" @click="textViewHandle(scope.row.content)" :underline="false">{{ scope.row.content }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="params" label="参数" header-align="center" align="center" class-name="nowrap">
          <template slot-scope="scope">
            <el-link type="primary" @click="jsonViewHandle(scope.row.params)" :underline="false">{{ scope.row.params }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="status" :label="$t('base.status')" header-align="center" align="center" width="100">
          <template slot-scope="scope">
            <el-link @click="jsonViewHandle(scope.row.result)" v-if="scope.row.status === 1" type="success" size="small" :underline="false">{{$t('success')}}</el-link>
            <el-link @click="jsonViewHandle(scope.row.result)" v-else size="small" type="danger" :underline="false">{{$t('error')}}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="发送时间" header-align="center" align="center" width="180"/>
        <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('msg:smsLog:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
    </div>
  </el-card>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinViewModule from '@/mixins/view-module'

export default {
  mixins: [mixinBaseModule, mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getDataListURL: '/msg/smsLog/page',
        getDataListIsPage: true,
        exportURL: '/msg/smsLog/export',
        deleteURL: '/msg/smsLog/delete',
        deleteIsBatch: true
      },
      dataForm: {
        tplCode: '',
        mobile: '',
        status: null,
        content: ''
      }
    }
  }
}
</script>
