<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="名称" prop="name">
                        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="sort" :label="$t('base.sort')">
                        <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" :label="$t('base.sort')"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="状态" prop="status">
                <el-radio-group v-model="dataForm.status" size="mini">
                    <el-radio-button :label="1">已审核</el-radio-button>
                    <el-radio-button :label="0">未审核</el-radio-button>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="imgs" label="图片">
                <image-upload ref="imgsUpload" v-model="dataForm.imgs" :limit="1" :tips="`建议尺寸400*400,且不超过2MB`"/>
            </el-form-item>
            <el-form-item label="简介" prop="content">
                <el-input v-model="dataForm.content" placeholder="简介" type="textarea"></el-input>
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
import mixinBaseModule from '@/mixins/base-module'
import ImageUpload from '@/components/image-upload'

export default {
  mixins: [mixinBaseModule, mixinFormModule],
  components: { ImageUpload },
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/shop/supplier/save`,
        dataFormUpdateURL: `/shop/supplier/update`,
        dataFormInfoURL: `/shop/supplier/info?id=`
      },
      dataForm: {
        name: '',
        remark: '',
        imgs: '',
        status: 0,
        content: '',
        sort: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        name: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        sort: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        imgs: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        status: [
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
