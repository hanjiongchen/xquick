package co.xquick.modules.shop.service.impl;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.service.impl.CrudServiceImpl;
import co.xquick.booster.util.WrapperUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.shop.dao.SpuDao;
import co.xquick.modules.shop.dto.SpuDTO;
import co.xquick.modules.shop.entity.*;
import co.xquick.modules.shop.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * 商品spu
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class SpuServiceImpl extends CrudServiceImpl<SpuDao, SpuEntity, SpuDTO> implements SpuService {

    @Autowired
    SkuService skuService;
    @Autowired
    BrandService brandService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    SpuCategoryService categoryService;

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

    @Override
    protected void beforeSaveOrUpdateDto(SpuDTO dto, int type) {
        // 检查一下品牌
        if (dto.getBrandId() != null) {
            BrandEntity brand = brandService.getById(dto.getBrandId());
            AssertUtils.isEmpty(brand, ErrorCode.RECORD_NOT_EXISTED, "品牌");
        }
        // 检查一下分类
        if (dto.getCategoryId() != null) {
            SpuCategoryEntity category = categoryService.getById(dto.getCategoryId());
            AssertUtils.isEmpty(category, ErrorCode.RECORD_NOT_EXISTED, "分类");
            AssertUtils.isTrue(category.getPid() == 0L, ErrorCode.COMMON_ERROR, "请选择小类");
        }
        // 检查一下供应商
        if (dto.getSupplierId() != null) {
            SupplierEntity supplier = supplierService.getById(dto.getSupplierId());
            AssertUtils.isEmpty(supplier, ErrorCode.RECORD_NOT_EXISTED, "供应商");
        }
    }

    @Override
    public SpuDTO getDtoById(Serializable id) {
        SpuDTO dto = super.getDtoById(id);
        AssertUtils.isEmpty(dto, ErrorCode.RECORD_NOT_EXISTED);

        SupplierEntity supplier = supplierService.getById(dto.getSupplierId());
        if (supplier != null) {
            dto.setSupplierName(supplier.getName());
        }

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCascade(Long id) {
        // 删除相关sku数据
        skuService.logicDeleteByWrapper(new QueryWrapper<SkuEntity>().eq("spu_id", id));
        return super.logicDeleteById(id);
    }

}
