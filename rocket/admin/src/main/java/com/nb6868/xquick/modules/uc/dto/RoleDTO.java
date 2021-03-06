package com.nb6868.xquick.modules.uc.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色")
public class RoleDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @NotBlank(message = "{code.require}", groups = DefaultGroup.class)
    private String code;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "{name.require}", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单ID列表")
    private List<Long> menuIdList;

    @ApiModelProperty(value = "部门ID列表")
    private List<Long> deptIdList;

}
