<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
      <el-form-item prop="name" :label="$t('schedule.beanName')">
        <el-input v-model="dataForm.name" :placeholder="$t('schedule.beanNameTips')"></el-input>
      </el-form-item>
      <el-form-item prop="params" :label="$t('schedule.params')">
        <el-input v-model="dataForm.params" :placeholder="$t('schedule.params')"></el-input>
      </el-form-item>
      <el-form-item prop="cron" :label="$t('schedule.cronExpression')">
        <el-input v-model="dataForm.cron" :placeholder="$t('schedule.cronExpressionTips')"></el-input>
      </el-form-item>
      <el-form-item prop="remark"  :label="$t('schedule.remark')">
        <el-input v-model="dataForm.remark" :placeholder="$t('schedule.remark')"></el-input>
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
      // 表单参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/sched/task/save`,
        dataFormUpdateURL: `/sched/task/update`,
        dataFormInfoURL: `/sched/task/info?id=`
      },
      dataForm: {
        id: '',
        name: '',
        params: '',
        cron: '',
        remark: '',
        status: 0
      }
    }
  },
  computed: {
    dataRule () {
      return {
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        cron: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.visible = true
      this.formLoading = true
      this.$nextTick(() => {
        this.resetForm()
        this.initFormData()
      })
    }
  }
}
</script>
