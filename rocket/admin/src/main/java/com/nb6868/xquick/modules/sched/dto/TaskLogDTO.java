package com.nb6868.xquick.modules.sched.dto;

import com.nb6868.xquick.booster.pojo.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定时任务日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "定时任务日志")
public class TaskLogDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    private Long taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "任务状态    0：失败    1：成功")
    private Integer status;

    @ApiModelProperty(value = "失败信息")
    private String error;

    @ApiModelProperty(value = "耗时(单位：毫秒)")
    private Integer times;

}
