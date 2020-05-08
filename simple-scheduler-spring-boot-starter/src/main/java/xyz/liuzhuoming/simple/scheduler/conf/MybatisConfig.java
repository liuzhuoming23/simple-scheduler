package xyz.liuzhuoming.simple.scheduler.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * mybatis配置
 *
 * @author liuzhuoming
 */
@MapperScan("xyz.liuzhuoming.simple.scheduler.mapper")
@Configuration
@Order(-1)
public class MybatisConfig {

}
