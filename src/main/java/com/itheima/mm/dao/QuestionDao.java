package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;

import java.util.List;
import java.util.Map;

public interface QuestionDao {
    Long total(QueryPageBean queryPageBean);

    List<Question> pageList(QueryPageBean queryPageBean);

    void add(Question question);

    void updateReviewStatus(Map<String, Object> parms);
}
