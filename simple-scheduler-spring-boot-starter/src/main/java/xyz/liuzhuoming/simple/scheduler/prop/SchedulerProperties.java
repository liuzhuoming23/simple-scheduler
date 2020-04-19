package xyz.liuzhuoming.simple.scheduler.prop;

import lombok.Data;
import org.quartz.SchedulerException;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuzhuoming
 */
@Data
@ConfigurationProperties(prefix = "scheduler")
public class SchedulerProperties {

    private final static int DEFAULT_SCHEDULER_MAX_SIZE = 64;

    /**
     * 调度器最大容量，超过之后新增任务抛出异常{@link SchedulerException}
     *
     * @see SchedulerException
     */
    private int schedulerMaxSize = DEFAULT_SCHEDULER_MAX_SIZE;
}
