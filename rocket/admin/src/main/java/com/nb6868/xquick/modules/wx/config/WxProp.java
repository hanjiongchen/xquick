package com.nb6868.xquick.modules.wx.config;

import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信配置信息
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@ApiModel(value = "微信配置信息")
public class WxProp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "appId")
    private String appid;

    @ApiModelProperty(value = "secret")
    private String secret;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "EncodingAESKey")
    private String aesKey;

    @ApiModelProperty(value = "消息格式，XML或者JSON")
    private String msgDataFormat;

    /**
     * 转换为WxMaDefaultConfigImpl
     * @return WxMaDefaultConfigImpl
     */
    public WxMaDefaultConfigImpl toWxMaDefaultConfigImpl() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appid);
        config.setSecret(secret);
        config.setToken(token);
        config.setAesKey(aesKey);
        config.setMsgDataFormat(msgDataFormat);
        return config;
    }

}
