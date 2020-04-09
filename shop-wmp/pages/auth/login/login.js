var api = require('../../../config/api.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');

var app = getApp();
Page({
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面渲染完成

  },
  onReady: function() {

  },
  onShow: function() {
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  },
  /**
   * 登录并且获取到用户信息
   */
  wxLogin: function(e) {
    console.log(e)
    if (!e.detail || 'getUserInfo:ok' !== e.detail.errMsg) {
      app.globalData.hasLogin = false;
      util.showErrorToast('微信登录失败');
      return;
    }

    /* 调用接口登录 */
    user.checkLogin().catch(() => {
      user.loginByWx(e.detail).then(res => {
        app.globalData.hasLogin = true;

        // 返回上一页
        wx.navigateBack({
          delta: 1
        })
      }).catch((err) => {
        app.globalData.hasLogin = false;
        util.showErrorToast('调用微信登录接口失败');
      });

    });
  },
  accountLogin: function() {
    wx.navigateTo({
      url: "/pages/auth/accountLogin/accountLogin"
    });
  }
})