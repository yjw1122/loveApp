package com.love.appserver.service.impl;

import com.love.appserver.dao.AUserinfoDo;
import com.love.appserver.mapper.LoveDriftingBottleMapper;
import com.love.appserver.service.LoveDriftingBottleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoveDriftingBottleServiceImpl
 * @Description 爱情漂流瓶业务实现类
 * @Author yangjingwei
 * @Date 2021/10/14 14:29
 * @Version 1.0
 **/
@Service
public class LoveDriftingBottleServiceImpl implements LoveDriftingBottleService {

    @Resource
    private LoveDriftingBottleMapper loveDriftingBottleMapper;

    @Override
    public Map<String, Object> saveInfo(Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("phoneNo", "18210408839");
        response.put("userName", "爱情杨");
        response.put("code", "0000");
        response.put("message", "寻找姻缘已发布(*￣︶￣)");
        return response;
    }

    @Override
    public Map<String, Object> getInfo(Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("phoneNo", "18210408839");
        response.put("userName", "爱情杨");
        response.put("sex", "男");
        response.put("age", "27");
        response.put("imgPath", "http://www.baidu.com");
        response.put("remarks", "求取姻缘，手机号同步微信");
        response.put("code", "0000");
        return response;
    }

    @Override
    public Map<String, Object> pay(Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "0000");
        response.put("message", "支付成功^_^");
        return response;
    }

    @Override
    public AUserinfoDo select_userInfo(int id){
       return loveDriftingBottleMapper.select_userInfo(id);
    }

    @Override
    public void insert_userInfo(AUserinfoDo aUserinfoDo) {
        loveDriftingBottleMapper.insert_student(aUserinfoDo);
    }


}
