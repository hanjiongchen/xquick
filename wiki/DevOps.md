# DevOps

# 前端部署
## 编译
在admin-vue目录中执行build.cmd(`npm run build:prod`)即可

## 部署
将编译所得的dist目录中所有的文件复制到前端服务器(比如Nginx、Apache、Tomcat),甚至是阿里云的oss都可以。

## Vue Router Mode
修改router/index.js中的的mode      
如果为hash,访问路径会出现`#`,比如`http://127.0.0.1/#/home`      
如果为history,可以避免出现#,但是在nginx中会出现访问404的问题,需要在配置文件中加入以下配置。
```
# 解决404
location / {
	try_files /$uri /$uri/ /index.html$args;
}
```

# 接口部署

## 编译
直接使用`mvn clean package -Dmaven.test.skip=true -P prod`即可得到所需的jar或者war包

## jar运行
_Spring Boot项目，推荐打成jar包的方式，部署到服务器上_ 

Spring Boot内置了Tomcat，可配置Tomcat的端口号、初始化线程数、最大线程数、连接超时时长、https等等，配置文件是application.yml

#### windows部署
`java -jar rest.jar --spring.profiles.active=prod`

#### linux部署
建议使用shell执行,可以指定运行环境、端口、context等
`nohup java -Dspring.profiles.active=prod -jar xquick-rocket.jar --server.port=8080 --server.servlet.context-path=/xquick-rocket 2>&1 | cronolog xquick-rocket-log.%Y-%m-%d.out >> /dev/null &` 

如果使用cronolog做日志分割，可能需要先安装cronolog`yum install -y cronolog httpd`

优化脚本如下
```text
process=`ps -fe|grep "xquick-rocket.jar" |grep -ivE "grep|cron" |awk '{print $2}'`
if [ !$process ];
then
echo "stop erp process $process ....."
kill -9 $process
sleep 1
fi
echo "start erp process....."
nohup java -Dspring.profiles.active=prod -jar xquick-rocket.jar --server.port=8080 --server.se
rvlet.context-path=/xquick-rocket 2>&1 | cronolog log.%Y-%m-%d.out >> /dev/null &
echo "start erp success!"
```

## tomcat部署

1. 将Application对应的pom文件中的packaging改为war
`<packaging>jar</packaging>`

2. 排除tomcat的依赖
```
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-tomcat</artifactId>
<scope>provided</scope>
</dependency>
<dependency>
<groupId>org.apache.tomcat.embed</groupId>
<artifactId>tomcat-embed-jasper</artifactId>
<scope>provided</scope>
</dependency>
```

3. 编译打包
`mvn clean package -Dmaven.test.skip=true -P prod`

## tomcat7部署

对于tomcat，按照上述方式直接部署可能出现错误`java.lang.NoClassDefFoundError: javax/el/ELManager`,这是由于tomcat7内置的el包版本太低。 解决办法是手动下载[el3.0](https://mvnrepository.com/artifact/javax.el/javax.el-api/3.0.0),放到tomcat的lib包中

# 其它

## 内置tomcat加入证书

一般建议通过nginx作为前置服务器做代理，但有时候遇到直接将tomcat作为前置应用的情况，同时又要求支持ssl协议，就需要将证书加入到SpringBoot内置Tomcat。

1. 申请证书: 适用于Tomcat的Https证书
2. 证书放到classpath: 将证书文件，比如xquick.idogfooding.com.pfx放到resources文件夹中，最后会打包到classpath中
3. 配置端口：在application.yml文件中配置http和https的端口
```
#https port
port: 8089 
#http port
http:
port: 8088
```

4. 配置application: 在启动Application,比如AdminApplication中加入以下配置

```text
@Value("${server.port}") private Integer httpsPort;   @Value("${server.http.port}") private Integer httpPort;   @Bean public TomcatServletWebServerFactory servletContainer() {
TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {    @Override
protected void postProcessContext(Context context) {
SecurityConstraint securityConstraint = new SecurityConstraint();
securityConstraint.setUserConstraint("CONFIDENTIAL");
SecurityCollection collection = new SecurityCollection();
collection.addPattern("/*");
securityConstraint.addCollection(collection);
context.addConstraint(securityConstraint);
}
};
tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
return tomcat; }   private Connector initiateHttpConnector() {
Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
connector.setPort(httpPort);
connector.setScheme("http");
// 不强制跳转为https
connector.setSecure(true);
// 访问http跳转到https
// connector.setRedirectPort(httpsPort);  return connector; }
```

5. 检查防火墙: 注意检查两个端口是否都在防火墙和云服务器安全策略中

## 跨域配置

跨域一般通过CORS解决，通过Nginx配置即可，CORS需要浏览器和服务器同时支持。目前，主流浏览器都支持该功能，Nginx配置如下所示：

```text
server {
   listen 80;
   server_name xquick.nb6868.com;
   location /xquick {
       alias /data/xquick-admin;
           index index.html;
       }
       location / {
           if ($request_method = 'OPTIONS') {
               add_header 'Access-Control-Allow-Origin' '$http_origin';
               add_header 'Access-Control-Allow-Credentials' 'true';
               add_header 'Access-Control-Allow-Methods' 'GET, POST, PUT, DELETE, OPTIONS';
               add_header 'Access-Control-Allow-Headers' 'DNT,X-CustomHeader,Keep-Alive,User-Age
               nt,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,token';
               add_header 'Access-Control-Max-Age' 1728000;
               add_header 'Content-Type' 'text/plain charset=UTF-8';
               add_header 'Content-Length' 0;
               return 204;
           }
           add_header 'Access-Control-Allow-Origin' '$http_origin';
           add_header 'Access-Control-Allow-Credentials' 'true';
           add_header 'Access-Control-Allow-Methods' 'GET, POST, PUT, DELETE, OPTIONS';
           add_header 'Access-Control-Allow-Headers' 'DNT,X-CustomHeader,Keep-Alive,User-Agent,X
           -Requested-With,If-Modified-Since,Cache-Control,Content-Type,token';
           proxy_pass http://localhost:8080;
           client_max_body_size 1024m;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header Host $host;
           proxy_redirect off;
   }
}
```

