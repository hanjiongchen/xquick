package com.nb6868.xquick.modules.log.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.log.entity.ErrorEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface ErrorDao extends BaseDao<ErrorEntity> {

}
