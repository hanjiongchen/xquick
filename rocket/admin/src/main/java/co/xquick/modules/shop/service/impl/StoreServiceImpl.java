package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.StoreDao;
import co.xquick.modules.shop.dto.StoreDTO;
import co.xquick.modules.shop.entity.StoreEntity;
import co.xquick.modules.shop.service.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商城
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class StoreServiceImpl extends CrudServiceImpl<StoreDao, StoreEntity, StoreDTO> implements StoreService {

    @Override
    public QueryWrapper<StoreEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<StoreEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
