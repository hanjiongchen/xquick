<template>
    <div>
        <div id="editor-container"/>
    </div>
</template>

<script>
/**
 * wangeditor 富文本编辑器
 * 更多接口见https://www.kancloud.cn/wangfupeng/wangeditor3/332599
 *
 * @author Charles zhangchaoxu@gmail.com
 */
import WangEditor from 'wangeditor'
import Cookies from 'js-cookie'

export default {
  name: 'RichTextEditor',
  props: {
    // 绑定的v-model,必须用value
    value: {
      type: String
    },
    // 编辑器高度
    containerHeight: {
      type: String,
      default: '350px'
    },
    // 上传地址
    uploadUrl: {
      type: String,
      default: `${window.SITE_CONFIG['apiURL']}/sys/oss/upload?token=${Cookies.get('token')}`
    }
  },
  data () {
    return {
      // 富文本编辑器
      textall: '',
      text: '',
      dialogVisible: false,
      editor: null
    }
  },
  mounted () {
    this.init()
  },
  watch: {
    // 监听prop传的value
    value (newVal, oldVal) {
      if (!newVal || !oldVal || oldVal === '<p><br></p>') {
      }
    }
  },
  methods: {
    // 初始化编辑器
    init () {
      this.editor = new WangEditor('#editor-container')
      window.wangEditor = WangEditor
      this.editor.customConfig.zIndex = 100
      this.editor.customConfig.onchange = (html) => {
        this.text = html
      }
      this.initUploadImg()
      this.editor.create()
      // 初始化插件
      this.initPlugins()
      if (this.disabled) {
        this.editor.$textElem.attr('contenteditable', false)
      }
      this.editor.txt.html(this.value)
      // 处理复制黏贴
      this.pasteHandle()
    },
    initUploadImg () {
      this.editor.customConfig.customUploadImg = (file, insert) => {
        this.uploadFile(file[0]).then(res => {
          insert(res)
        })
      }
    },
    handleClose (done) {
      this.text = this.textall
      done()
    },
    getUEContent () {
      return this.editor.txt.html()
    },
    initPlugins () {
      WangEditor.fullscreen = {
        // editor create之后调用
        init: function (editorSelector) {
          document.querySelector(editorSelector + ' .w-e-toolbar').appendHTML('<div class="w-e-menu"><span class="_wangEditor_btn_fullscreen" href="###" onclick="window.wangEditor.fullscreen.toggleFullscreen(\'' + editorSelector + '\')">全屏</span></div>')
        },
        toggleFullscreen: function (editorSelector) {
          document.querySelector(editorSelector).toggleClass('fullscreen-editor')
          if (document.querySelector(editorSelector + ' ._wangEditor_btn_fullscreen').innerText === '全屏') {
            document.querySelector(editorSelector + ' ._wangEditor_btn_fullscreen').innerText = '退出全屏'
          } else {
            document.querySelector(editorSelector + ' ._wangEditor_btn_fullscreen').innerText = '全屏'
          }
        }
      }
      WangEditor.fullscreen.init('#editor-container')
      WangEditor.views = {
        init: function (editorSelector) {
          document.querySelector(editorSelector + ' .w-e-toolbar').appendHTML('<div class="w-e-menu"><span class="_wangEditor_btn_fullscreen" href="###" onclick="window.wangEditor.views.toggleFullscreen(\'' + editorSelector + '\')">源代码</span></div>')
        },
        toggleFullscreen: () => {
          this.textall = this.value
          this.dialogVisible = true
        }
      }
      WangEditor.views.init('#editor-container')
    },
    pasteHandle () {
      // 粘贴键
      document.addEventListener('paste', e => {
        // 获取剪切板文件
        const getFile = event => {
          if (event.clipboardData || event.originalEvent) {
            let clipboardData = event.clipboardData || event.originalEvent.clipboardData
            if (clipboardData.items) {
              var items = clipboardData.items
              var len = items.length
              var blob = null
              for (var i = 0; i < len; i++) {
                if (items[i].type.indexOf('image') !== -1) {
                  blob = items[i].getAsFile()
                  return blob
                }
              }
            }
          }
        }
        const file = getFile(e)
        if (file) {
          this.uploadFile(file).then(res => {
            this.editor.txt.append('<img src="' + res + '" />')
          })
        }
      })
    },
    // 富文本编辑器 上传图片成功
    uploadEditorSuccessHandle (res, file, fileList) {
      if (res.code !== 0) {
        return this.$message.error(res.toast)
      }
      // 将图片插入指定位置
      this.quillEditor.insertEmbed(this.quillEditor.getSelection().index, 'image', res.data.src)
    },
    // 图片上传检查
    beforeImageUpload (file) {
      // 默认限制最大8M
      let size = 8
      // 是否允许格式
      const isAllowType = (file.type === 'image/jpeg' || file.type === 'image/jpeg' || file.type === 'image/png')
      // 是否大小范围内
      const isLtLimit = file.size / 1024 / 1024 < size
      if (!isAllowType) {
        this.$message.error('只支持jpg,png,jpeg格式文件!')
        return false
      }
      if (!isLtLimit) {
        this.$message.error('上传文件大小不能超过 ' + size + 'MB')
        return false
      }
      return true
    }
  }
}
</script>
