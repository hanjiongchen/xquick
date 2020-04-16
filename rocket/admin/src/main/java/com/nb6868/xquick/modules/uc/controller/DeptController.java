package com.nb6868.xquick.modules.uc.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.uc.dto.DeptDTO;
import com.nb6868.xquick.modules.uc.dto.DeptTreeDTO;
import com.nb6868.xquick.modules.uc.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("uc/dept")
@Api(tags = "部门管理")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("tree")
    @ApiOperation("树表")
    @RequiresPermissions("uc:dept:list")
    public Result<?> tree(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<DeptTreeDTO> list = deptService.treeList(params);

        return new Result<>().ok(list);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("uc:dept:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<DeptDTO> list = deptService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("uc:dept:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<DeptDTO> page = deptService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("uc:dept:info")
    public Result<?> info(@RequestParam Long id) {
        DeptDTO data = deptService.getDtoById(id);

        return new Result<DeptDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("uc:dept:save")
    public Result<?> save(@RequestBody DeptDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        deptService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("uc:dept:update")
    public Result<?> update(@RequestBody DeptDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        deptService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("uc:dept:delete")
    public Result<?> delete(@RequestParam Long id) {
        //效验数据
        AssertUtils.isEmpty(id, "id");

        deptService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("uc:dept:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        deptService.logicDeleteByIds(ids);

        return new Result<>();
    }

}
