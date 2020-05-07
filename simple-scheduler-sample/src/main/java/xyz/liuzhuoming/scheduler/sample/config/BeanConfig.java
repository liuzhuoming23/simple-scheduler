package xyz.liuzhuoming.scheduler.sample.config;

import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import xyz.liuzhuoming.simple.scheduler.job.DefaultJobManager;
import xyz.liuzhuoming.simple.scheduler.job.JdbcJobManager;
import xyz.liuzhuoming.simple.scheduler.job.JobManager;
import xyz.liuzhuoming.simple.scheduler.key.DefaultKeyGenerator;
import xyz.liuzhuoming.simple.scheduler.key.KeyGenerator;
import xyz.liuzhuoming.simple.scheduler.mapper.JobDataMapper;
import xyz.liuzhuoming.simple.scheduler.scheduler.SchedulerTemplate;

/**
 * bean初始化
 *
 * @author liuzhuoming
 */
@Configuration
@AllArgsConstructor
public class BeanConfig {

    private final JobDataMapper jobDataMapper;

    /**
     * 选择jdbc任务管理器
     */
    @Bean
    @ConditionalOnMissingBean(JobManager.class)
    @Order(1)
    public JobManager jobManager() {
        return new JdbcJobManager(jobDataMapper);
    }
}
