package com.nb6868.xquick.modules.sys.controller;

import com.nb6868.xquick.booster.pojo.Kv;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.util.DateUtils;
import com.nb6868.xquick.common.annotation.AnonAccess;
import com.nb6868.xquick.modules.uc.service.CaptchaService;
import com.sun.management.OperatingSystemMXBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * 系统接口
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@Api(tags = "首页接口")
public class IndexController {

    @GetMapping("/")
    @ApiOperation("系统信息")
    @AnonAccess
    public Result<?> index() {
        return new Result<>().success("api success");
    }

    @GetMapping("sys/info")
    @ApiOperation("系统信息")
    public Result<?> sysInfo() {
        OperatingSystemMXBean osmx = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        Kv data = Kv.init();
        data.set("sysTime", DateUtils.now());
        data.set("osName", System.getProperty("os.name"));
        data.set("osArch", System.getProperty("os.arch"));
        data.set("osVersion", System.getProperty("os.version"));
        data.set("userLanguage", System.getProperty("user.language"));
        data.set("userDir", System.getProperty("user.dir"));
        data.set("totalPhysical", osmx.getTotalPhysicalMemorySize() / 1024 / 1024);
        data.set("freePhysical", osmx.getFreePhysicalMemorySize() / 1024 / 1024);

        data.set("memoryRate", BigDecimal.valueOf((1 - osmx.getFreePhysicalMemorySize() * 1.0 / osmx.getTotalPhysicalMemorySize()) * 100).setScale(2, RoundingMode.HALF_UP));
        data.set("processors", osmx.getAvailableProcessors());
        data.set("jvmName", System.getProperty("java.vm.name"));
        data.set("javaVersion", System.getProperty("java.version"));
        data.set("javaHome", System.getProperty("java.home"));
        data.set("javaTotalMemory", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        data.set("javaFreeMemory", Runtime.getRuntime().freeMemory() / 1024 / 1024);
        data.set("javaMaxMemory", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        data.set("userName", System.getProperty("user.name"));
        data.set("systemCpuLoad", BigDecimal.valueOf(osmx.getSystemCpuLoad() * 100).setScale(2, RoundingMode.HALF_UP));
        data.set("userTimezone", System.getProperty("user.timezone"));

        return new Result<>().success(data);
    }

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("captcha/base64")
    @ApiOperation(value = "获得base64格式验证码图片")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "int", name = "width", value = "图片宽度"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "height", value = "图片高度")})
    @AnonAccess
    public Result<?> captcha(@RequestParam(required = false, defaultValue = "150") int width, @RequestParam(required = false, defaultValue = "50") int height) {
        String uuid = UUID.randomUUID().toString();
        // 随机取出一种
        String[] captchaTypes = {"arithmetic", "spec"};
        String image = captchaService.createBase64(uuid, width, height, captchaTypes[(int) (Math.random() * captchaTypes.length)]);
        // 将uuid和图片的base64返回给前端
        return new Result<>().success(Kv.init().set("uuid", uuid).set("image", image));
    }

}
