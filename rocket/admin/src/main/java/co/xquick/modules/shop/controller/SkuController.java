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
import co.xquick.modules.shop.dto.SkuDTO;
import co.xquick.modules.shop.excel.SkuExcel;
import co.xquick.modules.shop.service.SkuService;
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
 * 商品规格sku
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/sku")
@Api(tags="商品规格sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:sku:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SkuDTO> list = skuService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:sku:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SkuDTO> page = skuService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:sku:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        SkuDTO data = skuService.getDtoById(id);

        return new Result<SkuDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:sku:save")
    public Result<?> save(@RequestBody SkuDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        skuService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:sku:update")
    public Result update(@RequestBody SkuDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        skuService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:sku:delete")
    public Result delete(@RequestBody Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        skuService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("shop:sku:deleteBatch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        skuService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("shop:sku:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SkuDTO> list = skuService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "商品规格sku", list, SkuExcel.class);
    }

}
