const t = {}

t.loading = '加载中...'

t.brand = {}
t.brand.full = '极速开发管理平台'
t.brand.lg = '极速开发'
t.brand.mini = ''
t.brand.owner = 'www.nb6868.com'

/**
 * 常用操作
 */
t.view = '查看'
t.add = '新增'
t.save = '保存'
t.delete = '删除'
t.deleteBatch = '删除'
t.update = '修改'
t.query = '查询'
t.export = '导出'
t.import = '导入'
t.upload = '上传'
t.download = '下载'
t.handle = '操作'
t.reset = '重置'
t.close = '关闭'
t.confirm = '确定'
t.cancel = '取消'
t.logout = '退出'
t.success = '成功'
t.error = '失败'
t.enable = '启用'
t.disable = '停用'
t.pause = '暂停'
t.resume = '恢复'
t.run = '运行'
t.register = '注册'
t.forgetPassword = '忘记密码'

/**
 * 基本表结构
 */
t.base = {}
t.base.top = '指定'
t.base.sort = '排序'
t.base.name = '名称'
t.base.category = '分类'
t.base.code = '编码'
t.base.type = '类型'
t.base.value = '值'
t.base.cover = '封面'
t.base.createTime = '创建时间'
t.base.updateTime = '修改时间'
t.base.title = '标题'
t.base.content = '内容'
t.base.remark = '备注'
t.base.status = '状态'
t.base.success = '成功'
t.base.error = '失败'
t.base.mobile = '手机号'
t.base.send = '发送'
t.base.param = '参数'

t.prompt = {}
t.prompt.apierror = '接口调用失败'
t.prompt.title = '提示'
t.prompt.info = '确定进行[{handle}]操作?'
t.prompt.success = '操作成功'
t.prompt.failed = '操作失败'
t.prompt.deleteBatch = '请选择删除项'

t.validate = {}
t.validate.required = '必填项不能为空'
t.validate.format = '{attr}格式错误'

t.uploadText = '将文件拖到此处，或<em>点击上传</em>'
t.uploadTip = '只支持{format}格式文件!'
t.uploadSizetip = '上传文件大小不能超过 {size}MB'
t.uploadButton = '点击上传'

t.datePicker = {}
t.datePicker.range = '至'
t.datePicker.start = '开始日期'
t.datePicker.end = '结束日期'

t.fullscreen = {}
t.fullscreen.prompt = '您的浏览器不支持此操作'

t.updatePassword = {}
t.updatePassword.title = '修改密码'
t.updatePassword.username = '账号'
t.updatePassword.password = '原密码'
t.updatePassword.newPassword = '新密码'
t.updatePassword.confirmPassword = '确认密码'
t.updatePassword.validate = {}
t.updatePassword.validate.confirmPassword = '确认密码与新密码输入不一致'

t.contentTabs = {}
t.contentTabs.closeCurrent = '关闭当前标签页'
t.contentTabs.closeOther = '关闭其它标签页'
t.contentTabs.closeAll = '关闭全部标签页'

/* 页面 */
t.notFound = {}
t.notFound.desc = '抱歉！您访问的页面<em>失联</em>啦...'
t.notFound.back = '上一页'
t.notFound.home = '首页'

t.login = {}
t.login.title = '登录'
t.login.username = '用户名'
t.login.password = '密码'
t.login.captcha = '验证码'
t.login.copyright = '版权所有 2019 © '

/* 模块 */
t.model = {}
t.model.name = '名称'
t.model.key = '标识'
t.model.version = '版本'
t.model.createTime = '创建时间'
t.model.lastUpdateTime = '更新时间'
t.model.design = '在线设计'
t.model.deploy = '部署'
t.model.description = '描述'

t.process = {}
t.process.name = '名称'
t.process.key = '标识'
t.process.deployFile = '部署流程文件'
t.process.id = '流程ID'
t.process.deploymentId = '部署ID'
t.process.version = '版本'
t.process.resourceName = 'XML'
t.process.diagramResourceName = '图片'
t.process.deploymentTime = '部署时间'
t.process.active = '激活'
t.process.suspend = '挂起'
t.process.convertToModel = '转换为模型'

t.running = {}
t.running.id = '实例ID'
t.running.definitionKey = '定义Key'
t.running.processDefinitionId = '定义ID'
t.running.processDefinitionName = '定义名称'
t.running.activityId = '当前环节'
t.running.suspended = '是否挂起'
t.running.suspended0 = '否'
t.running.suspended1 = '是'

t.schedule = {}
t.schedule.beanName = 'bean名称'
t.schedule.beanNameTips = 'spring bean名称, 如: testTask'
t.schedule.pauseBatch = '暂停'
t.schedule.resumeBatch = '恢复'
t.schedule.runBatch = '执行'
t.schedule.log = '日志列表'
t.schedule.params = '参数'
t.schedule.cronExpression = 'cron表达式'
t.schedule.cronExpressionTips = '如: 0 0 12 * * ?'
t.schedule.remark = '备注'
t.schedule.status = '状态'
t.schedule.status0 = '暂停'
t.schedule.status1 = '正常'
t.schedule.statusLog0 = '失败'
t.schedule.statusLog1 = '成功'
t.schedule.pause = '暂停'
t.schedule.resume = '恢复'
t.schedule.run = '执行'
t.schedule.jobId = '任务ID'
t.schedule.times = '耗时(单位: 毫秒)'
t.schedule.createDate = '执行时间'

t.mail = {}
t.mail.name = '名称'
t.mail.config = '邮件配置'
t.mail.subject = '主题'
t.mail.createDate = '创建时间'
t.mail.send = '发送邮件'
t.mail.content = '内容'
t.mail.smtp = 'SMTP'
t.mail.port = '端口号'
t.mail.username = '邮箱账号'
t.mail.password = '邮箱密码'
t.mail.mailTo = '收件人'
t.mail.mailCc = '抄送'
t.mail.params = '模板参数'
t.mail.paramsTips = '如：{"code": "123456"}'
t.mail.templateId = '模版ID'
t.mail.status = '状态'
t.mail.status0 = '发送失败'
t.mail.status1 = '发送成功'
t.mail.mailFrom = '发送者'
t.mail.createDate = '发送时间'

t.sms = {}
t.sms.mobile = '手机号'
t.sms.status = '状态'
t.sms.status0 = '发送失败'
t.sms.status1 = '发送成功'
t.sms.config = '短信配置'
t.sms.send = '发送短信'
t.sms.platform = '平台类型'
t.sms.platform1 = '阿里云'
t.sms.platform2 = '腾讯云'
t.sms.platform3 = '聚合'
t.sms.params = '参数'
t.sms.paramsTips = '如：{"code": "123456"}'
t.sms.params1 = '参数1'
t.sms.params2 = '参数2'
t.sms.params3 = '参数3'
t.sms.params4 = '参数4'
t.sms.createDate = '发送时间'
t.sms.aliyunAccessKeyId = 'Key'
t.sms.aliyunAccessKeyIdTips = '阿里云AccessKeyId'
t.sms.aliyunAccessKeySecret = 'Secret'
t.sms.aliyunAccessKeySecretTips = '阿里云AccessKeySecret'
t.sms.aliyunSignName = '短信签名'
t.sms.aliyunTemplateCode = '短信模板'
t.sms.aliyunTemplateCodeTips = '短信模板CODE'
t.sms.qcloudAppId = 'AppId'
t.sms.qcloudAppIdTips = '腾讯云AppId'
t.sms.qcloudAppKey = 'AppKey'
t.sms.qcloudAppKeyTips = '腾讯云AppKey'
t.sms.qcloudSignName = '短信签名'
t.sms.qcloudTemplateId = '短信模板'
t.sms.qcloudTemplateIdTips = '短信模板ID'

t.dept = {}
t.dept.name = '名称'
t.dept.parentName = '上级部门'
t.dept.sort = '排序'
t.dept.parentNameDefault = '一级部门'

t.dict = {}
t.dict.dictName = '名称'
t.dict.dictType = '类型'
t.dict.dictValue = '值'
t.dict.sort = '排序'
t.dict.remark = '备注'
t.dict.createDate = '创建时间'

t.menu = {}
t.menu.name = '名称'
t.menu.icon = '图标'
t.menu.type = '类型'
t.menu.type0 = '菜单/页面'
t.menu.type1 = '按钮/接口'
t.menu.sort = '排序'
t.menu.url = '路由'
t.menu.permissions = '授权标识'
t.menu.permissionsTips = '多个用逗号分隔，如：uc:user:save,uc:user:update'
t.menu.parentName = '上级菜单'
t.menu.parentNameDefault = '一级菜单'
t.menu.resource = '授权资源'
t.menu.resourceUrl = '资源URL'
t.menu.resourceMethod = '请求方式'
t.menu.resourceAddItem = '添加一项'

t.params = {}
t.params.code = '编码'
t.params.content = '值'
t.params.remark = '备注'

t.role = {}
t.role.name = '名称'
t.role.remark = '备注'
t.role.createDate = '创建时间'
t.role.menuList = '菜单授权'
t.role.deptList = '数据授权'

t.user = {}
t.user.head = '头像'
t.user.username = '用户名'
t.user.nickName = '昵称'
t.user.idNo = '身份证号'
t.user.address = '地址'
t.user.deptName = '所属部门'
t.user.email = '邮箱'
t.user.mobile = '手机号'
t.user.status = '状态'
t.user.status0 = '停用'
t.user.status1 = '正常'
t.user.createDate = '创建时间'
t.user.password = '密码'
t.user.confirmPassword = '确认密码'
t.user.realName = '真实姓名'
t.user.gender = '性别'
t.user.gender0 = '男'
t.user.gender1 = '女'
t.user.gender2 = '保密'
t.user.roleIdList = '角色配置'
t.user.role = '角色'
t.user.validate = {}
t.user.validate.confirmPassword = '确认密码与密码输入不一致'

export default t
