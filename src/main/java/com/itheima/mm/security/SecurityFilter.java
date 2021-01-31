package com.itheima.mm.security;

import com.itheima.mm.framework.annotation.Controller;
import com.itheima.mm.framework.annotation.RequestMapping;
import com.itheima.mm.framework.utils.ClassScannerUtils;
import com.itheima.mm.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebFilter("/*")
public class SecurityFilter implements Filter {

    private Map<String,String> needAuthorityMap=new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        //扫描com.itheima.mm.controller包中所有的类
        List<Class<?>> classList = ClassScannerUtils.getClasssFromPackage("com.itheima.mm.controller");
        for (Class<?> aClass : classList) {
            //判断是否为Controller类
            if(aClass.isAnnotationPresent(Controller.class)){
                 //扫描类中的方法
                Method[] methods = aClass.getMethods();
                //遍历方法
                for (Method method : methods) {
                    //判断该方法是否实现功能并且有权限控制
                    boolean isMappingMethod = method.isAnnotationPresent(RequestMapping.class);
                    boolean isAuthorizeMethod = method.isAnnotationPresent(PreAuthorize.class);
                        //如果都有
                    if(isAuthorizeMethod&&isMappingMethod){
                         //获取资源的映射路径和需要的权限
                        String mappingPath = method.getAnnotation(RequestMapping.class).value();
                        String needAuthority = method.getAnnotation(PreAuthorize.class).value();
                        //把需要的资源路径和权限存储到needAurhorityMap中
                        needAuthorityMap.put(mappingPath,needAuthority);
                    }
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //先将ServletRequest和ServletResponse强转成HttpServletRequest和HttpServletResponse
        HttpServletResponse response= (HttpServletResponse) resp;
        HttpServletRequest request= (HttpServletRequest) req;
        //获取客户端请求的资源路径，全名为/mm/review/reviewQuestion.do,需要获取的为/review/reviewQuestion
        String requestURI = request.getRequestURI();
        //获取项目路径
        String contextPath = request.getContextPath();
        //requestURI路径中去除项目路径(从项目路径长度索引处剪除)
        String requestPath = requestURI.substring(contextPath.length());
        //判断是否为".do"结尾
        if (requestPath.endsWith(".do")){
            //去除".do"
            requestPath = requestPath.replace(".do", "");
        }
        //目标资源权限注解为空，则不需要权限控制，直接放行
        String needAuthority = needAuthorityMap.get(requestPath);
        if (needAuthority == null) {
            chain.doFilter(request,response);
            return;
        }
        //目标资源需要权限控制，获取当前用户拥有的权限
        User user = (User) request.getSession().getAttribute("user");
        //如果未登录，不允许访问，跳转到登录页面
        if (user == null) {
            System.out.println("未登录不允许访问"+requestPath);
            response.sendRedirect(contextPath+"/login.html");
            return;
        }
        //已经登录,如果用户没有权限，不允许访问
        List<String> userAuthorityList = user.getAuthorityList();
        if (userAuthorityList == null||userAuthorityList.size()==0) {
            System.out.println("未登录不允许访问"+user.getUsername()+"没有访问权限"+requestPath);
            response.getWriter().print("权限不足");
            return;
        }
        //用户拥有一定的权限，是否包含目标资源所需要的权限
        boolean canAccess = userAuthorityList.contains(needAuthority);
        if(canAccess){
                //有权限，放行
                chain.doFilter(request,response);
        }else {
            //没权限，不放行
            System.out.println("权限不足"+user.getUsername()+"没有审核权限"+requestPath);
            response.getWriter().print("权限不足");
        }
    }

    @Override
    public void destroy() {

    }
}
