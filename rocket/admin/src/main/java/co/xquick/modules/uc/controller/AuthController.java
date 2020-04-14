package co.xquick.modules.uc.controller;

import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.exception.XquickException;
import co.xquick.booster.pojo.Kv;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.DateUtils;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.common.annotation.AnonAccess;
import co.xquick.common.annotation.LogOperation;
import co.xquick.common.util.AESUtils;
import co.xquick.modules.msg.MsgConst;
import co.xquick.modules.msg.dto.SmsSendRequest;
import co.xquick.modules.msg.entity.SmsLogEntity;
import co.xquick.modules.msg.service.SmsLogService;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.uc.UcConst;
import co.xquick.modules.uc.dto.*;
import co.xquick.modules.uc.service.CaptchaService;
import co.xquick.modules.uc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * 授权接口
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
@RestController
@RequestMapping("auth")
@Api(tags = "授权")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private ParamService paramService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private SmsLogService smsLogService;

    @GetMapping("loginChannel")
    @ApiOperation(value = "获取登录配置")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "type", required = true)
    @AnonAccess
    public Result<?> loginChannel(@RequestParam String type) {
        LoginChannelCfg content = paramService.getContentObject(UcConst.LOGIN_CHANNEL_CFG_PREFIX + type.toUpperCase(), LoginChannelCfg.class);
        AssertUtils.isEmpty(content, ErrorCode.UNKNOWN_LOGIN_TYPE);

        return new Result<>().ok(content);
    }

    @PostMapping("sendSmsCode")
    @ApiOperation("发送验证码短信")
    @LogOperation("发送验证码短信")
    @AnonAccess
    public Result<?> sendSmsCode(@RequestBody SmsSendRequest dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);
        // 只允许发送CODE_开头的模板
        if (!dto.getTplCode().startsWith(MsgConst.SMS_CODE_TPL_PREFIX)) {
            throw new XquickException("不支持的模板");
        }
        // 先校验手机号是否1分钟内发送过
        SmsLogEntity lastSmsLog = smsLogService.findLastLogByTplCode(dto.getTplCode(), dto.getMobile());
        if (null != lastSmsLog && DateUtils.timeDiff(lastSmsLog.getCreateTime()) < 60 * 1000) {
            // 1分钟内已经发送过了
            return new Result<>().error("短信发送请求过于频繁");
        }

        dto.setParam("{\"code\":\"" + RandomStringUtils.randomNumeric(4) + "\"}");
        smsLogService.send(dto);

        return new Result<>();
    }

    /**
     * 加密登录
     * 逻辑同login接口
     */
    @PostMapping("loginEncrypt")
    @ApiOperation(value = "加密登录")
    @AnonAccess
    public Result<?> loginEncrypt(HttpServletRequest request, @RequestBody String loginEncrypted) throws UnsupportedEncodingException {
        // 密文转json明文
        String loginRaw = AESUtils.decrypt(URLDecoder.decode(loginEncrypted, "utf-8"));
        // json明文转实体
        LoginRequest login = JacksonUtils.jsonToPojo(loginRaw, LoginRequest.class);

        // 效验数据
        ValidatorUtils.validateEntity(login, DefaultGroup.class);

        return userService.login(request, login);
    }

    /**
     * 登录
     * 支持帐号登录、短信登录
     */
    @PostMapping("login")
    @ApiOperation(value = "登录")
    @AnonAccess
    public Result<?> login(HttpServletRequest httpServletRequest, @RequestBody LoginRequest request) {
        // 效验数据
        ValidatorUtils.validateEntity(request, DefaultGroup.class);

        return userService.login(httpServletRequest, request);
    }

    /**
     * 通过短信验证码修改密码
     * 忘记密码功能,通过短信验证码找回
     */
    @PostMapping("changePasswordBySmsCode")
    @ApiOperation(value = "通过短信验证码修改密码")
    @AnonAccess
    public Result<?> changePasswordBySmsCode(@RequestBody ChangePasswordBySmsCodeRequest request) {
        // 效验数据
        ValidatorUtils.validateEntity(request, DefaultGroup.class);
        LoginCfg loginCfg = paramService.getContentObject(UcConst.LOGIN_CFG_ADMIN, LoginCfg.class);
        AssertUtils.isEmpty(loginCfg, ErrorCode.UNKNOWN_LOGIN_TYPE);
        if (!loginCfg.isForgetPassword()) {
            throw new XquickException("未开放修改密码功能");
        }

        return userService.changePasswordBySmsCode(request);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    @ApiOperation(value = "注册")
    @AnonAccess
    public Result<?> register(@RequestBody RegisterRequest request) {
        // 效验数据
        ValidatorUtils.validateEntity(request, DefaultGroup.class);
        LoginCfg loginCfg = paramService.getContentObject(UcConst.LOGIN_CFG_ADMIN, LoginCfg.class);
        AssertUtils.isEmpty(loginCfg, ErrorCode.UNKNOWN_LOGIN_TYPE);
        if (!loginCfg.isRegister()) {
            throw new XquickException("未开放注册");
        }

        return userService.register(request);
    }

}
