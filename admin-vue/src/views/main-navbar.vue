<template>
  <nav class="aui-navbar" :class="`aui-navbar--${$store.state.navbarLayoutType}`">
    <div class="aui-navbar__header">
      <h1 class="aui-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="aui-navbar__brand-lg" href="javascript:;"><img src="~@/assets/img/logo_white.png" style="width: 30px;height: 30px; margin-right: 10px;"/>{{ sysTitleAbbr }}</a>
        <a class="aui-navbar__brand-mini" href="javascript:;"><img src="~@/assets/img/logo_white.png" style="width: 30px;height: 30px"/></a>
      </h1>
    </div>
    <div class="aui-navbar__body">
      <el-menu class="aui-navbar__menu mr-auto" mode="horizontal">
        <el-menu-item index="1" @click="$store.state.sidebarFold = !$store.state.sidebarFold">
          <i class="aui-navbar__icon-menu aui-navbar__icon-menu--switch el-icon-s-fold"/>
        </el-menu-item>
        <el-menu-item index="2" @click="refresh()">
          <i class="aui-navbar__icon-menu aui-navbar__icon-menu--refresh el-icon-refresh"/>
        </el-menu-item>
      </el-menu>
      <el-menu class="aui-navbar__menu" mode="horizontal">
        <el-menu-item index="1">
          <el-dropdown placement="bottom" :show-timeout="0">
            <el-button size="mini">快捷链接</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item><a href="https://fir.im/" target="_blank">APP下载</a></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
         <!--<el-menu-item index="2">
          <a href="" target="_blank">
            <i class="el-icon-arrow-down el-icon-link"/>
          </a>
        </el-menu-item>-->
        <el-menu-item index="3" @click="fullscreenHandle()">
          <i class="aui-navbar__icon-menu el-icon-full-screen"/>
        </el-menu-item>
        <el-menu-item index="4" class="aui-navbar__avatar">
          <el-dropdown placement="bottom" :show-timeout="0">
            <span class="el-dropdown-link">
              <el-image :src="$store.state.user.headUrl" fit="cover" class="el-dropdown-link">
                <div slot="error" class="image-slot el-dropdown-link">
                  <img slot="error" src="~@/assets/img/avatar.png">
                </div>
              </el-image>
              <span>{{ $store.state.user.username }}</span>
              <i class="el-icon-arrow-down"/>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="updatePasswordHandle()">{{ $t('updatePassword.title') }}</el-dropdown-item>
              <el-dropdown-item @click.native="logoutHandle()">{{ $t('logout') }}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password v-if="updatePasswordVisible" ref="updatePassword"/>
  </nav>
</template>

<script>
import Cookies from 'js-cookie'
import { messages } from '@/i18n'
import screenfull from 'screenfull'
import UpdatePassword from './main-navbar-update-password'
import { redirectLogin } from '@/utils'

export default {
  components: { UpdatePassword },
  inject: ['refresh'],
  data () {
    return {
      i18nMessages: messages,
      updatePasswordVisible: false,
      sysTitleAbbr: ''
    }
  },
  created () {
    this.sysTitleAbbr = Cookies.get('titleAbbr')
  },
  methods: {
    // 全屏
    fullscreenHandle () {
      if (!screenfull.enabled) {
        return this.$message({
          message: this.$t('fullscreen.prompt'),
          type: 'warning',
          duration: 500
        })
      }
      screenfull.toggle()
    },
    // 修改密码
    updatePasswordHandle () {
      this.updatePasswordVisible = true
      this.$nextTick(() => {
        this.$refs.updatePassword.init()
      })
    },
    // 退出
    logoutHandle () {
      this.$confirm(this.$t('prompt.info', { 'handle': this.$t('logout') }), this.$t('prompt.title'), {
        confirmButtonText: this.$t('confirm'),
        cancelButtonText: this.$t('cancel'),
        type: 'warning'
      }).then(() => {
        this.$http.post('/uc/user/logout?type=-10').then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.toast)
          }
          redirectLogin()
        }).catch(() => {})
      }).catch(() => {})
    }
  }
}
</script>
