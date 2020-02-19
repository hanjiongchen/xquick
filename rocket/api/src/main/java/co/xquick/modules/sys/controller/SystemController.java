package co.xquick.modules.sys.controller;

import co.xquick.booster.pojo.Result;
import co.xquick.booster.util.DateUtils;
import com.sun.management.OperatingSystemMXBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统接口
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "系统接口")
public class SystemController {

    @GetMapping("info")
    @ApiOperation("系统信息")
    public Result info() {
        OperatingSystemMXBean osmx = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        Map<String, Object> map = new HashMap<>();
        map.put("sysTime", DateUtils.now());
        map.put("osName", System.getProperty("os.name"));
        map.put("osArch", System.getProperty("os.arch"));
        map.put("osVersion", System.getProperty("os.version"));
        map.put("userLanguage", System.getProperty("user.language"));
        map.put("userDir", System.getProperty("user.dir"));
        map.put("totalPhysical", osmx.getTotalPhysicalMemorySize() / 1024 / 1024);
        map.put("freePhysical", osmx.getFreePhysicalMemorySize() / 1024 / 1024);

        map.put("memoryRate", BigDecimal.valueOf((1 - osmx.getFreePhysicalMemorySize() * 1.0 / osmx.getTotalPhysicalMemorySize()) * 100).setScale(2, RoundingMode.HALF_UP));
        map.put("processors", osmx.getAvailableProcessors());
        map.put("jvmName", System.getProperty("java.vm.name"));
        map.put("javaVersion", System.getProperty("java.version"));
        map.put("javaHome", System.getProperty("java.home"));
        map.put("javaTotalMemory", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        map.put("javaFreeMemory", Runtime.getRuntime().freeMemory() / 1024 / 1024);
        map.put("javaMaxMemory", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        map.put("userName", System.getProperty("user.name"));
        map.put("systemCpuLoad", BigDecimal.valueOf(osmx.getSystemCpuLoad() * 100).setScale(2, RoundingMode.HALF_UP));
        map.put("userTimezone", System.getProperty("user.timezone"));

        return new Result<>().ok(map);
    }

}
