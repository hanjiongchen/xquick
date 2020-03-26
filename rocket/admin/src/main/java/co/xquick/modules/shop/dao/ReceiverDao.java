package co.xquick.modules.shop.dao;

import co.xquick.booster.dao.BaseDao;
import co.xquick.booster.pojo.Const;
import co.xquick.modules.shop.entity.ReceiverEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Mapper
public interface ReceiverDao extends BaseDao<ReceiverEntity> {

    @Select("SELECT shop_receiver.*, uc_user.username as user_name" +
            " FROM shop_receiver LEFT JOIN uc_user ON shop_receiver.user_id = uc_user.id" +
            " ${ew.customSqlSegment}")
    @Override
    <E extends IPage<ReceiverEntity>> E selectPage(@Param(Const.PAGE) E page, Wrapper<ReceiverEntity> ew);

}
