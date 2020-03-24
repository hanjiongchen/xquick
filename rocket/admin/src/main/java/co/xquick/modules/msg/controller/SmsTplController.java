package co.xquick.modules.msg.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.common.util.ExcelUtils;
import co.xquick.modules.msg.dto.SmsTplDTO;
import co.xquick.modules.msg.excel.SmsTplExcel;
import co.xquick.modules.msg.service.SmsTplService;
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
 * 短信模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("msg/smsTpl")
@Api(tags="短信模板")
public class SmsTplController {
    @Autowired
    private SmsTplService smsTplService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("msg:smsTpl:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<SmsTplDTO> list = smsTplService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("msg:smsTpl:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SmsTplDTO> page = smsTplService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("msg:smsTpl:info")
    public Result<?> get(@RequestParam Long id){
        SmsTplDTO data = smsTplService.getDtoById(id);

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("msg:smsTpl:save")
    public Result<?> save(@RequestBody SmsTplDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        smsTplService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("msg:smsTpl:update")
    public Result<?> update(@RequestBody SmsTplDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        smsTplService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:smsTpl:delete")
    public Result<?> delete(@RequestParam Long id){
        //效验数据
        AssertUtils.isEmpty(id, "id");

        smsTplService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("msg:smsTpl:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids){
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        smsTplService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("msg:smsTpl:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SmsTplDTO> list = smsTplService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SmsTplExcel.class);
    }

}
