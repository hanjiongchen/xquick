/**
 * 多标签输入module
 *
 * @author Charles zhangchaoxu@gmail.com
 */
export default {
  data () {
    return {
      // 属性
      mixinMultiTagsModuleOptions: {
        notAllowedSplit: ',' // 不允许的分隔符
      },
      // 标签列表
      tags: [],
      tagInputVisible: false,
      tagInputValue: ''
    }
  },
  methods: {
    showTagInput () {
      this.tagInputVisible = true
      this.$nextTick(() => {
        this.$refs.tagInput.$refs.input.focus()
      })
    },
    saveTagInputHandle () {
      let inputValue = this.tagInputValue
      if (inputValue) {
        inputValue = inputValue.trim()
        if (!inputValue) {
          this.$message.error('不允许为空')
        } else if (inputValue.indexOf(this.mixinMultiTagsModuleOptions.notAllowedSplit) > -1) {
          this.$message.error('不允许出现' + this.mixinMultiTagsModuleOptions.notAllowedSplit)
        } else if (this.tags.indexOf(inputValue) > -1) {
          this.$message.error('不允许重复添加')
        } else {
          this.tags.push(inputValue)
        }
      }
      this.tagInputVisible = false
      this.tagInputValue = ''
    }
  }
}
