package co.xquick.modules.log.controller;

import co.xquick.common.annotation.LogOperation;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.common.util.ExcelUtils;
import co.xquick.modules.log.dto.LoginDTO;
import co.xquick.modules.log.excel.LoginExcel;
import co.xquick.modules.log.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 登录日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("log/login")
@Api(tags = "登录日志")
public class LoginController {

    @Autowired
    private LoginService logLoginService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态  0：失败    1：成功    2：账号已锁定", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startCreateTime", value = "开始时间", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "endCreateTime", value = "结束时间", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "createName", value = "用户名", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("log:login:page")
    public Result page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<LoginDTO> page = logLoginService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态  0：失败    1：成功    2：账号已锁定", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "createName", value = "用户名", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("log:login:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<LoginDTO> list = logLoginService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "登录日志", list, LoginExcel.class);
    }

}
