package com.itheima.mm.controller;


import com.itheima.mm.entity.Result;
import com.itheima.mm.framework.annotation.Controller;
import com.itheima.mm.framework.annotation.RequestMapping;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController{

    private UserService userService=new UserService();
    @RequestMapping("/user/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User loginUser = JsonUtils.parseJSON2Object(request, User.class);
            System.out.println(loginUser);
            User user=userService.login(loginUser);
            if(user!=null){
                request.getSession().setAttribute("user",user);
                JsonUtils.printResult(response,new Result(true,"登录成功"));
            }else {
                JsonUtils.printResult(response,new Result(false,"用户名或密码错误"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"登陆失败"));
        }
    }

    @RequestMapping("/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //销毁对象
            System.out.println("第一次提交！");
            System.out.println("第二次提交！");
            System.out.println("第三次提交！");
            //测试
            request.getSession().invalidate();
            JsonUtils.printResult(response,new Result(true,"退出成功！"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"退出成功！"));
        }
    }
}
