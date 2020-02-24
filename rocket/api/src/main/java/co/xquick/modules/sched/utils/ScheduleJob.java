package co.xquick.modules.sched.utils;

import co.xquick.booster.constant.Constant;
import co.xquick.booster.util.ExceptionUtils;
import co.xquick.booster.util.SpringContextUtils;
import co.xquick.modules.sched.SchedConst;
import co.xquick.modules.sched.entity.TaskEntity;
import co.xquick.modules.sched.entity.TaskLogEntity;
import co.xquick.modules.sched.service.TaskLogService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 定时任务
 *
 * @author Charles zhangchaoxu@gmail.com
 */
public class ScheduleJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) {
        TaskEntity task = (TaskEntity) context.getMergedJobDataMap().get(SchedConst.JOB_PARAM_KEY);

        // 数据库保存执行记录
        TaskLogEntity log = new TaskLogEntity();
        log.setTaskId(task.getId());
        log.setTaskName(task.getName());
        log.setParams(task.getParam());

        //任务开始时间
        long startTime = System.currentTimeMillis();
        try {
            //执行任务
            logger.info("任务准备执行，任务ID：{}", task.getId());
            Object target = SpringContextUtils.getBean(task.getName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, task.getParam());

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);
            //任务状态
            log.setStatus(Constant.SUCCESS);

            logger.info("任务执行完毕，任务ID：{}  总共耗时：{} 毫秒", task.getId(), times);
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：{}", task.getId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);

            //任务状态
            log.setStatus(Constant.FAIL);
            log.setError(ExceptionUtils.getErrorStackTrace(e));
        } finally {
            // 获取spring bean
            TaskLogService taskLogService = SpringContextUtils.getBean(TaskLogService.class);
            taskLogService.save(log);
        }
    }
}