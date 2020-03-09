<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-row>
                <el-col :span="12">
                    <el-form-item :label="$t('base.code')" prop="code">
                        <el-input v-model="dataForm.code" :placeholder="$t('base.code')"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="name" :label="$t('base.name')">
                        <el-input v-model="dataForm.name" :placeholder="$t('base.name')"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item :label="$t('sms.platform')" size="mini" prop="platform">
                <el-radio-group v-model="dataForm.platform">
                    <el-radio label="aliyun">阿里云</el-radio>
                    <el-radio label="juhe">聚合</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="平台配置" prop="config">
                <el-input v-model="dataForm.config" placeholder="平台配置" type="textarea"/>
            </el-form-item>
            <el-form-item label="短信参数" prop="param">
                <el-input v-model="dataForm.param" placeholder="短信参数"/>
            </el-form-item>
            <el-form-item label="短信内容" prop="content">
                <el-input v-model="dataForm.content" placeholder="短信内容为记录使用,实际短信内容请在短信平台修改"/>
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
        dataFormSaveURL: `/msg/smsTpl/save`,
        dataFormUpdateURL: `/msg/smsTpl/update`,
        dataFormInfoURL: `/msg/smsTpl/info?id=`
      },
      dataForm: {
        id: '',
        platform: '',
        name: '',
        code: '',
        config: '',
        param: '',
        content: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        code: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        platform: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        config: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        param: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        content: [
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
