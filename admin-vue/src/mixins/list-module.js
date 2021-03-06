/**
 * 列表基础
 */
import Cookies from 'js-cookie'
import qs from 'qs'
import dayjs from 'dayjs'

export default {
  data () {
    return {
      // 设置属性
      mixinListModuleOptions: {
        activatedIsNeed: true, // 此页面是否在激活（进入）时，调用查询数据列表接口
        getDataListURL: '', // 数据列表接口，API地址
        getDataListIsPage: false, // 数据列表接口，是否需要分页
        deleteURL: '', // 删除接口，API地址
        deleteBatchURL: '', // 删除接口，API地址
        deleteIsBatch: false, // 删除接口，是否需要批量
        deleteIsBatchKey: 'id', // 删除接口，批量状态下由那个key进行标记操作？比如：pid，uid...
        exportURL: '', // 导出接口，API地址
        idKey: 'id' // 默认表主键
      },
      // 默认属性
      dataForm: {}, // 查询条件
      dataList: [], // 数据列表
      order: 'desc', // 排序，asc／desc
      orderField: 'create_time', // 排序，字段
      page: 1, // 当前页码
      limit: 10, // 每页数
      total: 0, // 总条数
      dataListLoading: false, // 数据列表，loading状态
      dataListSelections: [], // 数据列表，多选项
      addOrUpdateVisible: false, // 新增/更新，弹窗visible状态
      uploadUrl: '', // 文件上传地址
      changeStatusVisible: false, // 修改状态,弹窗visible状态
      cancelVisible: false, // 取消操作,弹窗visible状态
      importVisible: false, // 导入操作,弹窗visible状态
      dateRange: null // 时间范围
    }
  },
  activated () {
    if (this.mixinListModuleOptions.activatedIsNeed) {
      this.getDataList()
    }
  },
  filters: {
    // 日期格式化
    dateFmt (cellValue) {
      let fmt = ''
      if (cellValue) {
        fmt = dayjs(cellValue).format('YYYY-MM-DD')
      }
      return fmt
    }
  },
  methods: {
    // 获取数据列表
    getDataList () {
      if (!this.beforeGetDataList()) {
        return
      }
      this.dataListLoading = true
      this.$http.get(
        this.mixinListModuleOptions.getDataListURL,
        {
          params: {
            order: this.order,
            orderField: this.orderField,
            page: this.mixinListModuleOptions.getDataListIsPage ? this.page : null,
            limit: this.mixinListModuleOptions.getDataListIsPage ? this.limit : null,
            ...this.dataForm
          }
        }
      ).then(({ data: res }) => {
        if (res.code !== 0) {
          this.onGetListError(res)
        } else {
          this.onGetListSuccess(res)
        }
        this.dataListLoading = false
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    // 查询数据列表
    queryDataList () {
      this.page = 1
      this.getDataList()
    },
    // 获取list之前的操作
    beforeGetDataList () {
      return true
    },
    // list信息获取成功
    onGetListSuccess (res) {
      this.dataList = this.mixinListModuleOptions.getDataListIsPage ? res.data.list : res.data
      this.total = this.mixinListModuleOptions.getDataListIsPage ? res.data.total : res.data.list.length
    },
    // list信息获取失败
    onGetListError (res) {
      this.dataList = []
      this.total = 0
      return this.$message.error(res.toast)
    },
    // 多选
    dataListSelectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 排序
    dataListSortChangeHandle (data) {
      if (!data.order || !data.prop) {
        this.order = ''
        this.orderField = ''
        return false
      }
      this.order = data.order.replace(/ending$/, '')
      this.orderField = data.prop.replace(/([A-Z])/g, '_$1').toLowerCase()
      this.getDataList()
    },
    // 分页, 每页条数
    pageSizeChangeHandle (val) {
      this.page = 1
      this.limit = val
      this.getDataList()
    },
    // 分页, 当前页
    pageCurrentChangeHandle (val) {
      this.page = val
      this.getDataList()
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.clear()
        this.$refs.addOrUpdate.dataForm.id = id
        this.$refs.addOrUpdate.dataFormMode = !id ? 'add' : 'update'
        this.$refs.addOrUpdate.init()
      })
    },
    // 查看
    previewHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.dataForm.id = id
        this.$refs.addOrUpdate.dataFormMode = 'view'
        this.$refs.addOrUpdate.init()
      })
    },
    // 修改状态
    changeStatusHandle (id) {
      this.changeStatusVisible = true
      this.$nextTick(() => {
        this.$refs.changeStatus.dataForm.id = id
        this.$refs.changeStatus.init()
      })
    },
    // 删除
    deleteHandle (id) {
      if (this.mixinListModuleOptions.deleteIsBatch && !id && this.dataListSelections.length <= 0) {
        // 批量删除先检查已选中个数
        return this.$message({
          message: this.$t('prompt.deleteBatch'),
          type: 'warning',
          duration: 500
        })
      }
      // 对话框提示是否删除
      this.$confirm(this.$t('prompt.info', { 'handle': this.$t('delete') }), this.$t('prompt.title'), {
        confirmButtonText: this.$t('confirm'),
        cancelButtonText: this.$t('cancel'),
        type: 'warning'
      }).then(() => {
        if (this.mixinListModuleOptions.deleteIsBatch) {
          // 批量删除
          this.$http.delete(`${this.mixinListModuleOptions.deleteBatchURL}`, { 'data': id ? [id] : this.dataListSelections.map(item => item[this.mixinListModuleOptions.deleteIsBatchKey]) })
            .then(({ data: res }) => {
              if (res.code !== 0) {
                return this.$message.error(res.toast)
              }
              this.$message({
                message: this.$t('prompt.success'),
                type: 'success',
                duration: 500,
                onClose: () => {
                  this.getDataList()
                }
              })
            })
        } else {
          // 单个删除
          this.$http.delete(`${this.mixinListModuleOptions.deleteURL}?` + this.mixinListModuleOptions.deleteIsBatchKey + '=' + id)
            .then(({ data: res }) => {
              if (res.code !== 0) {
                return this.$message.error(res.toast)
              }
              this.$message({
                message: this.$t('prompt.success'),
                type: 'success',
                duration: 500,
                onClose: () => {
                  this.getDataList()
                }
              })
            })
        }
      }).catch(() => {
      })
    },
    // 导出
    exportHandle () {
      const params = qs.stringify({
        'token': Cookies.get('token'),
        ...this.dataForm
      })
      window.location.href = `${window.SITE_CONFIG['apiURL']}${this.mixinListModuleOptions.exportURL}?${params}`
    },
    // 时间区间选择器变化
    dateRangeChangeHandle (value) {
      if (value !== null && value.length === 2) {
        this.dataForm.startCreateTime = value[0]
        this.dataForm.endCreateTime = value[1]
      } else {
        this.dataForm.startCreateTime = ''
        this.dataForm.endCreateTime = ''
      }
    },
    // 日期格式化
    dateFmt (row, column, cellValue) {
      let fmt = ''
      if (cellValue) {
        fmt = dayjs(cellValue).format('YYYY-MM-DD')
      }
      return fmt
    },
    // 格式化下拉菜单
    composeEditCommandValue (command, row) {
      return {
        'command': command,
        'row': row
      }
    },
    // 右侧操作按钮
    editActionHandler (command) {
      if (command.command === 'addOrUpdate') {
        // 新增/修改
        this.addOrUpdateHandle(command.row[this.mixinListModuleOptions.idKey])
      } else if (command.command === 'delete') {
        // 删除
        this.deleteHandle(command.row[this.mixinListModuleOptions.idKey])
      } else {
        this.moreEditActionHandler(command)
      }
    },
    // 其它更多按钮操作
    moreEditActionHandler (command) {

    }
  }
}
