package co.xquick.modules.uc.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
public class UserDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String realName;
    private String headUrl;
    private Integer gender;
    private String email;
    private String mobile;
    private Long deptId;
    private Long tenantId;
    private String password;
    private String token;
    private Integer status;
    private Integer type;
    private String nodeIds;
    private String regionCd;
    /**
     * 部门数据权限
     */
    private List<Long> deptIdList;

}
