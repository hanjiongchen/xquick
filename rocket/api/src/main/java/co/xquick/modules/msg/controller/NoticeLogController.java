package co.xquick.modules.msg.controller;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.common.util.ExcelUtils;
import co.xquick.modules.msg.dto.NoticeLogDTO;
import co.xquick.modules.msg.excel.NoticeLogExcel;
import co.xquick.modules.msg.service.NoticeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 通知发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("msg/noticeLog")
@Api(tags="通知发送记录")
public class NoticeLogController {
    @Autowired
    private NoticeLogService noticeLogService;

    @GetMapping("list")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.LIMIT, value = "最大显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("msg:noticeLog:list")
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<NoticeLogDTO> list = noticeLogService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("msg:noticeLog:page")
    public Result page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<NoticeLogDTO> page = noticeLogService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("msg:noticeLog:info")
    public Result info(@RequestParam Long id){
        NoticeLogDTO data = noticeLogService.getDtoById(id);

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("msg:noticeLog:save")
    public Result save(@RequestBody NoticeLogDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        noticeLogService.saveOrUpdateDto(dto);

        return new Result();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("msg:noticeLog:update")
    public Result update(@RequestBody NoticeLogDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        noticeLogService.saveOrUpdateDto(dto);

        return new Result();
    }

    @PutMapping("read")
    @ApiOperation("设置已读")
    @LogOperation("设置已读")
    public Result read(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        noticeLogService.read(ids);

        return new Result();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:noticeLog:delete")
    public Result delete(@RequestBody List<Long> ids){
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        noticeLogService.logicDeleteByIds(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("msg:noticeLog:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<NoticeLogDTO> list = noticeLogService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, null, list, NoticeLogExcel.class);
    }

}
