package com.itheima.mm.service;

import com.itheima.mm.constants.Constants;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.ReviewDao;
import com.itheima.mm.pojo.ReviewLog;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReviewService {
    public void reviewQuestion(ReviewLog reviewLog) throws IOException {
        SqlSession sqlSession=null;
        try {
            sqlSession = SqlSessionFactoryUtils.openSqlSession();
            //根据t_question表中的id值修改is_classic/status/review_status的值
            QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
            //将三个参数封装成Map集合
            Map<String,Object> parms=new HashMap<>();
            if(reviewLog.getStatus()== Constants.QUESTION_REVIEWED){
                //审核通过
                parms.put("isClassic",1);
                parms.put("reviewStatus",Constants.QUESTION_REVIEWED);
                parms.put("status",Constants.QUESTION_PUBLISHED);
                parms.put("questionId",reviewLog.getQuestionId());
            }else {
                parms.put("isClassic",0);
                parms.put("reviewStatus",Constants.QUESTION_REJECT_REVIEW);
                parms.put("status",Constants.QUESTION_PRE_PUBLISH);
                parms.put("questionId",reviewLog.getQuestionId());
            }
            //修改t_question中的值
            questionDao.updateReviewStatus(parms);

            //向t_review_log表中插入记录
            ReviewDao reviewDao = sqlSession.getMapper(ReviewDao.class);
            reviewDao.add(reviewLog);

            //提交事物
            SqlSessionFactoryUtils.commitAndClose(sqlSession);
        } catch (Exception e) {
            e.printStackTrace();
            SqlSessionFactoryUtils.rollbackAndClose(sqlSession);
            //异常抛出
             throw  e;
        }
    }
}
