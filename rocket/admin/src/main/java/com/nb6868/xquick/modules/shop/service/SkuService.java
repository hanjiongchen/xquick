package com.nb6868.xquick.modules.shop.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.shop.dto.SkuDTO;
import com.nb6868.xquick.modules.shop.entity.SkuEntity;

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

    /**
     * 获得spu默认项
     */
    SkuEntity getDefaultItemBySpuId(Long spuId);
}
