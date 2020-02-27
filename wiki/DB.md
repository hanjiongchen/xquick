# 数据库设计规范和实现
程序开发基于MySQL5.7实现,理论上支持Oracle、SQLServer、DB2等其它数据库  
_注意_ 修改表中各个数据库中有区别的地方 
_注意_ 修改Mapper文件中是否有MySQL特有的查询语句

## 数据持久化实现

数据库的持久化在对比JPA、Hibernate、MyBatis之后选择由MyBatis-Plus实现,兼具易用性和灵活性。

## 表命名规范

1. 数据库名称、表名称、字段名称一律小写,用\_分割
2. 遵守模块名称\_业务名称的形式,比如uc\_user
3. 关联表使用两个表名连接方式，比如uc\_user\_role
4. 建议所有表都带上id、deleted、create\_time、update\_time、create\_id、update\_id字段

## 主键

数据主键id使用MyBatisPlus的ID\_Worker，一律使用20位的unsigned tinyint，注意在pojo中使用Long定义 优点是兼具自增主键的连续性\(优化查询效率\)和uuid的不可推测性  
_注意_ JavaScript无法处理Java的Long,会导致精度丢失,具体表现为主键最后两位永远为0,解决思路:Long 转为 String 返回 [更多文档](https://mybatis.plus/guide/logic-delete.html)

## 逻辑删除

逻辑删除deleted使用1位的unsigned tinyint,1表示删除,0表示未删除  
在Entity中为deleted加上@TableLogic注解,对于使用Wrapper查询的sql会自动加上deleted = 1  
但是如果在mapper中做了自定义级联查询,需要手动加上deleted = 0的条件  
[更多文档](https://mybatis.plus/guide/logic-delete.html)

## 自动填充

自动填充通过AutoFillMetaObjectHandler实现,实现方式是填充到入参的entity内  
_注意_ 对于update方法只有update\(entity, updateWrapper\)才会自动填充，直接调用update\(updateWrapper\)是不会自动填充的 [更多文档](https://mybatis.plus/guide/auto-fill-metainfo.html)

## 分页

使用MybatisPlus的数据分页实现 [更多文档](https://mybatis.plus/guide/page.html)
更多问题见[MyBatisPlus文档](https://mybatis.plus/)

