package co.xquick.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import co.xquick.booster.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 收件地址
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_receiver")
public class ReceiverEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
	private Long userId;
    /**
     * 区域名称,如浙江省,宁波市,鄞州区
     */
	private String regionName;
    /**
     * 区域编号,如33000,33010,33011
     */
	private String regionCode;
    /**
     * 详细门牌号
     */
	private String address;
    /**
     * 收件人
     */
	private String consignee;
    /**
     * 邮编
     */
	private String zipCode;
    /**
     * 收件人手机号
     */
	private String mobile;
    /**
     * 默认项
     */
	private Integer defaultItem;
}
