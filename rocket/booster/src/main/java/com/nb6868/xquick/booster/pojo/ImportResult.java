package com.nb6868.xquick.booster.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 数据导入结果
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="导入结果", description="数据导入结果")
public class ImportResult implements Serializable {

    @ApiModelProperty(value = "结果")
    private boolean result;

    @ApiModelProperty(value = "提示")
    private String msg;

}
