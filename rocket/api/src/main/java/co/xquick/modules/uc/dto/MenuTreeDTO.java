package co.xquick.modules.uc.dto;

import co.xquick.booster.util.TreeNode;
import co.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 菜单(树形)
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "菜单(树形)")
public class MenuTreeDTO extends TreeNode<MenuTreeDTO> {

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "{name.require}", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "类型  0：菜单   1：按钮")
    @Range(min = 0, max = 1, message = "{type.range}", groups = DefaultGroup.class)
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "权限")
    private String permissions;

    @ApiModelProperty(value = "排序")
    @Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
    private Integer sort;

    @ApiModelProperty(value = "上级菜单名称")
    private String parentName;

    @ApiModelProperty(value = "上级菜单列表")
    private List<MenuTreeDTO> parentMenuList;

}
