<template>
  <el-submenu v-if="menu.children && menu.children.length >= 1" :index="menu.id" :popper-append-to-body="false">
    <template slot="title">
      <svg class="icon-svg aui-sidebar__menu-icon" aria-hidden="true"><use :xlink:href="`#${menu.icon}`"/></svg>
      <span>{{ menu.name }}</span>
    </template>
    <sub-menu v-for="item in menu.children" :key="item.id" :menu="item"/>
  </el-submenu>
  <el-menu-item v-else :index="menu.id" @click="gotoRouteHandle(menu)">
    <svg class="icon-svg aui-sidebar__menu-icon" aria-hidden="true"><use :xlink:href="`#${menu.icon}`"/></svg>
    <span>{{ menu.name }}</span>
  </el-menu-item>
</template>

<script>
import SubMenu from './main-sidebar-sub-menu'
import { isURL } from '@/utils/validate'

export default {
  name: 'sub-menu',
  props: {
    menu: {
      type: Object,
      required: true
    }
  },
  components: {
    SubMenu
  },
  methods: {
    /**
     * 跳转菜单
     */
    gotoRouteHandle (menu) {
      if (menu.urlNewBlank === 1) {
        // 菜单新页面打开,参考router/index.js
        // eslint-disable-next-line no-eval
        let URL = (menu.url || '').replace(/{{([^}}]+)?}}/g, (s1, s2) => eval(s2)) // URL支持{{ window.xxx }}占位符变量
        if (isURL(URL)) {
          // http开头的直接打开
          window.open(URL, '_blank')
        } else {
          window.open(menu.url.replace(/\//g, '-'), '_blank')
        }
      } else {
        // 通过menuId与动态(菜单)路由进行匹配跳转至指定路由
        const route = window.SITE_CONFIG['dynamicMenuRoutes'].filter(item => item.meta.menuId === menu.id)[0]
        if (route) {
          // 不能通过query传，否则参数会被带到路径中
          this.$router.push({ name: route.name, params: route.params })
        }
      }
    }
  }
}
</script>
