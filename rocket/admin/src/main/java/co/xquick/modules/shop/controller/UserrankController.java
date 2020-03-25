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
import co.xquick.modules.shop.dto.UserrankDTO;
import co.xquick.modules.shop.excel.UserrankExcel;
import co.xquick.modules.shop.service.UserrankService;
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
 * 用户等级
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("shop/userrank")
@Api(tags="用户等级")
public class UserrankController {
    @Autowired
    private UserrankService userrankService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("shop:userrank:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<UserrankDTO> list = userrankService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("shop:userrank:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<UserrankDTO> page = userrankService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("shop:userrank:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        UserrankDTO data = userrankService.getDtoById(id);

        return new Result<UserrankDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("shop:userrank:save")
    public Result<?> save(@RequestBody UserrankDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        userrankService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("shop:userrank:update")
    public Result<?> update(@RequestBody UserrankDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        userrankService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("shop:userrank:delete")
    public Result<?> delete(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        userrankService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("shop:userrank:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        userrankService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("shop:userrank:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<UserrankDTO> list = userrankService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "用户等级", list, UserrankExcel.class);
    }

}
