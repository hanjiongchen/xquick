package com.nb6868.xquick.modules.shop.service.impl;

import com.nb6868.xquick.booster.exception.ErrorCode;
import com.nb6868.xquick.booster.exception.XquickException;
import com.nb6868.xquick.booster.service.impl.CrudServiceImpl;
import com.nb6868.xquick.booster.util.WrapperUtils;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.modules.shop.ShopConst;
import com.nb6868.xquick.modules.shop.dao.StockLogDao;
import com.nb6868.xquick.modules.shop.dto.StockLogDTO;
import com.nb6868.xquick.modules.shop.entity.SkuEntity;
import com.nb6868.xquick.modules.shop.entity.SpuEntity;
import com.nb6868.xquick.modules.shop.entity.StockLogEntity;
import com.nb6868.xquick.modules.shop.service.SkuService;
import com.nb6868.xquick.modules.shop.service.SpuService;
import com.nb6868.xquick.modules.shop.service.StockLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Service
public class StockLogServiceImpl extends CrudServiceImpl<StockLogDao, StockLogEntity, StockLogDTO> implements StockLogService {

    @Autowired
    SkuService skuService;
    @Autowired
    SpuService spuService;

    @Override
    public QueryWrapper<StockLogEntity> getWrapper(String method, Map<String, Object> params) {
        return new WrapperUtils<StockLogEntity>(new QueryWrapper<>(), params)
                // 类型
                .eq("type", "type")
                .eq("spuId", "spu_id")
                .eq("skuId", "sku_id")
                // 搜索人
                .eq("createId", "create_id")
                // 创建时间区间
                .ge("startCreateTime", "create_time")
                .le("endCreateTime", "create_time")
                .getQueryWrapper();
    }

    @Override
    protected void beforeSaveOrUpdateDto(StockLogDTO dto, StockLogEntity toSaveEntity, int type) {
        // 检查商品规格
        SkuEntity skuEntity = skuService.getById(dto.getSkuId());
        AssertUtils.isEmpty(skuEntity, ErrorCode.RECORD_NOT_EXISTED, "商品规格");

        SpuEntity spuEntity = spuService.getById(skuEntity.getSpuId());
        AssertUtils.isEmpty(skuEntity, ErrorCode.RECORD_NOT_EXISTED, "商品");

        toSaveEntity.setSpuId(spuEntity.getId());
        toSaveEntity.setSpuName(spuEntity.getName());
        toSaveEntity.setSkuName(skuEntity.getName());

        if (type == ShopConst.StockLogTypeEnum.IN.value()) {
            // 入库
            toSaveEntity.setOutQty(0);
            if (dto.getInQty() <= 0) {
                throw new XquickException("入库数量不能小于0");
            }
            // 添加库存
            skuService.addStock(dto.getSkuId(), dto.getInQty());
            toSaveEntity.setStock(skuEntity.getStock() + dto.getInQty());
        } else if (type == ShopConst.StockLogTypeEnum.OUT.value()) {
            // 出库
            toSaveEntity.setInQty(0);
            if (dto.getOutQty() <= 0) {
                throw new XquickException("出库数量不能小于0");
            }
            if (skuEntity.getStock() < dto.getOutQty()) {
                throw new XquickException("出库数量不能超过当前库存");
            }
            // 减少库存
            skuService.addStock(dto.getSkuId(), -dto.getOutQty());
            toSaveEntity.setStock(skuEntity.getStock() - dto.getInQty());
        }
    }
}
