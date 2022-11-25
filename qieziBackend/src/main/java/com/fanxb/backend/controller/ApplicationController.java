package com.fanxb.backend.controller;

import com.fanxb.backend.entity.ResultObject;
import com.fanxb.backend.entity.dto.ApplicationSignDto;
import com.fanxb.backend.entity.vo.ApplicationSignVo;
import com.fanxb.backend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * @author fanxb
 * @date 2022/2/15 15:04
 */
@RestController
@RequestMapping("/application")
@Validated
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * 注册
     */
    @PostMapping("/sign")
    public ResultObject sign(@RequestBody ApplicationSignDto signDto) {
        return ResultObject.success(applicationService.sign(signDto));
    }

    /**
     * 页面访问
     *
     * @param callBack 回调函数
     * @param key      key
     * @author fanxb
     * date 2022/2/16 15:24
     */
    @GetMapping("/visit")
    @ResponseBody
    public void visit(HttpServletRequest request, HttpServletResponse response, @NotBlank(message = "回调函数不能为空") String callBack
            , @NotBlank(message = "key不能为空") String key, @NotBlank(message = "path不能为空") String path, @RequestParam(defaultValue = "false") boolean notAdd) throws IOException {
        applicationService.visit(request, response, callBack, key, path, notAdd);
    }

    /**
     * 检查key,secret是否存在
     *
     * @author fanxb
     * date 2022/2/16 15:24
     */
    @PostMapping("/check")
    public ResultObject check(@Validated @RequestBody ApplicationSignVo body) {
        return ResultObject.success(applicationService.check(body));
    }

    /**
     * 下载统计数据
     *
     * @return com.fanxb.backend.entity.ResultObject
     * @author fanxb
     * date 2022-11-25 15:26
     */
    @GetMapping("/download")
    public void download(String key, String secret, HttpServletResponse response) throws Exception {
        applicationService.download(key, secret, response);

    }
}
