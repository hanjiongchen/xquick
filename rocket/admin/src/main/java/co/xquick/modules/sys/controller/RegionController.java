package co.xquick.modules.sys.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.sys.dto.RegionDTO;
import co.xquick.modules.sys.dto.RegionTreeDTO;
import co.xquick.modules.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 行政区域
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("sys/region")
@Api(tags="行政区域")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("treeList")
    @ApiOperation("树形列表")
    @ApiImplicitParams({
    })
    @RequiresPermissions("sys:region:list")
    public Result treeList(@ApiIgnore @RequestParam Map<String, Object> params){
        List<RegionTreeDTO> list = regionService.treeList(params);

        return new Result<>().ok(list);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    @ApiImplicitParams({
    })
    @RequiresPermissions("sys:region:list")
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<RegionDTO> list = regionService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
    })
    @RequiresPermissions("sys:region:page")
    public Result page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<RegionDTO> page = regionService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("sys:region:info")
    public Result info(@RequestParam Long id){
        RegionDTO data = regionService.getDtoById(id);

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:region:save")
    public Result save(@RequestBody RegionDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        regionService.saveOrUpdateDto(dto);

        return new Result();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:region:update")
    public Result update(@RequestBody RegionDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        regionService.saveOrUpdateDto(dto);

        return new Result();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:region:delete")
    public Result delete(@RequestBody List<Long> ids){
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        regionService.logicDeleteByIds(ids);

        return new Result();
    }

}
