package xyz.liuzhuoming.simple.scheduler.job;

import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import xyz.liuzhuoming.simple.scheduler.scheduler.SchedulerTemplate;
import xyz.liuzhuoming.simple.scheduler.util.SpringUtil;

/**
 * 基础任务
 *
 * @author liuzhuoming
 */
public class BaseJob {

    /**
     * 当前任务参数集合
     *
     * @param jobName 任务名称
     * @return 参数集合
     */
    protected Map<String, Object> currentJobParams(String jobName) {
        return jobManager.getJobParamsByJobName(jobName);
    }

    private final JobManager jobManager = SpringUtil.getBean(JobManager.class);

    private final SchedulerTemplate schedulerTemplate = SpringUtil
        .getBean(SchedulerTemplate.class);

    /**
     * 执行完成后清理无效任务
     *
     * @param clazz               调用方法的实例类型
     * @param jobExecutionContext 任务执行上下文
     */
    public <JOB extends Job> void afterExecute(Class<JOB> clazz,
        JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        schedulerTemplate.deleteByJobName(clazz, jobName);
    }
}
