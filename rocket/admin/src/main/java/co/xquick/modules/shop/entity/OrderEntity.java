package co.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import co.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 订单
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_order")
public class OrderEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
	private Integer status;
    /**
     * 订单号
     */
	private String no;
}
