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
import co.xquick.modules.msg.dto.SmsSendRequestDTO;
import co.xquick.modules.msg.service.SmsLogService;
import co.xquick.modules.sys.service.ParamService;
import co.xquick.modules.uc.dto.LoginConfigDTO;
import co.xquick.modules.uc.dto.LoginRequestDTO;
import co.xquick.modules.uc.service.CaptchaService;
import co.xquick.modules.uc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
    @ApiOperation(value = "生成验证码图片", produces = "application/octet-stream")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "uuid", required = true)
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        // uuid不能为空
        AssertUtils.isBlank(uuid, ErrorCode.IDENTIFIER_NOT_NULL);

        // 生成图片验证码
        BufferedImage image = captchaService.create(uuid);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.close();
    }

    @GetMapping("loginConfig")
    @ApiOperation(value = "获取登录配置")
    @ApiImplicitParam(paramType = "query", dataType = "string", name = "type", required = true)
    public Result<?> loginConfig(@RequestParam String type) {
        LoginConfigDTO loginConfig = paramService.getContentObject(Constant.LOGIN_CONFIG_KEY + "_" + type.toUpperCase(), LoginConfigDTO.class, null);
        AssertUtils.isNull(loginConfig, ErrorCode.UNKNOWN_LOGIN_TYPE);

        return new Result<>().ok(loginConfig);
    }

    @PostMapping("sendLoginSms")
    @ApiOperation("发送登录验证码短信")
    @LogOperation("发送登录验证码短信")
    public Result<?> sendLoginSms(@RequestBody SmsSendRequestDTO dto) {
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
     * AES解密登录
     * 支持帐号登录、短信登录
     */
    @PostMapping("loginEncrypted")
    @ApiOperation(value = "帐号登录AES加密")
    public Result<?> loginEncrypted(HttpServletRequest request, @RequestBody String loginEncrypted) throws UnsupportedEncodingException {
        // 密文转json明文
        String loginRaw = AESUtils.decrypt(URLDecoder.decode(loginEncrypted, "utf-8"));
        // json明文转实体
        LoginRequestDTO login = JacksonUtils.jsonToPojo(loginRaw, LoginRequestDTO.class);
        // 效验数据
        ValidatorUtils.validateEntity(login, DefaultGroup.class);

        return new Result<>().ok(userService.login(request, login));
    }

    /**
     * 登录
     * 支持帐号登录、短信登录
     */
    @PostMapping("login")
    @ApiOperation(value = "帐号登录")
    public Result<?> login(HttpServletRequest request, @RequestBody LoginRequestDTO login) {
        // 效验数据
        ValidatorUtils.validateEntity(login, DefaultGroup.class);

        return new Result<>().ok(userService.login(request, login));
    }

}
