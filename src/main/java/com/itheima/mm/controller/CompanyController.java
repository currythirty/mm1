package com.itheima.mm.controller;


import com.itheima.mm.entity.Result;
import com.itheima.mm.framework.annotation.Controller;
import com.itheima.mm.framework.annotation.RequestMapping;
import com.itheima.mm.pojo.Company;
import com.itheima.mm.pojo.Industry;
import com.itheima.mm.service.CompanyService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Controller
public class CompanyController {

    private CompanyService companyService=new CompanyService();
    @RequestMapping("/company/complexList")
    public void complexList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("helloWorld");
            System.out.println("helloWorld2");
            System.out.println("helloworld3");
            System.out.println("helloworld4");
            //1.完成功能：查询所有企业
            List<Company> companyList = companyService.complexList();
            //2.处理结果
            JsonUtils.printResult(response, new Result(true, "加载企业列表成功", companyList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, "加载企业列表失败"));
        }
    }

}
