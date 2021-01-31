package com.itheima.mm.dao;

import com.itheima.mm.pojo.Industry;

import java.util.List;

/**
 * @author liuyp
 * @date 2020/10/21
 */
public interface IndustryDao {
    List<Industry> simpleList();

    List<Industry> findByCompanyId(Integer companyId);
}
