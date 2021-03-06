package com.nb6868.xquick.modules.sys.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.sys.dto.DictDTO;
import com.nb6868.xquick.modules.sys.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 */
@RestController
@RequestMapping("sys/dict")
@Api(tags="数据字典")
public class DictController {
    @Autowired
    private DictService dictService;

    @GetMapping("page")
    @ApiOperation("字典分类")
    @RequiresPermissions("sys:dict:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<DictDTO> page = dictService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("list")
    @ApiOperation("字典分类数据")
    @RequiresPermissions("sys:dict:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<DictDTO> list = dictService.listDto(params);

        return new Result<List<DictDTO>>().success(list);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("sys:dict:info")
    public Result<DictDTO> info(@RequestParam Long id){
        DictDTO data = dictService.getDtoById(id);

        return new Result<DictDTO>().success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:dict:save")
    public Result<?> save(@RequestBody DictDTO dto){
        // 效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        dictService.saveDto(dto);

        return new Result<>();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:dict:update")
    public Result<?> update(@RequestBody DictDTO dto){
        // 效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        dictService.updateDto(dto);

        return new Result<>();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:dict:delete")
    public Result<?> delete(@RequestParam Long id){
        // 效验数据
        AssertUtils.isEmpty(id, "id");

        dictService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("sys:dict:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids){
        // 效验数据
        AssertUtils.isListEmpty(ids, "id");

        dictService.logicDeleteByIds(ids);

        return new Result<>();
    }

}
