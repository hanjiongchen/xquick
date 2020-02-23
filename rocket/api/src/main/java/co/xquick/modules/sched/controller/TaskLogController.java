package  co.xquick.modules.sched.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.modules.sched.dto.TaskLogDTO;
import co.xquick.modules.sched.service.TaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Charles zhangchaoxu@gmail.com
 */
@RestController
@RequestMapping("/sched/taskLog")
@Api(tags="定时任务日志")
public class TaskLogController {
	@Autowired
	private TaskLogService scheduleJobLogService;

	@GetMapping("page")
	@ApiOperation("分页")
	@RequiresPermissions("sched:taskLog:page")
	public Result<PageData<TaskLogDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<TaskLogDTO> page = scheduleJobLogService.page(params);
		
		return new Result<PageData<TaskLogDTO>>().ok(page);
	}

	@GetMapping("{id}")
	@ApiOperation("信息")
	@RequiresPermissions("sched:task:info")
	public Result<TaskLogDTO> info(@PathVariable("id") Long id){
		TaskLogDTO log = scheduleJobLogService.get(id);
		
		return new Result<TaskLogDTO>().ok(log);
	}
}