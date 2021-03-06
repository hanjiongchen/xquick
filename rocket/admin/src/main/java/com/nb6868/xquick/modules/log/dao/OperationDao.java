package com.nb6868.xquick.modules.log.dao;

import com.nb6868.xquick.booster.dao.BaseDao;
import com.nb6868.xquick.modules.log.entity.OperationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface OperationDao extends BaseDao<OperationEntity> {

}
