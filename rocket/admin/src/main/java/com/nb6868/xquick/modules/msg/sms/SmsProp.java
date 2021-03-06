package com.nb6868.xquick.modules.msg.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 短信配置信息
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@ApiModel(value = "短信配置信息")
public class SmsProp implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "appKey")
    private String appKey;

    @ApiModelProperty(value = "appSecret")
    private String appSecret;

    @ApiModelProperty(value = "sign")
    private String sign;

    @ApiModelProperty(value = "tplId")
    private String tplId;

}
