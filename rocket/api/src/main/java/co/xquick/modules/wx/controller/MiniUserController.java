package co.xquick.modules.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.wx.config.WxProp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序用户接口
 *
 * @author Binary Wang
 * @author Charles
 */
@RestController
@RequestMapping("/wx/mini/user")
@Api(tags = "微信小程序-用户")
public class MiniUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ParamService paramService;

    @GetMapping("/login")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramCode", value = "微信配置参数表code", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "小程序换取code", paramType = "query", dataType = "String")
    })
    public Result login(@RequestParam String paramCode, @RequestParam String code) {
        // 初始化service
        WxMaService wxService = getWxService(paramCode);
        // todo router如何处理
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            //TODO 增加自己的逻辑，关联业务相关数据
            return new Result<>().ok(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return new Result().error(ErrorCode.WX_API_ERROR, e.toString());
        }
    }

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramCode", value = "微信配置参数表code", paramType = "query", dataType = "String")
    })
    @GetMapping("/info")
    public Result info(@RequestParam String paramCode, String sessionKey, String signature, String rawData, String encryptedData, String iv) {
        // 初始化service
        WxMaService wxService = getWxService(paramCode);
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return new Result<>().error(ErrorCode.WX_API_ERROR, "user check failed");
        }
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        return new Result<>().ok(userInfo);
    }

    @GetMapping("/phone")
    @ApiOperation("获取用户绑定手机号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramCode", value = "微信配置参数表code", paramType = "query", dataType = "String")
    })
    public Result phone(@RequestParam String paramCode, String sessionKey, String signature, String rawData, String encryptedData, String iv) {
        // 初始化service
        WxMaService wxService = getWxService(paramCode);
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return new Result<>().error(ErrorCode.WX_API_ERROR, "user check failed");
        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        return new Result<>().ok(phoneNoInfo);
    }

    /**
     * 获取微信Service
     * @param paramCode 参数编码
     * @return 微信Service
     */
    private WxMaService getWxService(String paramCode) {
        // 从参数表获取参数配置
        WxProp wxConfigProperties = paramService.getContentObject(paramCode, WxProp.class, null);
        AssertUtils.isNull(wxConfigProperties, ErrorCode.WX_CONFIG_ERROR);
        // 初始化service
        WxMaService wxService = new WxMaServiceImpl();
        wxService.setWxMaConfig(wxConfigProperties.toWxMaDefaultConfigImpl());
        return wxService;
    }

}
