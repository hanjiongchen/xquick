package com.nb6868.xquick.modules.shop.entity;

import com.nb6868.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品规格sku
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_sku")
public class SkuEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
	private Long spuId;
    /**
     * 商品图片,空则使用procut表的imgs
     */
	private String spuImgs;
    /**
     * 市场价
     */
	private BigDecimal marketPrice;
    /**
     * 销售价
     */
	private BigDecimal salePrice;
    /**
     * 是否默认项
     */
	private Integer defaultItem;
    /**
     * 当前库存
     */
	private Integer stock;
	/**
     * 编号
     */
	private String sn;
	/**
	 * 名称
	 */
	private String name;
}
