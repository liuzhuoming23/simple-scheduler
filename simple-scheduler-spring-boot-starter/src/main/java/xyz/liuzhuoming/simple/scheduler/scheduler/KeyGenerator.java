package xyz.liuzhuoming.simple.scheduler.scheduler;

/**
 * 默认job key生成器
 *
 * @author liuzhuoming
 */
public interface KeyGenerator {

    /**
     * 生成key
     *
     * @return key
     */
    String generate();
}
