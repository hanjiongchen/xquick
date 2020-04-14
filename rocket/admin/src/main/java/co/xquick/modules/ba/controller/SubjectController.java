package co.xquick.modules.ba.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.common.util.ExcelUtils;
import co.xquick.modules.ba.dto.SubjectDTO;
import co.xquick.modules.ba.excel.SubjectExcel;
import co.xquick.modules.ba.service.SubjectService;
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
 * 秉奥-检测题
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("ba/subject")
@Api(tags="秉奥-检测题")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("ba:subject:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SubjectDTO> list = subjectService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("ba:subject:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SubjectDTO> page = subjectService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("ba:subject:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        SubjectDTO data = subjectService.getDtoById(id);

        return new Result<SubjectDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("ba:subject:save")
    public Result<?> save(@RequestBody SubjectDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        subjectService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("ba:subject:update")
    public Result<?> update(@RequestBody SubjectDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        subjectService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("ba:subject:delete")
    public Result<?> delete(@RequestBody Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        subjectService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("ba:subject:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        subjectService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("ba:subject:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SubjectDTO> list = subjectService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "秉奥-检测题", list, SubjectExcel.class);
    }

}
