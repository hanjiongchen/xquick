package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.ConvertUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.dao.CouponDao;
import co.xquick.modules.shop.dto.CouponDTO;
import co.xquick.modules.shop.entity.CouponEntity;
import co.xquick.modules.shop.entity.StoreEntity;
import co.xquick.modules.shop.service.CouponService;
import co.xquick.modules.shop.service.StoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
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

    @Override
    public QueryWrapper<CouponEntity> getWrapper(String method, Map<String, Object> params){
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

        StoreEntity storeEntity = storeService.getById(dto.getStoreId());
        if (storeEntity != null) {
            dto.setStoreName(storeEntity.getName());
        }

        return dto;
    }

    @Override
    public List<CouponDTO> listDto(Map<String, Object> params) {
        List<CouponEntity> entityList = list(getWrapper("list", params));
        List<CouponDTO> couponDTOList = ConvertUtils.sourceToTarget(entityList, currentDtoClass());
        for (CouponDTO couponDTO : couponDTOList) {
            StoreEntity storeEntity = storeService.getById(couponDTO.getStoreId());
            if (storeEntity != null) {
                couponDTO.setStoreName(storeEntity.getName());
            }
        }

        return couponDTOList;
    }
}
