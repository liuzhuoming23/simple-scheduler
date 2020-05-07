package xyz.liuzhuoming.simple.scheduler.key;

import java.util.UUID;

/**
 * 默认key生成器
 *
 * @author liuzhuoming
 */
public class DefaultKeyGenerator implements KeyGenerator {

    @Override
    public String nextKey() {
        return UUID.randomUUID().toString();
    }
}
