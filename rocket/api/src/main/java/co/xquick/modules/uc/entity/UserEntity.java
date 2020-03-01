package co.xquick.modules.uc.entity;

import co.xquick.booster.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_user")
public class UserEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 身份证号码
     */
    private String idNo;
    /**
     * 编号
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 头像
     */
    private String imgs;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 手机号区域
     */
    private String mobileArea;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 用户类型
     */
    private Integer type;
    /**
     * 状态  0：停用   1：正常
     */
    private Integer status;
    /**
     * 角色ids
     */
    @TableField(exist = false)
    private String roleIds;
    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleNames;

}
