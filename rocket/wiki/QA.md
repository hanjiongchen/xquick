---
description: Question & Answer
---

# QA

1. 为什么大多数请求都会请求两次?

由于前后端分离,往往会带来跨域的问题,对于非简单请求,浏览器会先发一个options请求判断服务是否连通,再发送真正的请求。 表现出来就是所有的请求都会发生两次,一次options,一次get\(put post patch delete\)。 避免的方法是所有的请求都改造成简单请求,但这往往是不实际的。 为了一定程度提高性能,我们增加一个CrosFilter,对所有的OPTIONS请求直接放行,好处是OPTIONS请求的时间会提高,缺点也很明显,失去了OPTIONS请求预检的意义。

2. `java.io.IOException: The temporary upload location [/tmp/tomcat.x.x/work/Tomcat/localhost/xxx] is not valid`

对于Multipart上传内容,Springboot会先把文件缓存在tmp文件夹中,然而在centos有一个定期清空tmp的机制,因此会导致文件上传功能长时间不用的时候缓存文件夹被清空掉,然后上传失败的问题。
注意该问题暴露在前端是错误`No 'Access-Control-Allow-Origin' header is present on the requested resource.`,比较容易误导人。
最简单的解决办法是配置文件中加上tomcat.basedir的路径
[参考](https://blog.csdn.net/qq_21383435/article/details/91891664)



