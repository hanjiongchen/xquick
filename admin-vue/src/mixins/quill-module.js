/**
 * 富文本编辑器基础
 */
import 'quill/dist/quill.snow.css'
import Quill from 'quill'

export default {
  data () {
    /* eslint-disable */
    return {
      // 属性
      mixinQuillModuleOptions: {
      },
      // 富文本编辑器
      quillEditor: null,
      // 富文本编辑器共具条配置
      quillEditorToolbarOptions: [
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
    /* eslint-enable */
  },
  activated () {},
  methods: {
    // 初始化表单
    quillEditorHandle () {
      if (this.quillEditor) {
        // 已存在则清空内容
        this.quillEditor.deleteText(0, this.quillEditor.getLength())
      } else {
        // 不存在则初始化定义
        this.quillEditor = new Quill('#J_quillEditor', { modules: { toolbar: this.quillEditorToolbarOptions }, theme: 'snow' })
        // 自定义上传图片功能 (使用element upload组件)
        this.quillEditor.getModule('toolbar').addHandler('image', () => { this.$refs.uploadBtn.$el.click() })
        // 监听内容变化，动态赋值
        // this.quillEditor.on('text-change', () => { this.quillHtml = this.quillEditor.root.innerHTML })
      }
    },
    // 富文本编辑器 上传图片成功
    uploadEditorSuccessHandle (res, file, fileList) {
      if (res.code !== 0) {
        return this.$message.error(res.toast)
      }
      this.quillEditor.insertEmbed(this.quillEditor.getSelection().index, 'image', res.data.src)
    }
  }
}
