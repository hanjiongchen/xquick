# 文件存储

系统支持本地上传和阿里云OSS存储两种方式

## 本地上传



## 阿里云OSS

### 阿里云OSS直传[STS方式](https://help.aliyun.com/document_detail/56286.html))
1. 按照[STS临时授权访问OSS](https://help.aliyun.com/document_detail/100624.html))添加角色和授权
2. 按照[OSS Bucket设置](https://help.aliyun.com/document_detail/32069.html))在bucket的跨域设置中创建跨域规则：
```
-   将allowed origins设置成  `*`
-   将allowed methods设置成  `PUT, GET, POST, DELETE, HEAD`
-   将allowed headers设置成  `*`
-   将expose headers设置成  `etag`  `x-oss-request-id`
```
3. 服务器接口提供一个getSts接口
4. 前端使用阿里云[上传组件Browser.js](http://help.aliyun.com/document_detail/64041.html)上传文件
