package xyz.liuzhuoming.scheduler.sample.config;

import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import xyz.liuzhuoming.simple.scheduler.scheduler.SchedulerTemplate;

/**
 * 任务初始化
 *
 * @author liuzhuoming
 */
@Configuration
@AllArgsConstructor
public class SchedulerConfig {

    private final SchedulerTemplate quartzTemplate;

    @PostConstruct
    public void start() {
        quartzTemplate.start();
        quartzTemplate.restore();
    }
}
