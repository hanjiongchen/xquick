package co.xquick.modules.sys.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.common.util.ExcelUtils;
import co.xquick.modules.sys.dto.ParamDTO;
import co.xquick.modules.sys.excel.ParamExcel;
import co.xquick.modules.sys.service.ParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("sys/param")
@Api(tags = "参数管理")
public class ParamController {

    @Autowired
    private ParamService paramService;

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sys:param:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<ParamDTO> page = paramService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("sys:param:info")
    public Result<?> info(@RequestParam Long id) {
        ParamDTO data = paramService.getDtoById(id);

        return new Result<>().ok(data);
    }

    @GetMapping("getByCode")
    @ApiOperation("通过code获取param")
    public Result<?> getByCode(@Param("code") String code) {
        ParamDTO dto = paramService.getByCode(code);

        return new Result<>().ok(dto);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sys:param:save")
    public Result<?> save(@RequestBody ParamDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        paramService.saveOrUpdateDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sys:param:update")
    public Result<?> update(@RequestBody ParamDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        paramService.saveOrUpdateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:param:delete")
    public Result<?> delete(@RequestParam Long id) {
        //效验数据
        AssertUtils.isEmpty(id, "id");

        paramService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("sys:param:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        paramService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("sys:param:export")
    @ApiImplicitParam(name = "code", value = "参数编码", paramType = "query", dataType = "String")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<ParamDTO> list = paramService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "参数", list, ParamExcel.class);
    }

}
