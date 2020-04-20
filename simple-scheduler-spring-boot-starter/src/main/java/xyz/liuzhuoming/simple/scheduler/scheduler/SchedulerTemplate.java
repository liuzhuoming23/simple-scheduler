package xyz.liuzhuoming.simple.scheduler.scheduler;

import java.util.Date;
import java.util.Map;
import org.quartz.Job;
import org.quartz.Scheduler;

/**
 * quartz
 *
 * @author liuzhuoming
 */
public interface SchedulerTemplate {

    /**
     * 生成key
     *
     * @return job key
     */
    String generateKey();

    /**
     * 获取Scheduler 实例
     */
    Scheduler getScheduler();

    /**
     * 启动Scheduler
     */
    void start();

    /**
     * 系统重启之后恢复持久化任务
     */
    void restore();

    /**
     * 添加执行任务
     *
     * @param clazz       任务class
     * @param relatedId   外部数据id
     * @param params      参数集合
     * @param executeTime 执行时间
     */
    <JOB extends Job> void addJob(Class<JOB> clazz, String relatedId, Map<String, Object> params,
        Date executeTime);

    /**
     * 删除任务
     *
     * @param clazz     任务class
     * @param relatedId 外部数据id
     */
    <JOB extends Job> void deleteJobByRelatedId(Class<JOB> clazz, String relatedId);

    /**
     * 删除任务
     *
     * @param clazz   任务class
     * @param jobName 任务名称
     */
    <JOB extends Job> void deleteJobByJobName(Class<JOB> clazz, String jobName);
}
