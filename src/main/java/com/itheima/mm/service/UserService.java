package com.itheima.mm.service;

import com.itheima.mm.dao.PermissionDao;
import com.itheima.mm.dao.RoleDao;
import com.itheima.mm.dao.UserDao;
import com.itheima.mm.pojo.Permission;
import com.itheima.mm.pojo.Role;
import com.itheima.mm.pojo.User;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public User login(User loginUser) throws IOException {
        //使用工具类获取session对象
        SqlSession session = SqlSessionFactoryUtils.openSqlSession();
        //获取映射器代理
        UserDao userDao = session.getMapper(UserDao.class);
        //操作数据库，执行语句
        User user = userDao.login(loginUser);
        if (user != null) {
            //创建用于存储用户权限的集合
            List<String> userAuthority=new ArrayList<>();
            //加载用户当前拥有的角色
            PermissionDao permissionDao = session.getMapper(PermissionDao.class);
            RoleDao roleDao = session.getMapper(RoleDao.class);
            List<Role> roleList=roleDao.findByUid(user.getId());
            if (roleList != null&&roleList.size()>0) {
                for (Role role : roleList) {
                   userAuthority.add(role.getKeyword());
                   //加载每个角色可以操作的功能
                    List<Permission> permissionList = permissionDao.findByRid(role.getId());
                    if (permissionList != null&permissionList.size()>0) {
                        for (Permission permission : permissionList) {
                            userAuthority.add(permission.getKeyword());
                        }
                    }
                }
            }
          //把加载到的角色和功能全部保存到user对象的authorityList中
          user.setAuthorityList(userAuthority);
        }
        //提交关闭
        SqlSessionFactoryUtils.commitAndClose(session);
        return user;
    }
}
