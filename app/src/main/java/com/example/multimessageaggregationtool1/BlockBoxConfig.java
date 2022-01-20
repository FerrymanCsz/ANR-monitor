package com.example.multimessageaggregationtool1;
/**
 * 定义几个配置参数
 */

public class BlockBoxConfig {

    public long getWarnTime;
    private long warnTime = 300;
    private long anrTime = 3000;

    public long getWarnTime() {
        return warnTime;
    }

    public long getAnrTime() {
        return anrTime;
    }
}
