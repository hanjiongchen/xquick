package co.xquick.modules.wx.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信支付信息
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@Data
@ApiModel(value = "微信支付信息")
public class WxPayProp implements Serializable {

    @ApiModelProperty(value = "微信公众号或者小程序等的appid")
    private String appId;

    @ApiModelProperty(value = "微信支付商户号")
    private String mchId;

    @ApiModelProperty(value = "微信支付商户密钥")
    private String mchKey;

    @ApiModelProperty(value = "服务商模式下的子商户公众账号ID，普通模式请不要配置")
    private String subAppId;

    @ApiModelProperty(value = "服务商模式下的子商户号，普通模式请不要配置")
    private String subMchId;

    @ApiModelProperty(value = "apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定")
    private String keyPath;

}
