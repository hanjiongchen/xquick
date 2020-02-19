import Vue from 'vue'
import Element from 'element-ui'
import JsonViewer from 'vue-json-viewer'
import App from '@/App'
import i18n from '@/i18n'
import router from '@/router'
import store from '@/store'

import '@/icons'
import '@/element-ui/theme/index.css'
import '@/assets/scss/aui.scss'
import http from '@/utils/http'
import { hasPermission } from '@/utils'
import cloneDeep from 'lodash/cloneDeep'

Vue.config.productionTip = false

Vue.use(Element, {
  size: 'default',
  i18n: (key, value) => i18n.t(key, value)
})

Vue.use(JsonViewer)

// amap
/*
import VueAMap from 'vue-amap'
Vue.use(VueAMap)
VueAMap.initAMapApiLoader({
  // 高德的key
  key: '',
  // 插件集合
  plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
  // 高德 sdk 版本，默认为 1.4.4
  v: '1.4.4'
}) */

// 挂载全局
Vue.prototype.$http = http
Vue.prototype.$hasPermission = hasPermission

// 保存整站vuex本地储存初始状态
window.SITE_CONFIG['storeState'] = cloneDeep(store.state)

new Vue({
  i18n,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
