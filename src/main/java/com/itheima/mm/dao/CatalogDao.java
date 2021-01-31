package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;

import java.util.List;

public interface CatalogDao {
    List<Catalog> findByCourseId(Integer courseId);
}
