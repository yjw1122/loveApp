package com.love.appserver.mapper;

import com.love.appserver.dao.AUserinfoDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Interface LoveDriftingBottleMapper
 * @Description TODO
 * @Author yangjingwei
 * @Date 2021/10/21 15:05
 * @Version 1.0
 **/
@Mapper
public interface LoveDriftingBottleMapper {

    /**
     * 查询单个
     * @param i
     * @return
     */
    AUserinfoDo select_userInfo(int i);
    /**
     * 查询全部
     */
    List<AUserinfoDo> select_all_userInfo();

    /**
     * 添加
     */
    void insert_student(AUserinfoDo aUserinfoDo);
    /**
     * 删除
     */
    void delete_student(String str);
    /**
     * 修改
     */
    void update_student(AUserinfoDo aUserinfoDo);


}
