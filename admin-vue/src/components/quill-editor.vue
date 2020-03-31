<template>
    <div>
        <!-- 富文本编辑器, 容器 -->
        <div id="J_quillEditor"/>
        <!-- 定义了一个不显示的upload组件,然后和image工具绑定 -->
        <el-upload
                :action="uploadUrl"
                :show-file-list="false"
                :before-upload="beforeImageUpload"
                :on-success="uploadEditorSuccessHandle"
                style="display: none;">
            <el-button ref="uploadBtn" type="primary" size="small"/>
        </el-upload>
    </div>
</template>

<script>
/**
 * quill 富文本编辑器
 * 更多接口见https://quilljs.com/docs/quickstart/
 *
 * @author Charles zhangchaoxu@gmail.com
 */
import 'quill/dist/quill.snow.css'
import Quill from 'quill'
import Cookies from 'js-cookie'

export default {
  name: 'QuillEditor',
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
    },
    // 工具条属性
    toolbarOptions: {
      type: Array,
      default: () => {
        return [
          ['bold', 'italic', 'underline', 'strike'],
          ['blockquote', 'code-block', 'image'],
          [{ 'header': 1 }, { 'header': 2 }],
          [{ 'list': 'ordered' }, { 'list': 'bullet' }],
          [{ 'script': 'sub' }, { 'script': 'super' }],
          [{ 'indent': '-1' }, { 'indent': '+1' }],
          [{ 'direction': 'rtl' }],
          [{ 'size': ['small', false, 'large', 'huge'] }],
          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
          [{ 'color': [] }, { 'background': [] }],
          [{ 'font': [] }],
          [{ 'align': [] }],
          ['clean']
        ]
      }
    }
  },
  data () {
    return {
      // 富文本编辑器
      quillEditor: null
    }
  },
  mounted () {
    this.init()
  },
  watch: {
    // 监听prop传的value
    value (newVal, oldVal) {
      if (!newVal || !oldVal || oldVal === '<p><br></p>') {
        this.quillEditor.root.innerHTML = newVal
      }
    }
  },
  methods: {
    // 初始化编辑器
    init () {
      if (this.quillEditor) {
        // 已存在则清空内容
        this.quillEditor.deleteText(0, this.quillEditor.getLength())
      } else {
        // 不存在则初始化定义
        this.quillEditor = new Quill('#J_quillEditor', { modules: { toolbar: this.toolbarOptions }, theme: 'snow' })
        // 自定义上传图片功能 (使用element upload组件)
        this.quillEditor.getModule('toolbar').addHandler('image', () => { this.$refs.uploadBtn.$el.click() })
        // 监听内容变化,动态赋值,会有点卡
        this.quillEditor.on('text-change', () => {
          this.$emit('input', this.getInnerHTML())
        })
      }
      // 设置为model的值
      this.setInnerHTML(this.value)
      // 设置高度
      this.quillEditor.container.style.height = this.containerHeight
    },
    // 设置内容
    setInnerHTML (content) {
      this.quillEditor.root.innerHTML = content
    },
    // 获取内容
    getInnerHTML () {
      // getContents返回的是一个Delta
      return this.quillEditor.root.innerHTML
    },
    // 获取内容长度
    getContentLength () {
      // 即使是空,也会有一个\n’, 所以长度是1
      return this.quillEditor.getLength()
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
