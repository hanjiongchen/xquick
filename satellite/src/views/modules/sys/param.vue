<template>
    <el-card shadow="never" class="aui-card--fill">
        <div class="mod-sys__param">
            <el-form :inline="true" :model="dataForm">
                <el-form-item>
                    <el-input v-model="dataForm.code" :placeholder="$t('base.code')" clearable/>
                </el-form-item>
                <el-form-item>
                    <el-button @click="queryDataList()">{{ $t('query') }}</el-button>
                </el-form-item>
                <el-form-item v-if="$hasPermission('sys:param:save')">
                    <el-button type="primary" @click="addOrUpdateHandle()">{{ $t('add') }}</el-button>
                </el-form-item>
                <el-form-item v-if="$hasPermission('sys:param:delete')">
                    <el-button type="danger" @click="deleteHandle()">{{ $t('deleteBatch') }}</el-button>
                </el-form-item>
            </el-form>
            <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle" style="width: 100%;">
                <el-table-column type="selection" header-align="center" align="center" width="50"/>
                <el-table-column prop="code" :label="$t('base.code')" header-align="center" align="center" width="300"/>
                <el-table-column prop="remark" :label="$t('base.remark')" header-align="center" align="center" width="200"/>
                <el-table-column prop="content" :label="$t('base.content')" header-align="center" align="center" class-name="nowrap">
                    <template slot-scope="scope">
                        <el-link type="primary" @click="jsonViewHandle(scope.row.content)" :underline="false">{{ scope.row.content }}</el-link>
                    </template>
                </el-table-column>
                <el-table-column :label="$t('handle')" fixed="right" header-align="center" align="center" width="150">
                    <template slot-scope="scope">
                        <el-button v-if="$hasPermission('sys:param:update')" type="text" size="small" @click="editHandle(scope.row.id, scope.row.code)">{{ $t('update') }}</el-button>
                        <el-button v-if="$hasPermission('sys:param:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">{{ $t('delete') }}</el-button>
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
            <!-- 弹窗, 新增 / 修改 -->
            <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"/>
            <!-- 弹窗, oss配置 -->
            <oss-config v-if="ossConfigVisible" ref="ossConfig" @refreshDataList="getDataList"/>
            <!-- 弹窗, 登录配置 -->
            <login-config v-if="loginConfigVisible" ref="loginConfig" @refreshDataList="getDataList"/>
            <!-- 弹窗, App关于我们配置 -->
            <app-about-config v-if="appAboutConfigVisible" ref="appAboutConfig" @refreshDataList="getDataList"/>
            <!-- 弹窗, App跑马灯配置 -->
            <app-banner-config v-if="appBannerConfigVisible" ref="appBannerConfig" @refreshDataList="getDataList"/>
            <!-- 弹窗, App启动页配置 -->
            <app-loading-config v-if="appLoadingConfigVisible" ref="appLoadingConfig" @refreshDataList="getDataList"/>
            <!-- 弹窗, App客服配置-->
            <app-service-config v-if="appServiceConfigVisible" ref="appServiceConfig" @refreshDataList="getDataList"/>
            <!-- 弹窗, App版本号配置-->
            <app-version-config v-if="appVersionConfigVisible" ref="appVersionConfig" @refreshDataList="getDataList"/>
        </div>
    </el-card>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinViewModule from '@/mixins/view-module'

import AddOrUpdate from './param-add-or-update'
import OssConfig from './param-oss-config'
import LoginConfig from './param-login-config'
import AppAboutConfig from './params-app-about-config'
import AppBannerConfig from './params-app-banner-config'
import AppLoadingConfig from './params-app-loading-config'
import AppServiceConfig from './params-app-service-config'
import AppVersionConfig from './params-app-version-config'

export default {
  mixins: [mixinBaseModule, mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        activatedIsNeed: false,
        getDataListURL: '/sys/param/page',
        getDataListIsPage: true,
        deleteURL: '/sys/param/delete',
        deleteIsBatch: true
      },
      // 登录配置
      loginConfigVisible: false,
      // 云存储配置
      ossConfigVisible: false,
      // App关于我们配置
      appAboutConfigVisible: false,
      // App跑马灯配置
      appBannerConfigVisible: false,
      // App启动页配置
      appLoadingConfigVisible: false,
      // App客服配置
      appServiceConfigVisible: false,
      // App版本号配置
      appVersionConfigVisible: false,
      dataForm: {
        code: ''
      }
    }
  },
  activated () {
    if (this.$route.query.code) {
      this.dataForm.code = this.$route.query.code
    }
    if (this.$route.params.code) {
      this.dataForm.code = this.$route.params.code
    }
    this.getDataList()
  },
  methods: {
    // 修改
    editHandle (id, code) {
      if (code.startsWith('LOGIN_CONFIG_KEY')) {
        this.loginConfigVisible = true
        this.$nextTick(() => {
          this.$refs.loginConfig.dataForm.id = id
          this.$refs.loginConfig.init()
        })
      } else if (code === 'SMS_CONFIG_KEY') {
        this.smsConfigVisible = true
        this.$nextTick(() => {
          this.$refs.smsConfig.dataForm.id = id
          this.$refs.smsConfig.init()
        })
      } else if (code.startsWith('OSS_CONFIG_KEY')) {
        this.ossConfigVisible = true
        this.$nextTick(() => {
          this.$refs.ossConfig.dataForm.id = id
          this.$refs.ossConfig.init()
        })
      } else if (code === 'APP_ABOUY_CONFIG_KEY') {
        this.appAboutConfigVisible = true
        this.$nextTick(() => {
          this.$refs.appAboutConfig.dataForm.id = id
          this.$refs.appAboutConfig.init()
        })
      } else if (code === 'APP_BANNER_CONFIG_KEY') {
        this.appBannerConfigVisible = true
        this.$nextTick(() => {
          this.$refs.appBannerConfig.dataForm.id = id
          this.$refs.appBannerConfig.init()
        })
      } else if (code === 'APP_LOADING_CONFIG_KEY') {
        this.appLoadingConfigVisible = true
        this.$nextTick(() => {
          this.$refs.appLoadingConfig.dataForm.id = id
          this.$refs.appLoadingConfig.init()
        })
      } else if (code === 'APP_SERVICE_CONFIG_KEY') {
        this.appServiceConfigVisible = true
        this.$nextTick(() => {
          this.$refs.appServiceConfig.dataForm.id = id
          this.$refs.appServiceConfig.init()
        })
      } else if (code === 'APP_VERSION_CONFIG_KEY') {
        this.appVersionConfigVisible = true
        this.$nextTick(() => {
          this.$refs.appVersionConfig.dataForm.id = id
          this.$refs.appVersionConfig.init()
        })
      } else {
        this.addOrUpdateHandle(id)
      }
    }
  },
  components: {
    LoginConfig,
    OssConfig,
    AddOrUpdate,
    AppAboutConfig,
    AppBannerConfig,
    AppLoadingConfig,
    AppServiceConfig,
    AppVersionConfig
  }
}
</script>
