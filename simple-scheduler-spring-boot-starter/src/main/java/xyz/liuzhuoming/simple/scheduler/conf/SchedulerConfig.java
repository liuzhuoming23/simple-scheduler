package xyz.liuzhuoming.simple.scheduler.conf;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import xyz.liuzhuoming.simple.scheduler.scheduler.SchedulerTemplate;

/**
 * 调度初始化
 *
 * @author liuzhuoming
 */
@Configuration
@AllArgsConstructor
@Order(3)
public class SchedulerConfig implements CommandLineRunner {

    private final SchedulerTemplate quartzTemplate;

    @Override
    public void run(String... args) throws Exception {
        quartzTemplate.start();
        quartzTemplate.restore();
    }
}
