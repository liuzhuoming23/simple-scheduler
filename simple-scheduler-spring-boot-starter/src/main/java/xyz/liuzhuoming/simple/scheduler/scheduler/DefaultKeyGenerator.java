package xyz.liuzhuoming.simple.scheduler.scheduler;

import java.util.UUID;

/**
 * 默认key生成器
 *
 * @author liuzhuoming
 */
public class DefaultKeyGenerator implements KeyGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
