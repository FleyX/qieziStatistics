package com.fanxb.backend.service;

import com.fanxb.backend.entity.dto.ApplicationSignDto;
import com.fanxb.backend.entity.vo.ApplicationSignVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 应用管理
 *
 * @author fanxb
 * @date 2022/2/15 16:25
 */
public interface ApplicationService {

    /**
     * 应用注册
     *
     * @param signDto dto
     * @return {@link ApplicationSignVo}
     * @author fanxb
     * date 2022/2/15 16:32
     */
    ApplicationSignVo sign(ApplicationSignDto signDto);

    /**
     * 页面访问
     *
     * @param request  request
     * @param response response
     * @param callBack callBack
     * @param key      key
     * @param path     path
     * @param notAdd   只返回，不计数
     * @author fanxb
     * date 2022/2/16 10:20
     */
    void visit(HttpServletRequest request, HttpServletResponse response, String callBack, String key, String path, boolean notAdd) throws IOException;

    /**
     * 检查key,secret是否存在
     *
     * @param body body
     * @return boolean
     * @author fanxb
     * date 2022-11-24 23:05
     */
    boolean check(ApplicationSignVo body);

    /**
     * 下载站点数据
     *
     * @param key      key
     * @param secret   secret
     * @param response response
     * @author fanxb
     * date 2022-11-25 15:27
     */
    void download(String key, String secret, HttpServletResponse response) throws Exception;
}
