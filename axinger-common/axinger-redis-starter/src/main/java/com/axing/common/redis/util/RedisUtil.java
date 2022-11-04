package com.axing.common.redis.util;

import cn.hutool.core.util.StrUtil;

public class RedisUtil {

    /**
     * 使用:: 分隔,组成key
     *
     * @param kes
     * @return
     */
    public static String getRedisKey(Object... kes) {
        String join = StrUtil.join("::", kes);
        return join;
    }
}