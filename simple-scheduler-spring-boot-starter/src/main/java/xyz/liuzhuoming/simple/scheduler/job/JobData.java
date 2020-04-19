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
     * 绑定外部数据id（务必保持全局唯一）
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
