package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.UserrankDao;
import co.xquick.modules.shop.dto.UserrankDTO;
import co.xquick.modules.shop.entity.UserrankEntity;
import co.xquick.modules.shop.service.UserrankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户等级
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class UserrankServiceImpl extends CrudServiceImpl<UserrankDao, UserrankEntity, UserrankDTO> implements UserrankService {

    @Override
    public QueryWrapper<UserrankEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<UserrankEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
