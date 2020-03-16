package co.xquick.modules.uc.dto;

import co.xquick.booster.pojo.BaseDTO;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户")
public class UserDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "{username.require}", groups = DefaultGroup.class)
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{password.require}", groups = AddGroup.class)
    private String password;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "身份证号码")
    private String idNo;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "性别", required = true)
    @Range(min = 0, max = 2, message = "性别取值0-2", groups = DefaultGroup.class)
    private Integer gender;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "{email.error}", groups = DefaultGroup.class)
    private String email;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "{mobile.require}", groups = DefaultGroup.class)
    private String mobile;

    @ApiModelProperty(value = "部门ID")
    /**@NotNull(message = "{sysuser.deptId.require}", groups = DefaultGroup.class)*/
    private Long deptId;

    @ApiModelProperty(value = "状态  0：停用    1：正常", required = true)
    @Range(min = 0, max = 1, message = "状态值取值0-1", groups = DefaultGroup.class)
    private Integer status;

    @ApiModelProperty(value = "用户类别")
    @Range(min = 0, max = 100, message = "用户类别取值0-100", groups = DefaultGroup.class)
    private Integer type;

    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIdList;

    @ApiModelProperty(value = "角色Ids")
    private String roleIds;

    @ApiModelProperty(value = "角色名称")
    private String roleNames;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门链")
    private List<DeptDTO> deptChain;

}