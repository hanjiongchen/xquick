package co.xquick.modules.shop.controller;

import co.xquick.booster.pojo.Kv;
import co.xquick.booster.pojo.Result;
import co.xquick.modules.shop.service.OrderService;
import co.xquick.modules.shop.service.SpuService;
import co.xquick.modules.uc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页数据
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/home")
@Api(tags="品牌")
public class HomeController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private SpuService spuService;

    @GetMapping("count")
    @ApiOperation("统计数据")
    // @RequiresRoles("shop-admin")
    public Result<?> count() {
        int orderCount = orderService.count();
        int userCount = userService.query().ge("type", 100).count();
        int spuCount = spuService.query().count();

        Kv data = Kv.init()
                .set("userCount", userCount)
                .set("orderCount", orderCount)
                .set("spuCount", spuCount);
        return new Result<>().ok(data);
    }

}
