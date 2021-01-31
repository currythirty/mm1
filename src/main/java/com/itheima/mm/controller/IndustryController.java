package com.itheima.mm.controller;

import com.itheima.mm.entity.Result;
import com.itheima.mm.framework.annotation.Controller;
import com.itheima.mm.framework.annotation.RequestMapping;
import com.itheima.mm.pojo.Industry;
import com.itheima.mm.service.IndustryService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author liuyp
 * @date 2020/10/21
 */
@Controller
public class IndustryController {
    private IndustryService industryService = new IndustryService();

    @RequestMapping("/industry/simpleList")
    public void simpleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.完成功能：查询所有行业方向的id、name
            List<Industry> industryList = industryService.simpleList();
            //2.处理结果
            JsonUtils.printResult(response, new Result(true, "加载行业方向列表成功", industryList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, "加载行业方向列表失败"));
        }

    }
}

