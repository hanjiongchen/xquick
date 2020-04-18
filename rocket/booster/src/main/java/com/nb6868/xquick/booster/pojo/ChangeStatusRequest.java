package com.nb6868.xquick.booster.pojo;

import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新状态请求
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
public class ChangeStatusRequest implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "更新信息id不能为空", groups = DefaultGroup.class)
    private Long id;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空", groups = DefaultGroup.class)
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
