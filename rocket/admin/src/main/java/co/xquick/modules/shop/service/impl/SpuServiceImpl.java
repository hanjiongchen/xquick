package co.xquick.modules.shop.service.impl;

import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.modules.shop.dao.SpuDao;
import co.xquick.modules.shop.dto.SpuDTO;
import co.xquick.modules.shop.entity.SpuEntity;
import co.xquick.modules.shop.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        return new WrapperUtils<SpuEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .eq("storeId", "store_id")
                .eq("sn", "sn")
                .eq("status", "status")
                .eq("top", "top")
                .eq("type", "type")
                .eq("delivery", "delivery")
                .eq("marketable", "marketable")

                .getQueryWrapper();
    }

}
