package com.itheima.mm.controller;


import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.framework.annotation.Controller;
import com.itheima.mm.framework.annotation.RequestMapping;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.utils.JedisUtils;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class QuestionController {
    private QuestionService questionService=new QuestionService();

    @RequestMapping("/question/pageList")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            QueryPageBean queryPageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            PageResult pageResult=questionService.pageList(queryPageBean);
            System.out.println(queryPageBean.getPageSize());
            System.out.println(queryPageBean.getCurrentPage());
            System.out.println(queryPageBean.getQueryParams());
            System.out.println(pageResult);
            JsonUtils.printResult(response,new Result(true,"查询基础题目列表成功",pageResult));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询基础题目列表失败"));
        }
    }

    @RequestMapping("/question/save")
    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            //接收参数
            Question question = JsonUtils.parseJSON2Object(request, Question.class);
            //补全数据
            User user = (User) request.getSession().getAttribute("user");
            question.setUserId(user.getId());
            //完成功能
            questionService.save(question);
            JsonUtils.printResult(response,new Result(true,"添加题目成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"添加题目失败"));
        }
    }
}
