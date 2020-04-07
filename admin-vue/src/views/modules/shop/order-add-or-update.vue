<template>
    <el-card>
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-form-item label="状态" prop="status">
                <el-input v-model="dataForm.status" placeholder="状态"></el-input>
            </el-form-item>
            <el-form-item label="订单号" prop="no">
                <el-input v-model="dataForm.no" placeholder="订单号"></el-input>
            </el-form-item>
        </el-form>
        <div style="text-align: center;">
            <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('save') }}</el-button>
        </div>
    </el-card>
</template>

<script>
import mixinFormModule from '@/mixins/form-module'

export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/order/save`,
        dataFormUpdateURL: `/shop/order/update`,
        dataFormInfoURL: `/shop/order/info?id=`
      },
      dataForm: {
        id: '',
        status: '',
        no: ''
      }
    }
  },
  activated () {
    let queryId = this.$route.query.id
    if (this.dataForm.id !== queryId) {
      // 参数发生了变化
      if (!queryId) {
        this.$message.error(this.$t('addneedstep'))
        return
      } else {
        this.dataForm.id = queryId
      }
      // 根据id刷新tab名称
      let tab = this.$store.state.contentTabs.filter(item => item.name === this.$route.name)[0]
      if (tab) {
        tab.title = queryId ? '编辑订单' : '新增订单'
      }
      // 根据step刷新数据
      this.init()
    }
  },
  computed: {
    dataRule () {
      return {
        status: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.formLoading = true
      this.visible = true
      this.$nextTick(() => {
        this.resetForm()
        this.initFormData()
      })
    }
  }
}
</script>
