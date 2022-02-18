package com.fanxb.backend.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;

import javax.servlet.http.HttpServletRequest;

/**
 * 网络相关工具类
 *
 * @author fanxb
 * @date 2022/2/16 16:04
 */
public class NetUtil {
    private static final String[] HEADERS = new String[]{"x-forwarded-for", "X-Real-IP"};

    /**
     * 获取客户端真实ip
     *
     * @param request request
     * @return {@link String}
     * @author fanxb
     * date 2022/2/16 16:10
     */
    public static String getClientIp(HttpServletRequest request) {
        for (String header : HEADERS) {
            String ip = request.getHeader(header);
            if (StrUtil.isNotEmpty(ip)) {
                return ip.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }
}
