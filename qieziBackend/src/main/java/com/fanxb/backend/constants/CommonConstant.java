package com.fanxb.backend.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author fanxb
 * @date 2022/2/15 16:41
 */
@Component
public class CommonConstant {

    /**
     * 是否开发环境
     */
    public static boolean IS_DEV = false;

    @Value("${spring.profiles.active}")
    public void setIsDev(String env) {
        IS_DEV = env.contains("dev");
    }
}
