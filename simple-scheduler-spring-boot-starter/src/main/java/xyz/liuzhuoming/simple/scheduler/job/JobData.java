package xyz.liuzhuoming.simple.scheduler.job;

import java.util.Date;
import java.util.Map;
import lombok.Data;

/**
 * 任务数据
 *
 * @author liuzhuoming
 */
@Data
public class JobData {

    /**
     * 绑定外部数据id
     * <p>
     * 自行实现{@link JobManager}的话可以保证同一个{@link BaseJob}子类实例下唯一,默认实现{@link DefaultJobManager}务必保证全局唯一
     */
    private String relatedId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务参数
     */
    private Map<String, Object> jobParams;
    /**
     * 任务执行时间
     */
    private Date executeTime;
    /**
     * 任务类名称
     */
    private String className;
}
