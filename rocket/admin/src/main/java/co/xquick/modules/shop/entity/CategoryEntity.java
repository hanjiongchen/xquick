package co.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import co.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 商品类别
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_category")
public class CategoryEntity extends BaseEntity {
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
