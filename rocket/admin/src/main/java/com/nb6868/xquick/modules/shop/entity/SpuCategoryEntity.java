package com.nb6868.xquick.modules.shop.entity;

import com.nb6868.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shop_spu_category")
public class SpuCategoryEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 父id
     */
	private Long pid;
    /**
     * 店铺id
     */
	private Long storeId;
    /**
     * 店铺编码
     */
	private String storeCode;
    /**
     * 名称
     */
	private String name;
    /**
     * logo
     */
	private String logo;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 介绍
     */
	private String content;
}
