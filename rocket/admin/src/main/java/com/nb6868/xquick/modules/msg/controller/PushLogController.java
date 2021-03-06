package com.nb6868.xquick.modules.msg.controller;

import com.nb6868.xquick.booster.pojo.Const;
import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.msg.dto.PushLogDTO;
import com.nb6868.xquick.modules.msg.dto.PushSendRequest;
import com.nb6868.xquick.modules.msg.push.AbstractPushService;
import com.nb6868.xquick.modules.msg.push.PushConfig;
import com.nb6868.xquick.modules.msg.push.PushFactory;
import com.nb6868.xquick.modules.msg.service.PushLogService;
import com.nb6868.xquick.modules.sys.service.ParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 消息推送记录
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("msg/pushLog")
@Api(tags="消息推送记录")
public class PushLogController {
    @Autowired
    private PushLogService pushLogService;
    @Autowired
    private ParamService paramService;

    @PostMapping("/send")
    @ApiOperation("发送消息推送")
    @LogOperation("发送消息推送")
    public Result<?> send(@RequestBody PushSendRequest dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class);

        PushConfig config = paramService.getContentObject(Const.PUSH_CONFIG_KEY, PushConfig.class);
        if (config == null) {
            return new Result<>().error("未找到对应的推送配置");
        }

        // 获取推送服务
        AbstractPushService service = PushFactory.build(config);

        // 发送推送
        service.send(config, dto.getPushType(), dto.getAlias(), dto.getTags(), dto.getTitle(), dto.getContent(), dto.getParams(), dto.getApnsProd());

        return new Result<>();
    }

    @GetMapping("list")
    @ApiOperation("列表")
    @RequiresPermissions("msg:pushLog:list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> params){
        List<?> list = pushLogService.listDto(params);

        return new Result<>().success(list);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("msg:pushLog:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<PushLogDTO> page = pushLogService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("msg:pushLog:info")
    public Result<?> info(@PathVariable("id") Long id){
        PushLogDTO data = pushLogService.getDtoById(id);

        return new Result<>().success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("msg:pushLog:save")
    public Result<?> save(@RequestBody PushLogDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        pushLogService.saveDto(dto);

        return new Result<>().success(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("msg:pushLog:update")
    public Result<?> update(@RequestBody PushLogDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        pushLogService.updateDto(dto);

        return new Result<>().success(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("msg:pushLog:delete")
    public Result<?> delete(@RequestParam Long id){
        //效验数据
        AssertUtils.isEmpty(id, "id");

        pushLogService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("msg:pushLog:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids){
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        pushLogService.logicDeleteByIds(ids);

        return new Result<>();
    }

}
