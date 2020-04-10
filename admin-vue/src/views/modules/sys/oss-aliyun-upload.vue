<template>
  <el-dialog :visible.sync="visible" title="直传阿里云OSS" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-upload
      :action="url"
      :file-list="fileList"
      drag
      ref="upload"
      :http-request="ossUploadRequest"
      :before-upload="beforeUploadHandle"
      :on-success="successHandle"
      class="text-center">
      <i class="el-icon-upload"/>
      <div class="el-upload__text" v-html="$t('uploadText')"></div>
      <div class="el-upload__tip" slot="tip">{{ $t('uploadTip', { 'format': 'jpg、png、gif' }) }}</div>
    </el-upload>
  </el-dialog>
</template>

<script>
import oss from 'ali-oss'
import dayjs from 'dayjs'

export default {
  data () {
    return {
      visible: false,
      url: '',
      num: 0,
      token: '',
      fileList: []
    }
  },
  methods: {
    // 覆盖默认的上传行为，实现自定义上传
    async ossUploadRequest (option) {
      // 先从服务器获取oss配置信息
      let sts = await this.$http.get(`/sys/oss/getSts?paramCode=OSS_CFG_PUB`).catch(() => {})
      console.log(sts)
      if (sts.data.code !== 0) {
        // this.$refs.upload.clearFiles()
        return sts.data
      }
      // console.log(sts.data.Data)
      try {
        let vm = this
        vm.uploadMateriaDisabled = true
        // 获取OSS配置信息
        let client = oss({
          accessKeyId: sts.data.Data.AccessKeyId,
          accessKeySecret: sts.data.Data.AccessKeySecret,
          stsToken: sts.data.Data.SecurityToken,
          bucket: sts.data.Data.Bucket,
          region: sts.data.Data.Region,
          secure: sts.data.Data.Secure
        })
        let file = option.file
        // 分片同步上传文件
        var ret = await client.multipartUpload(sts.data.Data.RelativePath + '/' + dayjs().format('YYYYMMDDHHmmssSSS') + '_' + file.name, file, {
          progress: async function (p) {
            // 返回上传进度
            option.onProgress({ percent: p * 100 })
          }
        })
        vm.uploadMateriaDisabled = false
        if (ret.res.statusCode === 200) {
          return { 'Data': sts.data.Data.Prefix + ret.name, 'MsgCode': '0', 'MsgDetail': 'OK' }
        } else {
          return { 'Data': null, 'MsgCode': '1', 'MsgDetail': 'upload fail' }
        }
      } catch (error) {
        console.error(error)
        this.uploadMateriaDisabled = false
        return { 'Data': null, 'MsgCode': '2', 'MsgDetail': 'upload exception' }
      }
    },
    init () {
      this.visible = true
      this.num = 0
      this.fileList = []
    },
    // 上传之前
    beforeUploadHandle (file) {
      /* if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
        this.$message.error(this.$t('uploadTip', { 'format': 'jpg、png、gif' }))
        return false
      } */
      this.num++
    },
    // 上传成功
    successHandle (res, file, fileList) {
      if (res.code !== 0) {
        return this.$message.error(res.toast)
      }
      this.fileList = fileList
      this.num--
      if (this.num === 0) {
        this.$message({
          message: this.$t('prompt.success'),
          type: 'success',
          duration: 500,
          onClose: () => {
            this.visible = false
            this.$emit('refreshDataList')
          }
        })
      }
    }
  }
}
</script>
