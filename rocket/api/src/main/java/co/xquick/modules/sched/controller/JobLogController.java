package  co.xquick.modules.sched.controller;

import co.xquick.booster.pojo.PageData;
import co.xquick.booster.pojo.Result;
import co.xquick.modules.sched.dto.JobLogDTO;
import co.xquick.modules.sched.service.JobLogService;
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
@RequestMapping("/sched/jobLog")
@Api(tags="定时任务日志")
public class JobLogController {
	@Autowired
	private JobLogService scheduleJobLogService;

	@GetMapping("page")
	@ApiOperation("分页")
	@RequiresPermissions("sys:schedule:log")
	public Result<PageData<JobLogDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<JobLogDTO> page = scheduleJobLogService.page(params);
		
		return new Result<PageData<JobLogDTO>>().ok(page);
	}

	@GetMapping("{id}")
	@ApiOperation("信息")
	@RequiresPermissions("sys:schedule:log")
	public Result<JobLogDTO> info(@PathVariable("id") Long id){
		JobLogDTO log = scheduleJobLogService.get(id);
		
		return new Result<JobLogDTO>().ok(log);
	}
}