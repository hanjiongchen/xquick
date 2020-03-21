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
import co.xquick.modules.shop.dto.BrandDTO;
import co.xquick.modules.shop.excel.BrandExcel;
import co.xquick.modules.shop.service.BrandService;
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
 * 品牌
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/brand")
@Api(tags="品牌")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:brand:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<BrandDTO> list = brandService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:brand:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<BrandDTO> page = brandService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:brand:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        BrandDTO data = brandService.getDtoById(id);

        return new Result<BrandDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:brand:save")
    public Result<?> save(@RequestBody BrandDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        brandService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:brand:update")
    public Result update(@RequestBody BrandDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        brandService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:brand:delete")
    public Result delete(@RequestBody Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        brandService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("shop:brand:deleteBatch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        brandService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("shop:brand:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<BrandDTO> list = brandService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "品牌", list, BrandExcel.class);
    }

}
