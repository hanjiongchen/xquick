<template>
  <div v-loading.fullscreen.lock="loading" :element-loading-text="$t('loading')" :class="['aui-wrapper', { 'aui-sidebar--fold': $store.state.sidebarFold }]">
    <template v-if="!loading">
      <main-navbar/>
      <main-sidebar/>
      <div class="aui-content__wrapper">
        <main-content v-if="!$store.state.contentIsNeedRefresh"/>
      </div>
    </template>
  </div>
</template>

<script>
import MainNavbar from './main-navbar'
import MainSidebar from './main-sidebar'
import MainContent from './main-content'
import debounce from 'lodash/debounce'
export default {
  components: { MainNavbar, MainSidebar, MainContent },
  data () {
    return {
      loading: true
    }
  },
  watch: {
    $route: 'routeHandle'
  },
  provide () {
    return {
      // 刷新
      refresh () {
        this.$store.state.contentIsNeedRefresh = true
        this.$nextTick(() => {
          this.$store.state.contentIsNeedRefresh = false
        })
      }
    }
  },
  created () {
    this.windowResizeHandle()
    this.routeHandle(this.$route)
    Promise.all([
      this.getUserInfo()
      // this.getPermissions()
    ]).then(() => {
      this.loading = false
    })
  },
  methods: {
    // 窗口改变大小
    windowResizeHandle () {
      this.$store.state.sidebarFold = document.documentElement['clientWidth'] <= 992 || false
      window.addEventListener('resize', debounce(() => {
        this.$store.state.sidebarFold = document.documentElement['clientWidth'] <= 992 || false
      }, 150))
    },
    // 路由, 监听
    routeHandle (route) {
      if (!route.meta.isTab) {
        return false
      }
      let tab = this.$store.state.contentTabs.filter(item => item.name === route.name)[0]
      if (!tab) {
        // tab中不存在,添加
        tab = {
          ...window.SITE_CONFIG['contentTabDefault'],
          ...route.meta,
          'name': route.name,
          'params': { ...route.params },
          'query': { ...route.query }
        }
        this.$store.state.contentTabs = this.$store.state.contentTabs.concat(tab)
      } else {
        // tab中已存在
        if (tab.query !== route.query) {
          // query参数发生变化需要修改
          tab.query = route.query
        }
      }
      // 设置菜单当前项
      this.$store.state.sidebarMenuActiveName = tab.menuId
      this.$store.state.contentTabsActiveName = tab.name
    },
    // 获取当前用户信息
    getUserInfo () {
      return this.$http.get('/uc/user/userInfo').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        this.$store.state.user = res.data
      }).catch(() => {})
    }
    // 获取按钮权限
    /* getPermissions () {
      return this.$http.get('/uc/menu/permissions').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.toast)
        }
        window.SITE_CONFIG['permissions'] = res.data
      }).catch(() => {})
    } */
  }
}
</script>
