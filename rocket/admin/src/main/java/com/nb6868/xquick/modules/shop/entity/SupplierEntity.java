package com.nb6868.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nb6868.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 供应商
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_supplier")
public class SupplierEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
	private String code;
    /**
     * 名称
     */
	private String name;
    /**
     * 图片
     */
	private String imgs;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 状态0 未审核 1 已审核
     */
	private Integer status;
    /**
     * 内容
     */
	private String content;
}
