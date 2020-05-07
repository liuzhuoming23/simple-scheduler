package xyz.liuzhuoming.simple.scheduler.key;

/**
 * 默认job key生成器
 *
 * @author liuzhuoming
 */
public interface KeyGenerator {

    /**
     * 生成下一个key
     *
     * @return key
     */
    String nextKey();
}
