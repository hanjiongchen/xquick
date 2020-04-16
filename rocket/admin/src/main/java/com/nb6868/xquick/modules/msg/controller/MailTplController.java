package com.nb6868.xquick.modules.msg.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.msg.dto.MailTplDTO;
import com.nb6868.xquick.modules.msg.service.MailTplService;
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
 * 邮件模板
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("msg/mailTpl")
@Api(tags = "邮件模板")
public class MailTplController {

    @Autowired
    private MailTplService mailTplService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "name", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "code", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("msg:mailTpl:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<?> page = mailTplService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("msg:mailTpl:info")
    public Result<?> info(@RequestParam Long id) {
        MailTplDTO dto = mailTplService.getDtoById(id);

        return new Result<>().ok(dto);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("msg:mailTpl:save")
    public Result<?> save(@RequestBody MailTplDTO dto) {
        //校验类型
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        mailTplService.saveDto(dto);

        return new Result<>().ok(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("msg:mailTpl:update")
    public Result<?> update(@RequestBody MailTplDTO dto) {
        //校验类型
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        mailTplService.updateDto(dto);

        return new Result<>().ok(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:mailTpl:delete")
    public Result<?> delete(@RequestParam Long id) {
        //效验数据
        AssertUtils.isEmpty(id, "id");

        mailTplService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("msg:mailTpl:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        mailTplService.logicDeleteByIds(ids);

        return new Result<>();
    }

}
