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
import co.xquick.modules.shop.dto.SupplierDTO;
import co.xquick.modules.shop.excel.SupplierExcel;
import co.xquick.modules.shop.service.SupplierService;
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
 * 供应商
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/supplier")
@Api(tags="供应商")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:supplier:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SupplierDTO> list = supplierService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:supplier:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SupplierDTO> page = supplierService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:supplier:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        SupplierDTO data = supplierService.getDtoById(id);

        return new Result<SupplierDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:supplier:save")
    public Result<?> save(@RequestBody SupplierDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        supplierService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:supplier:update")
    public Result<?> update(@RequestBody SupplierDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        supplierService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:supplier:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        supplierService.logicDeleteById(id);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("shop:supplier:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SupplierDTO> list = supplierService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "供应商", list, SupplierExcel.class);
    }

}
