package com.nb6868.xquick.modules.log.controller;

import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.common.util.ExcelUtils;
import com.nb6868.xquick.modules.log.dto.ErrorDTO;
import com.nb6868.xquick.modules.log.excel.ErrorExcel;
import com.nb6868.xquick.modules.log.service.ErrorService;
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
 * 异常日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("log/error")
@Api(tags = "异常日志")
public class ErrorController {

    @Autowired
    private ErrorService logErrorService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startCreateTime", value = "开始时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endCreateTime", value = "结束时间", paramType = "query", dataType = "String")})
    @RequiresPermissions("log:error:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<ErrorDTO> page = logErrorService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("log:error:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<ErrorDTO> list = logErrorService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "异常日志", list, ErrorExcel.class);
    }

}
