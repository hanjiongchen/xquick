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
import co.xquick.modules.msg.dto.SmsLogDTO;
import co.xquick.modules.msg.dto.SmsSendRequest;
import co.xquick.modules.msg.excel.SmsLogExcel;
import co.xquick.modules.msg.service.SmsLogService;
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
 * 短信发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("msg/smsLog")
@Api(tags = "短信发送记录")
public class SmsLogController {
    @Autowired
    private SmsLogService smsLogService;

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("msg:smsLog:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<SmsLogDTO> list = smsLogService.listDto(params);

        return new Result<>().ok(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("msg:smsLog:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SmsLogDTO> page = smsLogService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("msg:smsLog:info")
    public Result<?> info(@RequestParam Long id) {
        SmsLogDTO data = smsLogService.getDtoById(id);

        return new Result<>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("msg:smsLog:save")
    public Result<?> save(@RequestBody SmsLogDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        smsLogService.saveOrUpdateDto(dto);

        return new Result<>();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("msg:smsLog:update")
    public Result<?> update(@RequestBody SmsLogDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        smsLogService.saveOrUpdateDto(dto);

        return new Result<>();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:smsLog:delete")
    public Result<?> delete(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        smsLogService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("msg:smsLog:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SmsLogDTO> list = smsLogService.listDto(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SmsLogExcel.class);
    }

    @PostMapping("/send")
    @ApiOperation("发送短信")
    @LogOperation("发送短信")
    @RequiresPermissions("msg:smsLog:save")
    public Result<?> send(@RequestBody SmsSendRequest dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, DefaultGroup.class);

        smsLogService.send(dto);

        return new Result<>();
    }

}
