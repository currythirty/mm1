package com.itheima.mm.dao;

import com.itheima.mm.pojo.Permission;

import java.util.List;

/**
 * @author liuyp
 * @date 2020/10/22
 */
public interface PermissionDao {
    List<Permission> findByRid(Integer roleId);
}
