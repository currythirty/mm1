package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;
import com.itheima.mm.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagDao {

    List<Tag> findByCourseId(Integer courseId);

    void addQuestionAndTagRef(@Param("questionId") Integer questionId, @Param("tagId") Integer tagId);
}
