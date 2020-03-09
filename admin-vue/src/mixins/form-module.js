/**
 * 表单基础
 */
import Cookies from 'js-cookie'
import debounce from 'lodash/debounce'
import { aesEncrypt } from '@/utils'
import { beforeImageUpload } from '@/utils/upload'

export default {
  data () {
    /* eslint-disable */
    return {
      // 属性
      mixinFormModuleOptions: {
        dataFormInfoURL: '',         // 数据信息接口，API地址
        dataFormSaveURL: '',         // 表单保存接口，API地址
        dataFormUpdateURL: '',       // 表单修改接口，API地址
        dataFormSubmitConfig: null,  // 表单提交配置
        dataFormParamEncrypt: false, // 是否加密参数
        idKey: 'id'                  // 默认表主键
      },
      // 接口提交参数
      dataFormSubmitParam: { },
      // 默认属性
      dataFormMode: '', // 表单模式,save/update
      dataForm: {}, // 表单
      visible: false, // 表单visible状态
      formLoading: true, // 表单是否加载中
      // 文件上传
      uploadUrl: '', // 文件上传地址
      acceptImageFormat: '.jpg,.jpeg,.png,.bmp', // 支持的图片文件类型
      acceptExcelFormat: '.xls,.xlsx', // 支持的Excel文件类型
      uploadFileList: [], // 已上传的图片
      // 隐藏upload最后的按钮
      hideUpload: false,
      // 图片查看器
      imageSwiperVisible: false,
      // 地图选择器
      mapLocationPickVisible: false,
      // 对话框是否全屏
      fullscreen: false,
    }
    /* eslint-enable */
  },
  activated () {},
  methods: {
    // 初始化表单
    initFormData () {
      if (this.dataForm.id) {
        // 修改
        this.dataFormMode = 'update'
        this.getInfo()
      } else {
        // 新增
        this.dataFormMode = 'save'
        this.formLoading = false
      }
    },
    // 获取信息
    getInfo () {
      this.formLoading = true
      this.$http.get(this.mixinFormModuleOptions.dataFormInfoURL + this.dataForm.id).then(({ data: res }) => {
        if (res.code !== 0) {
          this.onGetInfoError(res)
        } else {
          this.onGetInfoSuccess(res)
        }
        this.formLoading = false
      }).catch(resp => {
        console.error(resp)
        this.$message.error(this.$t('prompt.apierror') + resp)
        this.formLoading = false
      })
    },
    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
    },
    // form信息获取失败
    onGetInfoError (res) {
      return this.$message.error(res.msg)
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      if (this.mixinFormModuleOptions.dataFormParamEncrypt) {
        // 对参数做加密处理,注意要urlencode
        this.dataFormSubmitParam = encodeURIComponent(aesEncrypt(JSON.stringify(this.dataForm)))
      } else {
        this.dataFormSubmitParam = this.dataForm
      }
      return true
    },
    // 表单提交
    dataFormSubmitHandle: debounce(function () {
      this.formLoading = true
      if (this.beforeDateFormSubmit()) {
        // 验证表单
        this.$refs['dataForm'].validate((valid) => {
          if (!valid) {
            this.formLoading = false
            return false
          }
          // 验证通过,提交表单
          this.$http[this.dataFormMode === 'save' ? 'post' : 'put'](this.dataFormMode === 'save' ? this.mixinFormModuleOptions.dataFormSaveURL : this.mixinFormModuleOptions.dataFormUpdateURL, this.dataFormSubmitParam, this.mixinFormModuleOptions.dataFormSubmitConfig).then(({ data: res }) => {
            if (res.code !== 0) {
              this.onFormSubmitError(res)
            } else {
              this.onFormSubmitSuccess(res)
            }
            this.formLoading = false
          }).catch(resp => {
            this.formLoading = false
          })
        })
      } else {
        this.formLoading = false
      }
    }, 1000, { 'leading': true, 'trailing': false }),
    // 表单提交失败
    onFormSubmitError (res) {
      this.$message.error(res.code + ':' + res.msg)
    },
    // 表单提交成功
    onFormSubmitSuccess (res) {
      this.$message({
        message: this.$t('prompt.success'),
        type: 'success',
        duration: 500,
        onClose: () => {
          this.visible = false
          this.$emit('refreshDataList')
        }
      })
    },
    // 重置data中的数据,不充重置可能会有一些非form的字段一直保存的问题
    clear () {
      // 使用Object.assign(this.$data, this.$options.data())会把所有数据都清空掉
      // 使用Object.assign(this.$data.dataForm, this.$options.data(.dataForm))不知道为什么不工作
      this.$data.dataForm = this.$options.data().dataForm
    },
    // 重置表单
    resetForm (formName) {
      if (undefined === formName) { formName = 'dataForm' }
      this.$refs[formName].resetFields()
    },
    // [+] 图片相关
    // 初始化文件上传
    initUpload () {
      this.setUploadUrl()
      this.uploadFileList = []
    },
    // 设置文件上传地址
    setUploadUrl () {
      this.uploadUrl = `${window.SITE_CONFIG['apiURL']}/sys/oss/upload?token=${Cookies.get('token')}`
    },
    // 图片上传前检查
    beforeImageUpload (file) {
      return beforeImageUpload(file)
    },
    // 文件超出数量限制
    uploadExceedHandle (files, fileList) {
      this.$message.warning(`共选择了 ${files.length + fileList.length} 个文件,超出数量限制`)
    },
    // 文件上传成功
    uploadSuccessHandle (res, file, fileList) {
      if (res.code !== 0) {
        return this.$message.error(res.msg)
      } else {
        this.uploadFileList = fileList
      }
    },
    // 图片上传失败
    uploadErrorHandle  (err, file, fileList) {
      console.log(err)
      console.log(file)
      console.log(fileList)
      this.uploadFileList = fileList
    },
    // 文件发生变化
    uploadChangeHandle (file, fileList) {

    },
    // 图片移除成功
    uploadRemoveHandle (file, fileList) {
      this.uploadFileList = fileList
    },
    // 获得上传文件路径拼接
    getUploadFileString (fileList) {
      if (!fileList) {
        fileList = this.uploadFileList
      }
      let files = []
      fileList.forEach(function (item) {
        if (item.status === 'success') {
          if (item.url && item.url.startsWith('http')) {
            files.push(item.url)
          } else if (item.response && item.response.code === 0 && item.response.data) {
            files.push(item.response.data.src)
          }
        }
      })
      return files.join(',')
    },
    // 设置uploadFileList
    setUploadFileList (imgs) {
      if (imgs) {
        const that = this
        imgs.split(',').forEach(function (item) {
          that.uploadFileList.push({ url: item, name: item })
        })
      }
    },
    // 设置uploadFileList
    setImgsToUploadFileList (uploadFileList, imgs) {
      if (imgs) {
        imgs.split(',').forEach(function (item) {
          uploadFileList.push({ url: item, name: item })
        })
      }
    },
    // 选择地址
    mapLocationPickHandle () {
      this.mapLocationPickVisible = true
      this.$nextTick(() => {
        // 如果有地址的回到原先地址
        this.$refs.mapLocationPick.dataForm.position = [!this.dataForm.lng ? 120.210649 : this.dataForm.lng, !this.dataForm.lat ? 30.246071 : this.dataForm.lat]
        this.$refs.mapLocationPick.dataForm.regionNm = this.dataForm.regionNm
        this.$refs.mapLocationPick.dataForm.regionCd = this.dataForm.regionCd
        this.$refs.mapLocationPick.dataForm.address = this.dataForm.address
        this.$refs.mapLocationPick.init()
      })
    },
    // 接受位置选择返回结果
    onLocationInfoResult (result) {
      this.dataForm.address = result.address
      this.dataForm.lng = result.position[0]
      this.dataForm.lat = result.position[1]
      this.dataForm.regionNm = result.regionNm
      this.dataForm.regionCd = result.regionCd
    },
    // 新链接中打开文件
    openFileHandle (file) {
      window.open(file.url)
    },
    // 获得字典内容
    getDictList (type) {
      return this.$http.get('/sys/dict/list?dictType=' + type).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        return res.data
      }).catch(() => {
        this.$message.error('获取' + type + '字典值失败')
      })
    },
    // 改变全屏
    setFullScreen () {
      this.fullscreen = !this.fullscreen
    }
  }
}