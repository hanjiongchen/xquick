package co.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import co.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_stock_log")
public class StockLogEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * spu id
     */
	private Long spuId;
    /**
     * sku id
     */
	private Long skuId;
    /**
     * 类型 0 入库 1 出库
     */
	private Integer type;
    /**
     * 入库数量
     */
	private Integer inQty;
    /**
     * 出库数量
     */
	private Integer outQty;
    /**
     * 出入库后库存
     */
	private Integer stock;
}