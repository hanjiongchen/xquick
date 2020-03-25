package co.xquick.modules.shop.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.common.util.ExcelUtils;
import co.xquick.modules.shop.dto.SpuDTO;
import co.xquick.modules.shop.excel.SpuExcel;
import co.xquick.modules.shop.service.SpuService;
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
 * 商品spu
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/spu")
@Api(tags="商品spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:spu:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SpuDTO> list = spuService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:spu:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SpuDTO> page = spuService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:spu:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        SpuDTO data = spuService.getDtoById(id);

        return new Result<SpuDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:spu:save")
    public Result<?> save(@RequestBody SpuDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        spuService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:spu:update")
    public Result<?> update(@RequestBody SpuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        spuService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:spu:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        spuService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("shop:spu:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        spuService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("shop:spu:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SpuDTO> list = spuService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "商品spu", list, SpuExcel.class);
    }

}
