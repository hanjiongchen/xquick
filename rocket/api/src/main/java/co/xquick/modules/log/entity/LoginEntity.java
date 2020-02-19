package co.xquick.modules.log.entity;

import co.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_login")
public class LoginEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 登录类型
     */
    private Integer type;
    /**
     * 用户操作   0：用户登录   1：用户退出
     */
    private Integer operation;
    /**
     * 状态  0：失败    1：成功    2：账号已锁定
     */
    private Integer status;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 用户名
     */
    private String createName;

}
