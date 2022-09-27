package com.lizza.redisson.util;

import cn.hutool.core.util.StrUtil;

import java.time.LocalTime;

public class Log {

    public static void log(String info, Object... args) {
        System.out.println(StrUtil.format("{} " + info, LocalTime.now(), args));
    }
}
