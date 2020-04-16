package com.nb6868.xquick.modules.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.exception.XquickException;
import com.nb6868.xquick.booster.pojo.Const;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.modules.shop.dao.SpuDao;
import com.nb6868.xquick.modules.shop.dto.SpuDTO;
import com.nb6868.xquick.modules.shop.entity.*;
import com.nb6868.xquick.modules.shop.service.*;
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
                .and("search", queryWrapper -> {
                    String search = (String) params.get("search");
                    queryWrapper.like("name", search).or().like("sn", search).like("title", search);
                })
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
        // todo 多规格支持
        if (dto.getSpecType() == 0) {
            // 单规格
            if (dto.getMarketPrice() == null || dto.getSalePrice() == null) {
                throw new XquickException("销售价不允许为空");
            }
        } else {
            // 多规格
            throw new XquickException("目前只支持单规格");
        }
    }

    @Override
    protected void afterSaveOrUpdateDto(boolean ret, SpuDTO dto, SpuEntity existedEntity, int type) {
        if (dto.getSpecType() == 0) {
            // 单规格
            if (0 == type) {
                // 新增
                SkuEntity sku = new SkuEntity();
                sku.setDefaultItem(Const.BooleanEnum.TRUE.value());
                sku.setSpuId(dto.getId());
                sku.setSpuImgs(null);
                sku.setStock(0);
                sku.setSn(dto.getSn());
                sku.setName("default");
                sku.setMarketPrice(dto.getMarketPrice());
                sku.setSalePrice(dto.getSalePrice());
                skuService.save(sku);
            } else if (1 == type) {
                // 修改
                SkuEntity sku = skuService.getDefaultItemBySpuId(dto.getId());
                sku.setMarketPrice(dto.getMarketPrice());
                sku.setSalePrice(dto.getSalePrice());
                skuService.updateById(sku);
            }
        } else {
            // 多规格
            throw new XquickException("暂不支持多规格");
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
