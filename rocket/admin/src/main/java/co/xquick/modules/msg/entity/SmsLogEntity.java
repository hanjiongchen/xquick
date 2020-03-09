package co.xquick.modules.msg.entity;

import co.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_sms_log")
public class SmsLogEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    private Long tplId;
    /**
     * 模板编码
     */
    private String tplCode;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 参数
     */
    private String params;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 发送结果
     */
    private String result;
    /**
     * 发送状态  0：失败  1：成功
     */
    private Integer status;
    /**
     * 消费状态 0 未消费 1 已消费
     */
    private Integer consumed;
}
