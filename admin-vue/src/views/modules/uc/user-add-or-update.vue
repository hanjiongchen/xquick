<template>
    <el-dialog :visible.sync="visible" :title="mode === 'view' ? $t('view')  : (!dataForm.id ? $t('add') : $t('update'))" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form v-loading="formLoading" :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="username" :label="$t('user.username')">
                        <el-input v-model="dataForm.username" :placeholder="$t('user.username')" maxlength="50" show-word-limit :disabled="mode === 'view'"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="nickName" :label="$t('user.nickName')">
                        <el-input v-model="dataForm.nickName" :placeholder="$t('user.nickName')" maxlength="50" show-word-limit :disabled="mode === 'view'"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="realName" :label="$t('user.realName')">
                        <el-input v-model="dataForm.realName" :placeholder="$t('user.realName')" maxlength="5" show-word-limit :disabled="mode === 'view'"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="idNo" :label="$t('user.idNo')">
                        <el-input v-model="dataForm.idNo" :placeholder="$t('user.idNo')" maxlength="18" minlength="15" show-word-limit :disabled="mode === 'view'"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-if="mode !== 'view'">
                <el-col :span="12">
                    <el-form-item prop="password" :label="$t('user.password')" :class="{ 'is-required': !dataForm.id }">
                        <el-input v-model="dataForm.password" :placeholder="$t('user.password')"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="confirmPassword" :label="$t('user.confirmPassword')" :class="{ 'is-required': !dataForm.id }">
                        <el-input v-model="dataForm.confirmPassword" :placeholder="$t('user.confirmPassword')"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="mobile" :label="$t('user.mobile')">
                        <el-input v-model="dataForm.mobile" :placeholder="$t('user.mobile')" maxlength="11" show-word-limit :disabled="mode === 'view'"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="email" :label="$t('user.email')">
                        <el-input v-model="dataForm.email" :placeholder="$t('user.email')" maxlength="50" show-word-limit :disabled="mode === 'view'"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="gender" :label="$t('user.gender')" size="mini">
                        <el-radio-group v-model="dataForm.gender" :disabled="mode === 'view'">
                            <el-radio :label="0">{{ $t('user.gender0') }}</el-radio>
                            <el-radio :label="1">{{ $t('user.gender1') }}</el-radio>
                            <el-radio :label="2">{{ $t('user.gender2') }}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="status" :label="$t('user.status')" size="mini">
                        <el-radio-group v-model="dataForm.status" :disabled="mode === 'view'">
                            <el-radio :label="0">{{ $t('user.status0') }}</el-radio>
                            <el-radio :label="1">{{ $t('user.status1') }}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="deptId" :label="$t('user.deptName')">
                        <el-cascader
                                :disabled="mode === 'view'"
                                ref="deptName"
                                style="width: 100%"
                                :options="deptList"
                                v-model="deptListSelected"
                                filterable
                                clearable
                                :props="{ checkStrictly: true, label: 'name', value: 'id' }"
                                :placeholder="$t('user.deptName')"
                                @change="(value) => this.dataForm.deptId = (value && value.length > 0) ? value[value.length - 1] : ''"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="code" label="工号">
                        <el-input v-model="dataForm.code" placeholder="输入工号" maxlength="50" show-word-limit :disabled="mode === 'view'"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item prop="type" label="用户类型">
                        <el-select v-model="dataForm.type" placeholder="选择类型" clearable class="w-percent-100" :disabled="mode === 'view'">
                            <el-option v-for="item in typeList" :key="item.dictValue" :label="item.dictName" :value="item.dictValue"/>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="roleIdList" :label="$t('user.roleIdList')">
                        <el-select v-model="dataForm.roleIdList" multiple clearable :placeholder="$t('user.roleIdList')" class="w-percent-100" :disabled="mode === 'view'">
                            <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id"/>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item prop="address" :label="$t('user.address')">
                <el-input v-model="dataForm.address" :placeholder="$t('user.address')" maxlength="200" show-word-limit :disabled="mode === 'view'"/>
            </el-form-item>
            <el-form-item prop="remark" :label="$t('base.remark')">
                <el-input v-model="dataForm.remark" :placeholder="$t('base.remark')" maxlength="500" type="textarea" :disabled="mode === 'view'"/>
            </el-form-item>
            <el-form-item prop="avatar" :label="$t('user.head')">
                <el-upload
                        ref="upload"
                        :class="{hide:mode === 'view' || hideUpload}"
                        :disabled="mode === 'view'"
                        :before-upload="beforeImageUpload"
                        :on-success="uploadSuccessHandle"
                        list-type="picture-card"
                        :limit="1"
                        :accept="acceptImageFormat"
                        :file-list="uploadFileList"
                        :on-preview="uploadPreviewHandle"
                        :multiple="false"
                        :on-exceed="uploadExceedHandle"
                        :on-change="uploadChangeHandle"
                        :on-remove="uploadRemoveHandle"
                        :action="uploadUrl">
                    <i class="el-icon-plus"/>
                </el-upload>
            </el-form-item>
        </el-form>
        <template slot="footer">
            <el-button @click="visible = false">{{ $t('cancel') }}</el-button>
            <el-button v-if="mode !== 'view'" type="primary" @click="dataFormSubmitHandle()">{{ $t('confirm') }}</el-button>
        </template>
        <!-- 弹窗, 图片查看 -->
        <image-viewer :z-index="imageViewerZIndex" :url-list="imageViewerPreviewSrcList" ref="imageViewer" v-show="imageViewerVisible" :on-close="closeImageViewerHandle"/>
    </el-dialog>
</template>

<script>
import mixinBaseModule from '@/mixins/base-module'
import mixinFormModule from '@/mixins/form-module'
import ImageViewer from 'element-ui/packages/image/src/image-viewer'
import { isIDNo, isRealName, isMobile } from '@/utils/validate'
import { removeEmptyChildren } from '@/utils'

export default {
  mixins: [mixinBaseModule, mixinFormModule],
  components: { ImageViewer },
  data () {
    return {
      // 表单参数
      mixinFormModuleOptions: {
        dataFormSaveURL: `/uc/user/save`,
        dataFormUpdateURL: `/uc/user/update`,
        dataFormInfoURL: `/uc/user/info?id=`
      },
      // 部门列表
      deptList: [],
      // 角色列表
      roleList: [],
      deptListDefault: [],
      // 部门已选中
      deptListSelected: [],
      dataForm: {
        id: '',
        username: '',
        deptId: '0',
        deptName: '',
        password: '',
        birthday: '',
        confirmPassword: '',
        realName: '',
        gender: 0,
        email: '',
        mobile: '',
        roleIdList: [],
        status: 1,
        type: '',
        avatar: '',
        nickname: '',
        idNo: '',
        address: '',
        remark: '',
        code: ''
      },
      // 类型
      typeList: [
        { dictName: '系统管理员', dictValue: 10 },
        { dictName: '单位管理员', dictValue: 20 },
        { dictName: 'App用户', dictValue: 100 }
      ]
    }
  },
  computed: {
    dataRule () {
      const validatePassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          return callback(new Error(this.$t('validate.required')))
        }
        callback()
      }
      const validateConfirmPassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          return callback(new Error(this.$t('validate.required')))
        }
        if (this.dataForm.password !== value) {
          return callback(new Error(this.$t('user.validate.confirmPassword')))
        }
        callback()
      }
      const validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          return callback(new Error(this.$t('validate.format', { 'attr': this.$t('user.mobile') })))
        }
        callback()
      }
      const validateRealName = (rule, value, callback) => {
        if (value && !isRealName(value)) {
          return callback(new Error(this.$t('validate.format', { 'attr': this.$t('user.realName') })))
        }
        callback()
      }
      const validateIDNo = (rule, value, callback) => {
        if (value && !isIDNo(value)) {
          return callback(new Error(this.$t('validate.format', { 'attr': this.$t('user.idNo') })))
        }
        callback()
      }
      return {
        username: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: this.$t('validate.required'), trigger: 'change' }
        ],
        password: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        confirmPassword: [
          { validator: validateConfirmPassword, trigger: 'blur' }
        ],
        status: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: this.$t('validate.required'), trigger: 'blur' },
          { validator: validateMobile, trigger: 'blur' }
        ],
        idNo: [
          { validator: validateIDNo, trigger: 'blur' }
        ],
        realName: [
          { validator: validateRealName, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getDeptList()
    this.getRoleList()
  },
  methods: {
    init () {
      this.visible = true
      this.formLoading = true
      this.$nextTick(() => {
        this.initUpload()
        this.resetForm()
        this.roleIdListDefault = []
        this.deptListSelected = []
        // this.deptList = []
        // this.roleList = []
        this.initFormData()
        /* Promise.all([
                                  this.getDeptList(),
                                  this.getRoleList()
                                ]).then(() => {
                                  this.initFormData()
                                }) */
      })
    },
    // 获取部门列表
    getDeptList () {
      return this.$http.get('/uc/dept/list').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        this.deptList = removeEmptyChildren(res.data)
      }).catch(() => {
      })
    },
    // 获取角色列表
    getRoleList () {
      return this.$http.get('/uc/role/list').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        this.roleList = res.data
      }).catch(() => {
      })
    },
    // form信息获取成功
    onGetInfoSuccess (res) {
      this.dataForm = {
        ...this.dataForm,
        ...res.data,
        roleIdList: []
      }
      // 角色配置, 区分是否为默认角色
      for (let i = 0; i < res.data.roleIdList.length; i++) {
        if (this.roleList.filter(item => item.id === res.data.roleIdList[i])[0]) {
          this.dataForm.roleIdList.push(res.data.roleIdList[i])
          continue
        }
        this.roleIdListDefault.push(res.data.roleIdList[i])
      }
      // 赋值图片
      this.setUploadFileList(this.dataForm.headUrl)
      // 赋值部门
      res.data.pdeptList.forEach(item => {
        this.deptListSelected.push(item.id)
      })
    },
    // 表单提交之前的操作
    beforeDateFormSubmit () {
      this.dataForm.headUrl = this.getUploadFileString()
      this.dataFormSubmitParam = {
        ...this.dataForm,
        roleIdList: [
          ...this.dataForm.roleIdList,
          ...this.roleIdListDefault
        ]
      }
      return true
    }
  }
}
</script>
