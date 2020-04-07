package co.xquick.modules.shop.entity;

import co.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品spu
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_spu")
public class SpuEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
	private Long storeId;
    /**
     * 品牌id
     */
	private Long brandId;
    /**
     * 分类id
     */
	private Long categoryId;
    /**
     * 供应商id
     */
	private Long supplierId;
	/**
	 * 规格类型
	 */
	private Integer specType;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 编号
     */
	private String sn;
    /**
     * 是否需要物流
     */
	private Integer delivery;
    /**
     * 是否上架
     */
	private Integer marketable;
    /**
     * 是否置顶
     */
	private Integer top;
    /**
     * 类型 1 商品 2 积分兑换 3 赠品
     */
	private Integer type;
    /**
     * 名称
     */
	private String name;
    /**
     * 标题
     */
	private String title;
    /**
     * 标签,逗号分隔
     */
	private String tags;
	/**
	 * 限购方式0不限购 1 永久限购 2 按天限购 3 按周限购 4 按月限购 5 按年限购
	 */
	private Integer limitType;
	/**
	 * 限购数量
	 */
	private Integer limitCount;
	/**
	 * 会员折扣
	 */
	private Integer memberDiscount;
    /**
     * 市场价
     */
	private BigDecimal marketPrice;
    /**
     * 售价
     */
	private BigDecimal salePrice;
    /**
     * 属性,不会影响价格、数量等业务
     */
	private String attrs;
    /**
     * 规格,与sku关联
     */
	private String specs;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 点击数
     */
	private Integer hits;
    /**
     * 图片
     */
	private String imgs;
    /**
     * 图文内容
     */
	private String content;
    /**
     * 评分
     */
	private Float score;
}
