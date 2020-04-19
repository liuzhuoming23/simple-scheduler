package xyz.liuzhuoming.simple.scheduler.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import xyz.liuzhuoming.simple.scheduler.job.JobManager;
import xyz.liuzhuoming.simple.scheduler.prop.SchedulerProperties;
import xyz.liuzhuoming.simple.scheduler.scheduler.KeyGenerator;
import xyz.liuzhuoming.simple.scheduler.scheduler.SchedulerTemplate;
import xyz.liuzhuoming.simple.scheduler.scheduler.SchedulerTemplateImpl;

/**
 * 调度自动配置
 *
 * @author liuzhuoming
 */
@Configuration
@EnableConfigurationProperties(SchedulerProperties.class)
@RequiredArgsConstructor
@Order(2)
public class SchedulerAutoConfiguration {

    private final SchedulerProperties schedulerProperties;
    private final JobManager jobManager;
    private final KeyGenerator keyGenerator;

    @Bean
    @ConditionalOnMissingBean(SchedulerTemplate.class)
    @Order(0)
    public SchedulerTemplate schedulerTemplate() {
        SchedulerTemplateImpl schedulerTemplate = new SchedulerTemplateImpl();
        schedulerTemplate.setSchedulerProperties(schedulerProperties);
        schedulerTemplate.setJobManager(jobManager);
        schedulerTemplate.setKeyGenerator(keyGenerator);
        return schedulerTemplate;
    }
}
