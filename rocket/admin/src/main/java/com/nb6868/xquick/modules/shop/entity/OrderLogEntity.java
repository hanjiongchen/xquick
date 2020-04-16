package com.nb6868.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nb6868.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_order_log")
public class OrderLogEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
	private Long orderId;
    /**
     * 类型
     */
	private Integer type;
    /**
     * 内容
     */
	private String content;
}
