---
description: xquick极速开发,如火箭发射卫星般加速您的软件开发速度
---

# xquick
![img](https://cdn4.iconfinder.com/data/icons/space-and-astronomy-1/800/rocket-128.png)
![img](https://cdn4.iconfinder.com/data/icons/space-and-astronomy-1/800/satellite-128.png)

xquick致力于搭建一套软件项目中经常遇到的一些常见需求,比如权限管理、消息管理、日志管理等。     
结合代码生成器工具,减轻开发人员的繁琐开发工作。

## 项目组成
项目前后端分离

* 管理后台由接口程序[火箭rocket🚀](https://github.com/zhangchaoxu/xquick-rocket)和前端程序[卫星satellite☄](https://github.com/zhangchaoxu/xquick-satellite)两个项目组成。
* 另外提供了一套可以独立运行的[代码生成器](https://github.com/zhangchaoxu/xquick-rocket/generator)用于基础代码的生成,减少工作量。

## 已实现功能模块
### 用户权限管理模块
包含用户管理、角色管理、部门管理、菜单/按钮/接口权限管理,支持基于角色的访问控制;

### 消息模块
包含短信和邮件的模板和发送记录管理,支持基于模板灵活配置下次内容,便于业务端直接调用;

### 日志模块
包含登录、操作、错误的日志记录,支持日志的查询,基于注解实现便捷的操作日志记录;

### 系统模块
包含存储管理、字典管理、系统参数管理、区域管理、日历管理等;

###内容模块
包含内容分类管理、内容管理,实现了一个简易的CMS系统;

## 待实现功能模块
* [] 微信模块
* [] 更加灵活的用户权限管理,目前RBAC0
* [] 定时任务模具
* [] 工作流引擎
* [] 更加强大和使用的代码生成工具,不再支持批量生成,单表操作,可以勾选字段和类型

## 图标logo
* [登录背景生成网站](https://trianglify.io)
* [avatar](https://www.iconfinder.com/iconsets/business-avatar-1)
* [logo](https://www.iconfinder.com/icons/2120156/astronaut_astronomy_rocket_science_space_icon)

## Thanks
 [人人开源](https://www.renren.io/)
 [Jeecg-Boot](http://www.jeecg.com/)
 [Bladex](https://bladex.vip/#/)

## 文档 
更多详细内容见[gitbook文档](https://zhangchaoxu.gitbook.io/xquick/)