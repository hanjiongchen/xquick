package co.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.modules.shop.dao.SkuDao;
import co.xquick.modules.shop.dto.SkuDTO;
import co.xquick.modules.shop.entity.SkuEntity;
import co.xquick.modules.shop.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商品规格sku
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SkuServiceImpl extends CrudServiceImpl<SkuDao, SkuEntity, SkuDTO> implements SkuService {

    @Override
    public QueryWrapper<SkuEntity> getWrapper(String method, Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SkuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
