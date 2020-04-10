package co.xquick.modules.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.Const;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.HttpContextUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.common.annotation.GuestAccess;
import co.xquick.modules.log.entity.LoginEntity;
import co.xquick.modules.log.service.LoginService;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.wx.config.WxProp;
import co.xquick.modules.wx.dto.WxLoginRequest;
import co.xquick.modules.wx.service.UserWxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 微信小程序用户接口
 *
 * @author Binary Wang
 * @author Charles
 */
@RestController
@RequestMapping("/wx/ma/user")
@Api(tags = "微信小程序-用户")
public class MaUserController {

    @Autowired
    ParamService paramService;
    @Autowired
    UserWxService userWxService;
    @Autowired
    LoginService logLoginService;

    @PostMapping("/login")
    @ApiOperation("登录")
    @GuestAccess
    public Result<?> login(HttpServletRequest httpServletRequest, @RequestBody WxLoginRequest request) {
        // 效验数据
        ValidatorUtils.validateEntity(request, DefaultGroup.class);
        // 登录日志
        LoginEntity loginLog = new LoginEntity();
        loginLog.setType(UcConst.LoginTypeEnum.APP_WECHAT.value());
        loginLog.setCreateTime(new Date());
        loginLog.setIp(HttpContextUtils.getIpAddr(httpServletRequest));
        loginLog.setUserAgent(httpServletRequest.getHeader(HttpHeaders.USER_AGENT));

        // 初始化service
        WxMaService wxService = getWxService(request.getParamCode());
        String sessionKey;
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(request.getCode());
            sessionKey = session.getSessionKey();
        } catch (WxErrorException e) {
            loginLog.setResult(Const.ResultEnum.FAIL.value());
            loginLog.setMsg(e.getError().getErrorCode() + ":" + e.getError().getErrorMsg());
            logLoginService.save(loginLog);
            return new Result<>().error(ErrorCode.WX_API_ERROR);
        }

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, request.getRawData(), request.getSignature())) {
            loginLog.setResult(Const.ResultEnum.FAIL.value());
            loginLog.setMsg("校验微信用户信息失败");
            logLoginService.save(loginLog);
            return new Result<>().error(ErrorCode.WX_API_ERROR, "校验微信用户信息失败");
        }
        // 解密获得用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, request.getEncryptedData(), request.getIv());
        // UserWxEntity userWx = userWxService.getByAppIdAndOpenId(wxService.get);
        //TODO 增加自己的逻辑，关联业务相关数据
        return new Result<>().ok(userInfo);
    }

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramCode", value = "微信配置参数表code", paramType = "query", dataType = "String")
    })
    @GetMapping("/info")
    public Result<?> info(@RequestParam String paramCode, String sessionKey, String signature, String rawData, String encryptedData, String iv) {
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
    public Result<?> phone(@RequestParam String paramCode, String sessionKey, String signature, String rawData, String encryptedData, String iv) {
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
