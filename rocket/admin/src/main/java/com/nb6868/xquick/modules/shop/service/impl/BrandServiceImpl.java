package com.nb6868.xquick.modules.shop.service.impl;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.modules.shop.dao.BrandDao;
import com.nb6868.xquick.modules.shop.dto.BrandDTO;
import com.nb6868.xquick.modules.shop.entity.BrandEntity;
import com.nb6868.xquick.modules.shop.service.BrandService;
import com.nb6868.xquick.modules.shop.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * 品牌
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class BrandServiceImpl extends CrudServiceImpl<BrandDao, BrandEntity, BrandDTO> implements BrandService {

    @Autowired
    private SpuService spuService;

    @Override
    public QueryWrapper<BrandEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<BrandEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .getQueryWrapper();
    }

    @Override
    public boolean logicDeleteById(Serializable id) {
        AssertUtils.isTrue(SqlHelper.retBool(spuService.query().eq("brand_id", id).count()), ErrorCode.COMMON_ERROR, "存在商品,不允许删除");
        return super.logicDeleteById(id);
    }

}
