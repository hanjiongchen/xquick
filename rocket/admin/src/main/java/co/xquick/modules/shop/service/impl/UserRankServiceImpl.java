package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.UserRankDao;
import co.xquick.modules.shop.dto.UserRankDTO;
import co.xquick.modules.shop.entity.UserRankEntity;
import co.xquick.modules.shop.service.UserRankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户等级
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class UserRankServiceImpl extends CrudServiceImpl<UserRankDao, UserRankEntity, UserRankDTO> implements UserRankService {

    @Override
    public QueryWrapper<UserRankEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<UserRankEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
