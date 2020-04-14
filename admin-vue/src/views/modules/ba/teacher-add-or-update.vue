<template>
    <el-dialog :visible.sync="visible" :title="!dataForm.id ? $t('add') : $t('update')" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="dataForm.name" placeholder="名称"></el-input>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="状态" prop="status">
                        <el-radio-group v-model="dataForm.status" size="small">
                            <el-radio-button :label="0">未激活</el-radio-button>
                            <el-radio-button :label="1">激活</el-radio-button>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="排序" prop="sort">
                        <el-input-number controls-position="right" :min="0" v-model="dataForm.sort" placeholder="排序" class="w-percent-100"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="头像" prop="imgs">
                <image-upload ref="imgsUpload" v-model="dataForm.imgs" :limit="4" :tips="`建议尺寸400*400,且不超过2MB`"/>
            </el-form-item>
            <el-form-item label="详细介绍" prop="content">
                <quill-editor ref="editorContent" containerHeight="200px" v-model="dataForm.content"/>
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
import QuillEditor from '@/components/quill-editor'
import ImageUpload from '@/components/image-upload'

export default {
  mixins: [mixinFormModule],
  components: { QuillEditor, ImageUpload },
  data () {
    return {
      // 表单模块参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/ba/teacher/save`,
        dataFormUpdateURL: `/ba/teacher/update`,
        dataFormInfoURL: `/ba/teacher/info?id=`
      },
      dataForm: {
        id: '',
        name: '',
        sort: '',
        type: '',
        imgs: '',
        content: '',
        status: 1
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
        type: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        imgs: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        content: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
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
