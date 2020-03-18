package co.xquick.modules.uc.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.modules.uc.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 系统用户Token
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Mapper
public interface TokenDao extends BaseDao<TokenEntity> {

    @Select("select user_id from uc_token where  token = #{token} and expire_date > now() and deleted = 0")
    Long getUserIdByToken(@Param("token") String token);

    @Select("select user_id, type from uc_token where  token = #{token} and expire_date > now() and deleted = 0")
    TokenEntity getUserIdAndTypeByToken(@Param("token") String token);

    @Select("select * from uc_token where token = #{token} and expire_date > NOW() and deleted = 0 limit 1")
    TokenEntity getByToken(@Param("token") String token);

    @Select("select * from uc_token where deleted = 0 and user_id = #{userId}")
    TokenEntity getByUserId(@Param("userId") Long userId);

    @Update("UPDATE uc_token SET token = #{token} where deleted = 0 and user_id = #{userId}")
    int updateToken(@Param("userId") Long userId, @Param("token") String token);

    @Update("UPDATE uc_token SET expire_date = DATE_ADD(NOW(), interval #{expire} second) WHERE token = #{token} and deleted = 0")
    int renewalToken(@Param("token") String token, @Param("expire") Long expire);

}
