package co.xquick.modules.shop.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.shop.dao.SkuDao;
import co.xquick.modules.shop.dto.SkuDTO;
import co.xquick.modules.shop.entity.SkuEntity;
import co.xquick.modules.shop.service.SkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        return new WrapperUtils<SkuEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .eq("storeId", "store_id")
                .eq("spuId", "spu_id")
                .eq("sn", "sn")
                .and("search", queryWrapper -> {
                    String search = (String) params.get("search");
                    queryWrapper.like("name", search).or().like("sn", search);
                })
                .getQueryWrapper();
    }

    @Override
    public boolean addStock(Long id, Integer stock) {
        return update().eq("id", id).setSql("stock = stock + " + stock).update(new SkuEntity());
    }

    @Override
    public SkuEntity getDefaultItemBySpuId(Long spuId) {
        return query().eq("default_item", 1).eq("spu_id", spuId).last("LIMIT 1").one();
    }
}
