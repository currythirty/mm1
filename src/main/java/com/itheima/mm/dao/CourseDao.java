package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

import java.util.List;

public interface CourseDao {
    //查询学科总数
    Long total(QueryPageBean queryPageBean);
    List<Course> pageList(QueryPageBean queryPageBean);

    //添加
    void add(Course course);

    //修改
    void edit(Course course);

    //删除
    void delete(String id);
    //查询是否有关联外键，有就不能删除
    Long tagCount(String id);
    Long catalogCount(String id);
    Long questionCount(String id);

    //查询学科下拉菜单
    List<Course> simpleList();

    //新增试题下拉学科菜单关联目录和标签
    List<Course> complexList();

}
