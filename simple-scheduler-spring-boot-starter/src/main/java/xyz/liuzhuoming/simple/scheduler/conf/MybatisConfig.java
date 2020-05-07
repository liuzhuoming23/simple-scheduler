package xyz.liuzhuoming.simple.scheduler.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置
 *
 * @author liuzhuoming
 */
@MapperScan("xyz.liuzhuoming.simple.scheduler.mapper")
@Configuration
public class MybatisConfig {

}
