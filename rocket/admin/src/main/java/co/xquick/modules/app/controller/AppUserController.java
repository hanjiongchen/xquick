package co.xquick.modules.app.controller;

import co.xquick.booster.pojo.Result;
import co.xquick.common.annotation.LogOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * App用户管理
 *
 * @author Charles
 */
@RestController
@RequestMapping("/app/uc/user")
@Api(tags = "用户管理")
public class AppUserController {

    @GetMapping("userInfo")
    @ApiOperation("获取用户ID")
    @LogOperation("sss")
    @RequiresGuest
    public Result<?> appUserInfo() {
        // Long userId = (Long) request.getAttribute("userId");
        return new Result<>().ok(1);
    }

}
