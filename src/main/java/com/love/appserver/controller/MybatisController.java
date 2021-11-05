package com.love.appserver.controller;

import com.alibaba.fastjson.JSON;
import com.love.appserver.dao.AUserinfoDo;
import com.love.appserver.service.LoveDriftingBottleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName MybatisController
 * @Description TODO
 * @Author yangjingwei
 * @Date 2021/10/25 10:12
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/mybatis")
@Slf4j
public class MybatisController {

    @Resource
    private LoveDriftingBottleService loveDriftingBottleService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(@RequestBody Map<String, Object> request) {
        log.info("insert请求信息：{}", JSON.toJSONString(request));
        AUserinfoDo aUserinfoDo = new AUserinfoDo();
        aUserinfoDo.setName((String) request.get("name"));
        aUserinfoDo.setAge(Integer.parseInt((String) request.get("age")));
        aUserinfoDo.setSex((String) request.get("sex"));
        loveDriftingBottleService.insert_userInfo(aUserinfoDo);
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public AUserinfoDo select(@RequestBody Map<String, Object> request) {
        log.info("select请求信息：{}", JSON.toJSONString(request));
        AUserinfoDo aUserinfoDo = loveDriftingBottleService.select_userInfo(Integer.parseInt((String) request.get("id")));
        return aUserinfoDo;
    }
}
