/**
 * 基础module
 *
 * @author Charles zhangchaoxu@gmail.com
 */
export default {
  data () {
    return {
      imageViewerZIndex: 2000, // 图片查看器zIndex
      imageViewerPreviewSrcList: [], // 图片查看文件列表
      prevOverflow: '', // 原先的overflow样式
      imageViewerVisible: false, // 图片查看器,弹窗visible状态
      // 日期范围选择器
      dateRangePickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  methods: {
    // 文本查看器
    textViewHandle (content, title, customClass) {
      this.$msgbox({
        title: title || '查看',
        message: content,
        confirmButtonText: '关闭',
        customClass: customClass || 'el-message-w-60'
      })
    },
    // html查看器
    htmlViewHandle (content, title, customClass) {
      this.$msgbox({
        title: title || '查看',
        message: content,
        dangerouslyUseHTMLString: true,
        confirmButtonText: '关闭',
        customClass: customClass || 'el-message-w-60'
      })
    },
    // 预览上传文件
    uploadPreviewHandle () {
      let imgList = []
      this.uploadFileList.forEach(item => {
        imgList.push(item.url)
      })
      this.imageViewerHandle(imgList)
    },
    // 图片查看器
    imageViewerHandle (imgList) {
      // 保留prevent body scroll现场
      this.prevOverflow = document.body.style.overflow
      document.body.style.overflow = 'hidden'
      this.imageViewerVisible = true
      this.imageViewerPreviewSrcList = imgList
    },
    // 关闭图片查看器
    closeImageViewerHandle () {
      // 还原prevent body scroll现场
      document.body.style.overflow = this.prevOverflow
      this.imageViewerVisible = false
    },
    // json查看器
    // [vue-json-viewer](https://github.com/chenfengjw163/vue-json-viewer)
    jsonViewHandle (content, title, customClass) {
      let json = null
      if (typeof content === 'string') {
        try {
          json = JSON.parse(content)
        } catch (e) {
          console.error(e.toString())
        }
      }
      if (json === null || !json || typeof json !== 'object') {
        return this.$message.error('json字符串格式错误')
      }
      this.$msgbox({
        title: title || '查看',
        message: this.$createElement('json-viewer', { attrs: { value: json, copyable: true } }),
        confirmButtonText: '关闭',
        customClass: customClass || 'el-message-w-60'
      })
    },
    // 选中用户
    onUserPicked (result) {
      if (result && result.length > 0) {
        this.dataForm.userId = result[0].id
        this.dataForm.userName = result[0].username
      }
    }
  }
}
