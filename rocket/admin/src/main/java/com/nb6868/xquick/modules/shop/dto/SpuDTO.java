package com.nb6868.xquick.modules.shop.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/**
 * 商品spu
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "商品spu")
public class SpuDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "店铺id")
	private Long storeId;

	@ApiModelProperty(value = "品牌id")
	private Long brandId;

	@ApiModelProperty(value = "分类id")
	private Long categoryId;

	@ApiModelProperty(value = "供应商id")
	private Long supplierId;

	@ApiModelProperty(value = "供应商名称")
	private String supplierName;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "编号")
	private String sn;

	@ApiModelProperty(value = "是否需要物流", required = true)
	@Range(min = 0, max = 1, message = "是否需要物流取值0-1", groups = DefaultGroup.class)
	private Integer delivery;

	@ApiModelProperty(value = "是否上架", required = true)
	@Range(min = 0, max = 1, message = "是否上架取值0-1", groups = DefaultGroup.class)
	private Integer marketable;

	@ApiModelProperty(value = "是否置顶", required = true)
	@Range(min = 0, max = 1, message = "是否置顶取值0-1", groups = DefaultGroup.class)
	private Integer top;

	@ApiModelProperty(value = "类型", required = true)
	@Range(min = 0, max = 1, message = "类型取值1-3", groups = DefaultGroup.class)
	private Integer type;

	/**
	 * 限购方式0不限购 1 永久限购 2 按天限购 3 按周限购 4 按月限购 5 按年限购
	 */
	@ApiModelProperty(value = "限购方式", required = true)
	@Range(min = 0, max = 5, message = "限购方式取值1-3", groups = DefaultGroup.class)
	private Integer limitType;

	@ApiModelProperty(value = "限购数量")
	@Range(min = 0, max = 10000, message = "限购方式取值0-10000", groups = DefaultGroup.class)
	private Integer limitCount;

	@ApiModelProperty(value = "会员折扣", required = true)
	@Range(min = 0, max = 1, message = "会员折扣取值0-1", groups = DefaultGroup.class)
	private Integer memberDiscount;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "标签,逗号分隔")
	private String tags;

	@ApiModelProperty(value = "市场价")
	private BigDecimal marketPrice;

	@ApiModelProperty(value = "售价")
	private BigDecimal salePrice;

	@ApiModelProperty(value = "属性,不会影响价格、数量等业务")
	private String attrs;

	@ApiModelProperty(value = "规格类型", required = true)
	@Range(min = 0, max = 1, message = "规格类型取值0-1", groups = DefaultGroup.class)
	private Integer specType;

	@ApiModelProperty(value = "规格,与sku关联")
	private String specs;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "点击数")
	private Integer hits;

	@ApiModelProperty(value = "图片")
	private String imgs;

	@ApiModelProperty(value = "图文内容")
	private String content;

	@ApiModelProperty(value = "评分")
	private Float score;

	@ApiModelProperty(value = "分销提成比例")
	private BigDecimal distScale;

	@ApiModelProperty(value = "分销提成最大值")
	private BigDecimal distMaxVal;

	@ApiModelProperty(value = "分销提成最小值")
	private BigDecimal distMinVal;

	@ApiModelProperty(value = "分销提成值")
	private BigDecimal distVal;

	@ApiModelProperty(value = "是否可以加入购物车", required = true)
	@Range(min = 0, max = 1, message = "是否可以加入购物车取值0-1", groups = DefaultGroup.class)
	private Integer cartable;

}
