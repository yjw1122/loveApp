package com.love.blind.box;

import com.alibaba.fastjson.JSON;
import com.love.appserver.dao.AUserinfoDo;
import com.love.appserver.service.LoveDriftingBottleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName DatasourceDemoApplicationTests
 * @Description TODO
 * @Author yangjingwei
 * @Date 2021/10/21 15:04
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DatasourceDemoApplicationTests {

    @Resource
    private LoveDriftingBottleService loveDriftingBottleService;

    @Test
    public void testSelect_userInfo(){
        AUserinfoDo aUserinfoDo= loveDriftingBottleService.select_userInfo(1);
        log.info(JSON.toJSONString(aUserinfoDo));
    }

    @Test
    public void testInsert_userInfo(){
        AUserinfoDo aUserinfoDo=new AUserinfoDo();
        aUserinfoDo.setName("111111111");
        aUserinfoDo.setAge(10);
        aUserinfoDo.setSex("男");
        loveDriftingBottleService.insert_userInfo(aUserinfoDo);
    }

}
