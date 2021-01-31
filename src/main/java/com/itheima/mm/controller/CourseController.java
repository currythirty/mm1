package com.itheima.mm.controller;


import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.framework.annotation.Controller;
import com.itheima.mm.framework.annotation.RequestMapping;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class CourseController {
    private CourseService courseService=new CourseService();

    //查看和搜索
    @RequestMapping("/course/pageList")
    public void pageList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //接收参数，封装
            QueryPageBean queryPageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            //完成功能，调用Service
            PageResult pageResult = courseService.pageList(queryPageBean);
            //处理结果
            JsonUtils.printResult(response,new Result(true,"分页搜索学科列表成功！",pageResult));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"分页搜索学科列表失败！"));
        }
    }

    //添加
    @RequestMapping("/course/add")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //接收参数
            Course course = JsonUtils.parseJSON2Object(request, Course.class);
            //补全数据，create_date(当前时间)/user_id(当前登录用户)
            User user = (User) request.getSession().getAttribute("user");
            System.out.println(user);
            //设置id
            course.setUserId(user.getId());
            //调用时间格式工具
            course.setCreateDate(DateUtils.parseDate2String(new Date()));
            //完成功能，调用Service
            courseService.add(course);
            JsonUtils.printResult(response,new Result(true,"新增学科成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"新增学科失败"));
        }
    }

    //修改
    @RequestMapping("/course/edit")
    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Course course = JsonUtils.parseJSON2Object(request, Course.class);
            courseService.edit(course);
            JsonUtils.printResult(response,new Result(true,"修改学科成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"修改学科失败"));
        }
    }

    //删除
    @RequestMapping("/course/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");
            courseService.delete(id);
            JsonUtils.printResult(response, new Result(true, "删除学科成功"));
        } catch(RuntimeException e){
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, "删除学科失败"));
        }
    }

    //下拉学科列表
    @RequestMapping("/course/simpleList")
    public void simpleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //调用service，查询所有学科
            List<Course> courseList= courseService.simpleList();
            JsonUtils.printResult(response,new Result(true,"查询学科列表成功",courseList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询学科列表失败"));
        }
    }

    //添加学科栏加载所有学科及其关联的目录列表和标签列表
    @RequestMapping("/course/complexList")
    public void complexList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.完成功能
            List<Course> courseList = courseService.complexList();
            //2.处理结果
            JsonUtils.printResult(response, new Result(true, "加载学科列表成功", courseList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, "加载学科列表失败"));
        }
    }
}
