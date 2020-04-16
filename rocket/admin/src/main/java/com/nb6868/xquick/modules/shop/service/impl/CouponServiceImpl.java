package com.nb6868.xquick.modules.shop.service.impl;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.modules.shop.dao.CouponDao;
import com.nb6868.xquick.modules.shop.dto.CouponDTO;
import com.nb6868.xquick.modules.shop.entity.CouponEntity;
import co.xquick.modules.shop.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb6868.xquick.modules.shop.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * 优惠券
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class CouponServiceImpl extends CrudServiceImpl<CouponDao, CouponEntity, CouponDTO> implements CouponService {

    @Autowired
    private StoreService storeService;
    @Autowired
    private SpuCategoryService spuCategoryService;
    @Autowired
    private SpuService spuService;
    @Autowired
    private SkuService skuService;

    @Override
    public QueryWrapper<CouponEntity> getWrapper(String method, Map<String, Object> params) {
        String name = (String)params.get("name");
        String type = (String)params.get("type");
        String status = (String)params.get("status");
        String storeName = (String)params.get("storeName");

        QueryWrapper<CouponEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "shop_coupon.name", name);
        wrapper.eq(StringUtils.isNotBlank(type), "shop_coupon.type", type);
        wrapper.eq(StringUtils.isNotBlank(status), "shop_coupon.status", status);
        wrapper.like(StringUtils.isNotBlank(storeName), "shop_store.name", storeName);

        return wrapper;
    }

    @Override
    public CouponDTO getDtoById(Serializable id) {
        CouponDTO dto = super.getDtoById(id);
        AssertUtils.isEmpty(dto, ErrorCode.RECORD_NOT_EXISTED);

        dto.setLimitSpuCategoryName(spuCategoryService.getSelectColumnById(dto.getLimitSpuCategoryId(), "name"));

        return dto;
    }

}
