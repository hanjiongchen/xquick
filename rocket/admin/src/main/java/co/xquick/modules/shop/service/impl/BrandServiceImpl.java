package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.dao.BrandDao;
import co.xquick.modules.shop.dto.BrandDTO;
import co.xquick.modules.shop.entity.BrandEntity;
import co.xquick.modules.shop.service.BrandService;
import co.xquick.modules.shop.service.SpuService;
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
