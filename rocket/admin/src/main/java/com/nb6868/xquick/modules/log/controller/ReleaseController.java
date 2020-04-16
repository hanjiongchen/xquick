package com.nb6868.xquick.modules.log.controller;

import com.nb6868.xquick.common.annotation.AnonAccess;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.util.ExcelUtils;
import com.nb6868.xquick.modules.log.dto.ReleaseDTO;
import com.nb6868.xquick.modules.log.excel.ReleaseExcel;
import com.nb6868.xquick.modules.log.service.ReleaseService;
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
 * 更新日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("log/release")
@Api(tags = "更新日志")
public class ReleaseController {
    @Autowired
    private ReleaseService releaseService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("log:release:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<ReleaseDTO> list = releaseService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("log:release:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<ReleaseDTO> page = releaseService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("getLatestByCode")
    @ApiOperation("通过code获取最新的release")
    @AnonAccess
    public Result<?> getLatestByCode(@RequestParam String code) {
        // 效验参数
        AssertUtils.isEmpty(code, "code");

        ReleaseDTO data = releaseService.getLatestByCode(code);

        return new Result<ReleaseDTO>().ok(data);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("log:release:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        ReleaseDTO data = releaseService.getDtoById(id);

        return new Result<ReleaseDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("log:release:save")
    public Result<?> save(@RequestBody ReleaseDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        releaseService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("log:release:update")
    public Result<?> update(@RequestBody ReleaseDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        releaseService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("log:release:delete")
    public Result<?> delete(@RequestBody Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        releaseService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("log:release:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        releaseService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("log:release:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<ReleaseDTO> list = releaseService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "更新日志", list, ReleaseExcel.class);
    }

}
