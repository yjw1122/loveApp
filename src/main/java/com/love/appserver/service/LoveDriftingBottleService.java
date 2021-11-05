package com.love.appserver.service;

import com.love.appserver.dao.AUserinfoDo;

import java.util.Map;

/**
 * @Interface LoveDriftingBottleService
 * @Description 爱情盲盒service
 * @Author yangjingwei
 * @Date 2021/10/14 14:29
 * @Version 1.0
 **/
public interface LoveDriftingBottleService {
    /**
     * 保存用户信息
     *
     * @param request 请求信息
     * @return 响应信息
     */
    Map<String, Object> saveInfo(Map<String, Object> request);

    /**
     * 获取求缘信息
     *
     * @param request 请求信息
     * @return 响应信息
     */
    Map<String, Object> getInfo(Map<String, Object> request);

    /**
     * 支付
     *
     * @param request 请求信息
     * @return 响应信息
     */
    Map<String, Object> pay(Map<String, Object> request);


    AUserinfoDo select_userInfo(int i);

    void insert_userInfo(AUserinfoDo aUserinfoDo);
}
