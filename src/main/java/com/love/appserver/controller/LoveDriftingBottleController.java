package com.love.appserver.controller;

import com.alibaba.fastjson.JSON;
import com.love.appserver.service.LoveDriftingBottleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName LoveDriftingBottleController
 * @Description 爱情漂流瓶项目app总入口
 * @Author yangjingwei
 * @Date 2021/10/14 14:28
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/love/drifting/bottle")
@Slf4j
public class LoveDriftingBottleController {

    @Resource
    private LoveDriftingBottleService loveDriftingBottleService;

    /**
     * 保存求缘信息
     *
     * @param request 请求信息
     * @return 响应信息
     */
    @RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
    public Map<String, Object> saveInfo(@RequestBody Map<String, Object> request) {
        log.info("saveInfo请求信息：{}", JSON.toJSONString(request));
        Map<String, Object> response = loveDriftingBottleService.saveInfo(request);
        log.info("saveInfo响应信息：{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 获取求缘信息
     *
     * @param request 请求信息
     * @return 响应信息
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public Map<String, Object> getInfo(@RequestBody Map<String, Object> request) {
        log.info("getInfo请求信息：{}", JSON.toJSONString(request));
        Map<String, Object> response = loveDriftingBottleService.getInfo(request);
        log.info("getInfo响应信息：{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 支付
     *
     * @param request 请求信息
     * @return 响应信息
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Map<String, Object> pay(@RequestBody Map<String, Object> request) {
        log.info("pay请求信息：{}", JSON.toJSONString(request));
        Map<String, Object> response = loveDriftingBottleService.pay(request);
        log.info("pay响应信息：{}", JSON.toJSONString(response));
        return response;
    }

}
