package co.xquick.modules.sched.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.booster.validator.ValidatorUtils;
import co.xquick.booster.validator.group.AddGroup;
import co.xquick.booster.validator.group.DefaultGroup;
import co.xquick.booster.validator.group.UpdateGroup;
import co.xquick.common.annotation.LogOperation;
import co.xquick.modules.sched.dto.TaskDTO;
import co.xquick.modules.sched.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("/sched/task")
@Api(tags="定时任务")
public class TaskController {
	@Autowired
	private TaskService taskService;

	@GetMapping("page")
	@ApiOperation("分页")
	@RequiresPermissions("sched:task:page")
	public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<TaskDTO> page = taskService.page(params);

		return new Result<>().ok(page);
	}

	@GetMapping("info")
	@ApiOperation("信息")
	@RequiresPermissions("sched:task:info")
	public Result<?> info(@RequestParam Long id){
		TaskDTO schedule = taskService.get(id);
		
		return new Result<>().ok(schedule);
	}

	@PostMapping("save")
	@ApiOperation("保存")
	@LogOperation("保存")
	@RequiresPermissions("sched:task:save")
	public Result<?> save(@RequestBody TaskDTO dto){
		ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

		taskService.save(dto);
		
		return new Result<>();
	}

	@PutMapping("update")
	@ApiOperation("修改")
	@LogOperation("修改")
	@RequiresPermissions("sched:task:update")
	public Result<?> update(@RequestBody TaskDTO dto){
		ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

		taskService.update(dto);
		
		return new Result<>();
	}

	@DeleteMapping("delete")
	@ApiOperation("删除")
	@LogOperation("删除")
	@RequiresPermissions("sched:task:delete")
	public Result<?> delete(@RequestBody Long[] ids){
		taskService.deleteBatch(ids);
		
		return new Result<>();
	}

	@PutMapping("/run")
	@ApiOperation("立即执行")
	@LogOperation("立即执行")
	@RequiresPermissions("sched:task:run")
	public Result<?> run(@RequestBody Long[] ids){
		taskService.run(ids);
		
		return new Result<>();
	}

	@PutMapping("/pause")
	@ApiOperation("暂停")
	@LogOperation("暂停")
	@RequiresPermissions("sched:task:pause")
	public Result<?> pause(@RequestBody Long[] ids){
		taskService.pause(ids);
		
		return new Result<>();
	}

	@PutMapping("/resume")
	@ApiOperation("恢复")
	@LogOperation("恢复")
	@RequiresPermissions("sched:task:resume")
	public Result<?> resume(@RequestBody Long[] ids){
		taskService.resume(ids);
		
		return new Result<>();
	}

}