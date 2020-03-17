package co.xquick.modules.uc.entity;

import co.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 苹果用户
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("uc_user_apple")
public class UserAppleEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 苹果用户id
     */
	private String userIdentifier;
    /**
     * App包名aud
     */
	private String packageName;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 邮箱是否验证
     */
	private Integer emailVerified;
    /**
     * 对应的用户id
     */
	private Long userId;
}
