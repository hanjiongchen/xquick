package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.dao.StoreDao;
import co.xquick.modules.shop.dto.StoreDTO;
import co.xquick.modules.shop.entity.StoreEntity;
import co.xquick.modules.shop.service.BrandService;
import co.xquick.modules.shop.service.SpuService;
import co.xquick.modules.shop.service.StoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * 商城
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class StoreServiceImpl extends CrudServiceImpl<StoreDao, StoreEntity, StoreDTO> implements StoreService {

    @Autowired
    private SpuService spuService;
    @Autowired
    private BrandService brandService;

    @Override
    public QueryWrapper<StoreEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<StoreEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .getQueryWrapper();
    }

    @Override
    public boolean logicDeleteById(Serializable id) {
        AssertUtils.isTrue(SqlHelper.retBool(brandService.query().eq("store_id", id).count()), ErrorCode.COMMON_ERROR, "店铺存在品牌,不允许删除");
        AssertUtils.isTrue(SqlHelper.retBool(spuService.query().eq("store_id", id).count()), ErrorCode.COMMON_ERROR, "店铺存在商品,不允许删除");
        return super.logicDeleteById(id);
    }

}
