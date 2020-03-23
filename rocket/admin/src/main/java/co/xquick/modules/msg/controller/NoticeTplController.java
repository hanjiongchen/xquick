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
import co.xquick.modules.msg.dto.NoticeTplDTO;
import co.xquick.modules.msg.excel.NoticeTplExcel;
import co.xquick.modules.msg.service.NoticeTplService;
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
 * 通知模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("msg/noticeTpl")
@Api(tags="通知模板")
public class NoticeTplController {
    @Autowired
    private NoticeTplService noticeTplService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("msg:noticeTpl:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<NoticeTplDTO> list = noticeTplService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("msg:noticeTpl:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<NoticeTplDTO> page = noticeTplService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("msg:noticeTpl:info")
    public Result<?> info(@RequestParam Long id){
        NoticeTplDTO data = noticeTplService.getDtoById(id);

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("msg:noticeTpl:save")
    public Result<?> save(@RequestBody NoticeTplDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        noticeTplService.saveOrUpdateDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("msg:noticeTpl:update")
    public Result<?> update(@RequestBody NoticeTplDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        noticeTplService.saveOrUpdateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:noticeTpl:delete")
    public Result<?> delete(@RequestParam Long id){
        //效验数据
        AssertUtils.isEmpty(id, "id");

        noticeTplService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("msg:noticeTpl:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids){
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        noticeTplService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("msg:noticeTpl:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<NoticeTplDTO> list = noticeTplService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, null, list, NoticeTplExcel.class);
    }

}
