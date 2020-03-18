package co.xquick.modules.sys.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.sys.entity.ParamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 参数管理
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface ParamDao extends BaseDao<ParamEntity> {

    /**
     * 根据参数编码，查询content
     *
     * @param code 参数编码
     * @return 参数值
     */
    @Select("select content from sys_param where code = #{code} and deleted = 0 limit 1")
    String getContentByCode(@Param("code") String code);

}
