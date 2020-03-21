package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.SpuDao;
import co.xquick.modules.shop.dto.SpuDTO;
import co.xquick.modules.shop.entity.SpuEntity;
import co.xquick.modules.shop.service.SpuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商品spu
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SpuServiceImpl extends CrudServiceImpl<SpuDao, SpuEntity, SpuDTO> implements SpuService {

    @Override
    public QueryWrapper<SpuEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SpuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
