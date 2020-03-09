import Cookies from 'js-cookie'
import CryptoJS from 'crypto-js'
import store from '@/store'

/**
 * AES加密
 * @param raw 明文
 * @param key 加密key
 * @returns {string}
 */
export function aesEncrypt (raw, key) {
  key = key || '1234567890adbcde'
  return CryptoJS.AES.encrypt(CryptoJS.enc.Utf8.parse(raw), CryptoJS.enc.Utf8.parse(key), { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 }).toString()
}

/**
 * 解密
 * @param cipher 密文
 * @param key 加密key
 * @returns {string}
 */
export function aesDecrypt (cipher, key) {
  key = key || '1234567890adbcde'
  return CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt(cipher, CryptoJS.enc.Utf8.parse(key), { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 })).toString()
}

/**
 * 权限
 * @param {*} key
 */
export function hasPermission (key) {
  return window.SITE_CONFIG['permissions'].indexOf(key) !== -1 || false
}

/**
 * 清除登录信息
 */
export function clearLoginInfo () {
  store.commit('resetStore')
  Cookies.remove('token')
  window.SITE_CONFIG['dynamicMenuRoutesHasAdded'] = false
}

/**
 * 获取uuid
 */
export function getUUID () {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
    return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
  })
}

/**
 * 获取svg图标(id)列表
 */
export function getIconList () {
  const res = []
  const list = document.querySelectorAll('svg symbol')
  for (let i = 0; i < list.length; i++) {
    res.push(list[i].id)
  }
  return res
}

/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
export function treeDataTranslate (data, id = 'id', pid = 'pid') {
  var res = []
  var temp = {}
  for (var i = 0; i < data.length; i++) {
    temp[data[i][id]] = data[i]
  }
  for (var k = 0; k < data.length; k++) {
    if (!temp[data[k][pid]] || data[k][id] === data[k][pid]) {
      res.push(data[k])
      continue
    }
    if (!temp[data[k][pid]]['children']) {
      temp[data[k][pid]]['children'] = []
    }
    temp[data[k][pid]]['children'].push(data[k])
    data[k]['_level'] = (temp[data[k][pid]]._level || 0) + 1
  }
  return res
}

/**
 * 去除空children
 * 在如cascader等控件中，对于空数组会认为是有效节点
 * 需要设置为undefined
 */
export function removeEmptyChildren (data) {
  for (let itm of data) {
    if (!itm.children || itm.children.length < 1) {
      itm.children = undefined
    } else {
      removeEmptyChildren(itm.children)
    }
  }
  return data
}

/**
 * 是否json字符串
 *  * @param {*} str
 */
export function isJson (str) {
  if (typeof str === 'string') {
    try {
      const obj = JSON.parse(str)
      return typeof obj === 'object' && obj
    } catch (e) {
      return false
    }
  }
}
