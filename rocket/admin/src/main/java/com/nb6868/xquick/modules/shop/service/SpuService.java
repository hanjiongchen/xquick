package com.nb6868.xquick.modules.shop.service;

import com.nb6868.xquick.booster.service.CrudService;
import com.nb6868.xquick.modules.shop.dto.SpuDTO;
import com.nb6868.xquick.modules.shop.entity.SpuEntity;

/**
 * 商品spu
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public interface SpuService extends CrudService<SpuEntity, SpuDTO> {

    /**
     * 级联删除
     *
     * @param id 类别id
     */
    boolean deleteCascade(Long id);

}
