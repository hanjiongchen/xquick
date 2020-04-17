package com.nb6868.xquick.modules.sched.controller;

import com.nb6868.xquick.booster.pojo.PageData;
import com.nb6868.xquick.booster.pojo.Result;
import com.nb6868.xquick.booster.validator.AssertUtils;
import com.nb6868.xquick.booster.validator.ValidatorUtils;
import com.nb6868.xquick.booster.validator.group.AddGroup;
import com.nb6868.xquick.booster.validator.group.DefaultGroup;
import com.nb6868.xquick.booster.validator.group.UpdateGroup;
import com.nb6868.xquick.common.annotation.LogOperation;
import com.nb6868.xquick.modules.sched.dto.TaskDTO;
import com.nb6868.xquick.modules.sched.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("/sched/task")
@Api(tags = "定时任务")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresPermissions("sched:task:page")
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<TaskDTO> page = taskService.pageDto(params);

        return new Result<>().success(page);
    }

    @GetMapping("info")
    @ApiOperation("信息")
    @RequiresPermissions("sched:task:info")
    public Result<?> info(@RequestParam Long id) {
        TaskDTO data = taskService.getDtoById(id);

        return new Result<>().success(data);
    }

    @PostMapping("save")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("sched:task:save")
    public Result<?> save(@RequestBody TaskDTO dto) {
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        taskService.saveDto(dto);

        return new Result<>().success(dto);
    }

    @PutMapping("update")
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("sched:task:update")
    public Result<?> update(@RequestBody TaskDTO dto) {
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        taskService.updateDto(dto);

        return new Result<>().success(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("sched:task:delete")
    public Result<?> delete(@RequestParam Long id) {
		//效验数据
		AssertUtils.isEmpty(id, "id");

		taskService.logicDeleteById(id);

        return new Result<>();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    @LogOperation("批量删除")
    @RequiresPermissions("sched:task:deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> ids) {
        //效验数据
        AssertUtils.isListEmpty(ids, "id");

        taskService.logicDeleteByIds(ids);

        return new Result<>();
    }

    @PutMapping("/run")
    @ApiOperation("立即执行")
    @LogOperation("立即执行")
    @RequiresPermissions("sched:task:run")
    public Result<?> run(@RequestBody List<Long> ids) {
		//效验数据
		AssertUtils.isListEmpty(ids, "id");

        taskService.run(ids);

        return new Result<>();
    }

    @PutMapping("/pause")
    @ApiOperation("暂停")
    @LogOperation("暂停")
    @RequiresPermissions("sched:task:pause")
    public Result<?> pause(@RequestBody List<Long> ids) {
		//效验数据
		AssertUtils.isListEmpty(ids, "id");

		taskService.pause(ids);

        return new Result<>();
    }

    @PutMapping("/resume")
    @ApiOperation("恢复")
    @LogOperation("恢复")
    @RequiresPermissions("sched:task:resume")
    public Result<?> resume(@RequestBody List<Long> ids) {
		//效验数据
		AssertUtils.isListEmpty(ids, "id");

        taskService.resume(ids);

        return new Result<>();
    }

}