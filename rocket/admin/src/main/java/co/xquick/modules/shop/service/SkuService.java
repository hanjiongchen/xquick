package co.xquick.modules.shop.service;

import co.xquick.booster.service.CrudService;
import co.xquick.modules.shop.dto.SkuDTO;
import co.xquick.modules.shop.entity.SkuEntity;

/**
 * 商品规格sku
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface SkuService extends CrudService<SkuEntity, SkuDTO> {

    /**
     * 修改sku库存数量
     *
     * @param id skuId
     * @param stock 正数添加,负数减少
     */
    boolean addStock(Long id, Integer stock);
}
