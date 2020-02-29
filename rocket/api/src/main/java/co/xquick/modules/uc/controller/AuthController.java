package co.xquick.modules.uc.controller;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.exception.ErrorCode;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.DateUtils;
import co.xquick.booster.util.JacksonUtils;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.common.util.AESUtils;
import co.xquick.modules.msg.dto.SmsLogDTO;
import co.xquick.modules.msg.dto.SmsSendRequest;
import co.xquick.modules.msg.service.SmsLogService;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.uc.dto.LoginConfigDTO;
import co.xquick.modules.uc.dto.LoginRequest;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 授权接口
 * 注意: 本controller所有接口无需授权可直接访问
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

    @GetMapping("captcha")
    @ApiOperation(value = "生成验证码图片")
    public Result<?> captcha() {
        String uuid = UUID.randomUUID().toString();
        String image = captchaService.createBase64(uuid);
        Map<String, Object> map = new HashMap<>(2);
        map.put("uuid", uuid);
        map.put("image", image);
        // 将key和base64返回给前端
        return new Result<>().ok(map);
    }

    @GetMapping("loginConfig")
    @ApiOperation(value = "获取登录配置")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "type", required = true)
    public Result<?> loginConfig(@RequestParam String type) {
        LoginConfigDTO loginConfig = paramService.getContentObject(Constant.LOGIN_CONFIG_KEY + "_" + type.toUpperCase(), LoginConfigDTO.class, null);
        AssertUtils.isNull(loginConfig, ErrorCode.UNKNOWN_LOGIN_TYPE);

        return new Result<>().ok(loginConfig);
    }

    @PostMapping("sendSmsCode")
    @ApiOperation("发送验证码短信")
    @LogOperation("发送验证码短信")
    public Result<?> sendSmsCode(@RequestBody SmsSendRequest dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);
        // 先校验手机号是否1分钟内发送过
        SmsLogDTO lastSmsLog = smsLogService.findLastLogByTplCode(dto.getTplCode(), dto.getMobile());
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
     * 支持帐号登录、短信登录
     */
    @PostMapping("loginEncrypt")
    @ApiOperation(value = "加密登录")
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
    public Result<?> login(HttpServletRequest request, @RequestBody LoginRequest login) {
        // 效验数据
        ValidatorUtils.validateEntity(login, DefaultGroup.class);

        return userService.login(request, login);
    }

    /**
     * 通过短信验证码修改密码
     * 忘记密码功能,通过短信验证码找回
     */
    @PostMapping("changePasswordBySmsCode")
    @ApiOperation(value = "通过短信验证码修改密码")
    public Result<?> changePasswordBySmsCode(HttpServletRequest request, @RequestBody LoginRequest login) {
        // 效验数据
        ValidatorUtils.validateEntity(login, DefaultGroup.class);

        return userService.login(request, login);
    }
}
