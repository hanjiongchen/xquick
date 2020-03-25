package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.dao.SupplierDao;
import co.xquick.modules.shop.dto.SupplierDTO;
import co.xquick.modules.shop.entity.SupplierEntity;
import co.xquick.modules.shop.entity.UserRankEntity;
import co.xquick.modules.shop.service.SpuService;
import co.xquick.modules.shop.service.SupplierService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * 供应商
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SupplierServiceImpl extends CrudServiceImpl<SupplierDao, SupplierEntity, SupplierDTO> implements SupplierService {

    @Autowired
    private SpuService spuService;

    @Override
    public QueryWrapper<SupplierEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<SupplierEntity>(new QueryWrapper<>(), params)
                .like("name", "name")
                .getQueryWrapper();
    }

    @Override
    public boolean logicDeleteById(Serializable id) {
        AssertUtils.isTrue(SqlHelper.retBool(spuService.query().eq("supplier_id", id).count()), ErrorCode.COMMON_ERROR, "店铺存在商品,不允许删除");
        return super.logicDeleteById(id);
    }
}
