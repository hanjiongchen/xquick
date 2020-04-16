package com.nb6868.xquick.modules.ba.controller;

import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.util.ExcelUtils;
import com.nb6868.xquick.modules.ba.dto.ExamUserDTO;
import com.nb6868.xquick.modules.ba.excel.ExamUserExcel;
import com.nb6868.xquick.modules.ba.service.ExamUserService;
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
 * 秉奥-用户检测
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("ba/examUser")
@Api(tags="秉奥-用户检测")
public class ExamUserController {
    @Autowired
    private ExamUserService examUserService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("ba:examUser:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<ExamUserDTO> list = examUserService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("ba:examUser:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<ExamUserDTO> page = examUserService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("ba:examUser:info")
    public Result<?> info(@RequestParam Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        ExamUserDTO data = examUserService.getDtoById(id);

        return new Result<ExamUserDTO>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("ba:examUser:save")
    public Result<?> save(@RequestBody ExamUserDTO dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        examUserService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("ba:examUser:update")
    public Result<?> update(@RequestBody ExamUserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        examUserService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("ba:examUser:delete")
    public Result<?> delete(@RequestBody Long id) {
        // 效验参数
        AssertUtils.isEmpty(id, "id");

        examUserService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("ba:examUser:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        // 效验参数
        AssertUtils.isListEmpty(ids, "id");

        examUserService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("ba:examUser:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<ExamUserDTO> list = examUserService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, "秉奥-用户检测", list, ExamUserExcel.class);
    }

}
