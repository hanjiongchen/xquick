<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
          <el-form-item label="消费金额" prop="amount">
          <el-input v-model="dataForm.amount" placeholder="消费金额"></el-input>
      </el-form-item>
          <el-form-item label="默认项" prop="defaultItem">
          <el-input v-model="dataForm.defaultItem" placeholder="默认项"></el-input>
      </el-form-item>
          <el-form-item label="是否特殊" prop="special">
          <el-input v-model="dataForm.special" placeholder="是否特殊"></el-input>
      </el-form-item>
          <el-form-item label="优惠比例0-1" prop="scale">
          <el-input v-model="dataForm.scale" placeholder="优惠比例0-1"></el-input>
      </el-form-item>
          <el-form-item label="状态  0：停用   1：正常" prop="status">
          <el-input v-model="dataForm.status" placeholder="状态  0：停用   1：正常"></el-input>
      </el-form-item>
                </el-form>
    <template slot="footer">
      <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
    </template>
  </el-dialog>
</template>

<script>
import mixinFormModule from '@/mixins/form-module'
export default {
  mixins: [mixinFormModule],
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/userrank/save`,
        dataFormUpdateURL: `/shop/userrank/update`,
        dataFormInfoURL: `/shop/userrank/info?id=`
      },
      dataForm: {
        id: '',
        name: '',
        amount: '',
        defaultItem: '',
        special: '',
        scale: '',
        status: '',
        createId: '',
        createTime: '',
        updateId: '',
        updateTime: '',
        deleted: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        amount: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        defaultItem: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        special: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        scale: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        status: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        createId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        updateId: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        updateTime: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        deleted: [
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
