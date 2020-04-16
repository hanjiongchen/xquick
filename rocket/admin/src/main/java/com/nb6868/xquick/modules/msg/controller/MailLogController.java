package com.nb6868.xquick.modules.msg.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.msg.dto.MailLogDTO;
import com.nb6868.xquick.modules.msg.dto.MailSendRequest;
import com.nb6868.xquick.modules.msg.service.MailLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 邮件发送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("msg/mailLog")
@Api(tags = "邮件发送记录")
public class MailLogController {

    @Autowired
    private MailLogService mailLogService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tplId", value = "tplId", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tplCode", value = "tplCode", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mailTo", value = "mailTo", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "status", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("msg:mailLog:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<MailLogDTO> page = mailLogService.pageDto(params);
        //测试
        return new Result<>().ok(page);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:mailLog:delete")
    public Result<?> delete(@RequestParam Long id) {
        //效验数据
        AssertUtils.isEmpty(id, "id");

        mailLogService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量")
    @LogOperation("批量")
    @RequiresPermissions("msg:mailLog:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        mailLogService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @PostMapping("/send")
    @ApiOperation("发送邮件")
    @LogOperation("发送邮件")
    @RequiresPermissions("msg:mailLog:save")
    public Result<?> send(@RequestBody MailSendRequest dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);

        boolean flag = mailLogService.send(dto);
        if (flag) {
            return new Result<>();
        }

        return new Result<>().error("邮件发送失败");
    }

}
