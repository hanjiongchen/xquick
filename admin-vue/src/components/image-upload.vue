<template>
    <div>
        <el-upload
                :class="{ hide:uploadFileList.length >= limit }"
                :before-upload="beforeImageUpload"
                :on-success="uploadSuccessHandle"
                list-type="picture-card"
                :limit="limit"
                :accept="acceptImageFormat"
                :file-list="uploadFileList"
                :on-preview="uploadPreviewHandle"
                :multiple="false"
                :on-exceed="uploadExceedHandle"
                :on-remove="uploadRemoveHandle"
                :action="uploadUrl">
            <i class="el-icon-plus"></i>
            <div slot="tip" class="el-upload__tip">{{tips}}</div>
        </el-upload>
        <!-- 弹窗, 图片查看 -->
        <image-viewer :z-index="imageViewerZIndex" :url-list="imageViewerPreviewSrcList" ref="imageViewer" v-show="imageViewerVisible" :on-close="closeImageViewerHandle"/>
    </div>
</template>

<script>
/**
 * 图片上传组件
 * 更多接口见https://element.eleme.cn/#/zh-CN/component/upload
 *
 * @author Charles zhangchaoxu@gmail.com
 */
import Cookies from 'js-cookie'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'
import { isURL } from '@/utils/validate'

export default {
  name: 'ImageUpload',
  components: { ImageViewer },
  props: {
    // 绑定的v-model,必须用value
    // 参考https://cn.vuejs.org/v2/guide/components-custom-events.html#%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BB%84%E4%BB%B6%E7%9A%84-v-model
    value: {
      type: String
    },
    // 提示文字
    tips: {
      type: String,
      default: ''
    },
    // 接收的文件格式
    acceptImageFormat: {
      type: String,
      default: '.jpg,.jpeg,.png,.bmp'
    },
    // 限制个数
    limit: {
      type: Number,
      default: 9
    },
    // 上传地址
    uploadUrl: {
      type: String,
      default: `${window.SITE_CONFIG['apiURL']}/sys/oss/upload?token=${Cookies.get('token')}`
    }
  },
  data () {
    return {
      uploadFileList: this.getUploadFileListFromString(this.value),
      imageViewerZIndex: 2000, // 图片查看器zIndex
      imageViewerPreviewSrcList: [], // 图片查看文件列表
      prevOverflow: '', // 原先的overflow样式
      imageViewerVisible: false // 图片查看器,弹窗visible状态
    }
  },
  mounted () {
    this.init()
  },
  watch: {
    // 监听prop传的value
    value (newVal, oldVal) {
      console.log('newVal' + newVal)
      console.log('oldVal' + oldVal)
      this.uploadFileList = this.getUploadFileListFromString(newVal)
    }
  },
  methods: {
    // 初始化编辑器
    init () {
      this.uploadFileList = []
    },
    getUploadFileListFromString (imgs) {
      let fileList = []
      if (imgs) {
        imgs.split(',').forEach(item => {
          fileList.push({ url: item, name: item })
        })
      }
      return fileList
    },
    // 获得上传文件路径拼接
    getUploadFileString (fileList) {
      if (!fileList) {
        fileList = this.uploadFileList
      }
      let files = []
      fileList.forEach(item => {
        if (item.status === 'success') {
          if (isURL(item.url)) {
            files.push(item.url)
          } else if (item.response && item.response.code === 0 && item.response.data) {
            files.push(item.response.data.src)
          }
        }
      })
      return files.join(',')
    },
    // 文件超出数量限制
    uploadExceedHandle (files, fileList) {
      this.$message.warning(`共选择了 ${files.length + fileList.length} 个文件,超出数量限制`)
    },
    // 文件上传成功
    uploadSuccessHandle (res, file, fileList) {
      if (res.code !== 0) {
        return this.$message.error(res.toast)
      } else {
        this.$emit('input', this.getUploadFileString(fileList))
      }
    },
    // 图片上传失败
    uploadErrorHandle (err, file, fileList) {
      console.log(err)
      console.log(file)
      console.log(fileList)
      this.$emit('input', this.getUploadFileString(fileList))
    },
    // 文件发生变化
    uploadChangeHandle (file, fileList) {
    },
    // 图片移除成功
    uploadRemoveHandle (file, fileList) {
      this.$emit('input', this.getUploadFileString(fileList))
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
    // 图片上传检查
    beforeImageUpload (file) {
      // 默认限制最大8M
      let size = 8
      // 是否允许格式
      const isAllowType = (file.type === 'image/jpeg' || file.type === 'image/jpeg' || file.type === 'image/png')
      // 是否大小范围内
      const isLtLimit = file.size / 1024 / 1024 < size
      if (!isAllowType) {
        this.$message.error('只支持' + this.acceptImageFormat + '格式文件!')
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