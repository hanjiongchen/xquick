package co.xquick.modules.msg.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.AssertUtils;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.msg.dto.MailTplDTO;
import co.xquick.modules.msg.service.MailTplService;
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
    public Result page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<MailTplDTO> page = mailTplService.pageDto(params);

        return new Result<>().ok(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("msg:mailTpl:info")
    public Result<MailTplDTO> info(@RequestParam Long id) {
        MailTplDTO mailTpl = mailTplService.getDtoById(id);

        return new Result<MailTplDTO>().ok(mailTpl);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("msg:mailTpl:save")
    public Result save(@RequestBody MailTplDTO dto) {
        //校验类型
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        mailTplService.saveOrUpdateDto(dto);

        return new Result();
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("msg:mailTpl:update")
    public Result update(@RequestBody MailTplDTO dto) {
        //校验类型
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        mailTplService.saveOrUpdateDto(dto);

        return new Result();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:mailTpl:delete")
    public Result delete(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        mailTplService.logicDeleteByIds(ids);

        return new Result();
    }

}
