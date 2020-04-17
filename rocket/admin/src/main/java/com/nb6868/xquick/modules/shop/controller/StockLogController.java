package com.nb6868.xquick.modules.shop.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.common.util.ExcelUtils;
import com.nb6868.xquick.modules.shop.dto.StockLogDTO;
import com.nb6868.xquick.modules.shop.excel.StockLogExcel;
import com.nb6868.xquick.modules.shop.service.StockLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 出入库记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/stockLog")
@Api(tags="出入库记录")
public class StockLogController {
    @Autowired
    private StockLogService stockLogService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:stockLog:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<StockLogDTO> list = stockLogService.listDto(params);

        return new Result<>().success(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:stockLog:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<StockLogDTO> page = stockLogService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:stockLog:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        StockLogDTO data = stockLogService.getDtoById(id);

        return new Result<StockLogDTO>().success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:stockLog:save")
    public Result<?> save(@RequestBody StockLogDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        stockLogService.saveDto(dto);

        return new Result<>().success(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:stockLog:update")
    public Result<?> update(@RequestBody StockLogDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        stockLogService.updateDto(dto);

        return new Result<>().success(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:stockLog:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        stockLogService.logicDeleteById(id);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("shop:stockLog:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<StockLogDTO> list = stockLogService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "出入库记录", list, StockLogExcel.class);
    }

}
