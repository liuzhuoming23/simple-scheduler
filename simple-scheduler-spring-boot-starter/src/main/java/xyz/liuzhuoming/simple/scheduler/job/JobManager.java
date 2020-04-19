package xyz.liuzhuoming.simple.scheduler.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务管理器
 *
 * @author liuzhuoming
 */
public interface JobManager {

    /**
     * 系统重启之后获取未执行的持久化任务
     */
    List<JobData> getUnExecutedJobs();

    /**
     * 新增任务数据
     *
     * @param jobData 任务数据
     */
    void addJob(JobData jobData);

    /**
     * 使用本id的任务是否已存在
     *
     * @param className 任务类名称
     * @param relatedId 外部数据id
     * @return true存在 false不存在
     */
    boolean isExist(String className, String relatedId);

    /**
     * 根据任务名称删除任务数据
     *
     * @param jobName 任务名称
     */
    void deleteJobByJobName(String jobName);

    /**
     * 根据id获取任务名称
     *
     * @param className 任务类名称
     * @param relatedId 外部数据id
     * @return 任务名称
     */
    String getJobNameByRelatedId(String className, String relatedId);

    /**
     * 根据任务名称获取任务参数
     *
     * @param jobName 任务名称
     * @return 任务参数
     */
    Map<String, Object> getJobParamsByJobName(String jobName);

    /**
     * 根据任务名称获取执行时间
     *
     * @param jobName 任务名称
     * @return 执行时间
     */
    Date getExecuteTime(String jobName);

    /**
     * 获取任务总数
     *
     * @return 任务总数
     */
    int getJobSum();
}
