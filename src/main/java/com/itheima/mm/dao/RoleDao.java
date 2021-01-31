package com.itheima.mm.dao;

import com.itheima.mm.pojo.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findByUid(Integer userId);
}
