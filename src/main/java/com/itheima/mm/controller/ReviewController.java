package com.itheima.mm.controller;


import com.itheima.mm.entity.Result;
import com.itheima.mm.framework.annotation.Controller;
import com.itheima.mm.framework.annotation.RequestMapping;
import com.itheima.mm.pojo.ReviewLog;
import com.itheima.mm.pojo.User;
import com.itheima.mm.security.PreAuthorize;
import com.itheima.mm.service.ReviewService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class ReviewController {
    private ReviewService reviewService=new ReviewService();
    @PreAuthorize("QUESTION_REVIEW_UPDATE")//资源的关键字
    @RequestMapping("/review/reviewQuestion")//资源的映射路径
    public void reviewQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //接收参数
            ReviewLog reviewLog = JsonUtils.parseJSON2Object(request, ReviewLog.class);

            //补全数据
            reviewLog.setCreateDate(DateUtils.parseDate2String(new Date()));//审核时间
            User user = (User) request.getSession().getAttribute("user");
            reviewLog.setUserId(user.getId());//审核人

            //完成功能
            reviewService.reviewQuestion(reviewLog);
            JsonUtils.printResult(response,new Result(true,"审核完成"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"审核失败"));
        }
    }
}
