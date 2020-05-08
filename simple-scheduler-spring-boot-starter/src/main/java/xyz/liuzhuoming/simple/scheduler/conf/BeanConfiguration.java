package xyz.liuzhuoming.simple.scheduler.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import xyz.liuzhuoming.simple.scheduler.job.DefaultJobManager;
import xyz.liuzhuoming.simple.scheduler.job.JobManager;
import xyz.liuzhuoming.simple.scheduler.key.DefaultKeyGenerator;
import xyz.liuzhuoming.simple.scheduler.key.KeyGenerator;
import xyz.liuzhuoming.simple.scheduler.util.SpringUtil;

/**
 * bean自动配置
 *
 * @author liuzhuoming
 */
@Configuration
@Order(1)
public class BeanConfiguration {

    @Bean
    @ConditionalOnMissingBean(KeyGenerator.class)
    @Order(2)
    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(JobManager.class)
    @Order(1)
    public JobManager jobManager() {
        return new DefaultJobManager();
    }

    @Bean
    @ConditionalOnMissingBean(SpringUtil.class)
    @Order(0)
    public SpringUtil springUtil() {
        return new SpringUtil();
    }
}
