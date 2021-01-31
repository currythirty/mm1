package com.itheima.mm.service;

import com.itheima.mm.constants.Constants;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.QuestionItemDao;
import com.itheima.mm.dao.TagDao;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.QuestionItem;
import com.itheima.mm.pojo.Tag;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class QuestionService {
    //题目列表
    public PageResult pageList(QueryPageBean queryPageBean) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        Long total=questionDao.total(queryPageBean);
        List<Question>questionList = questionDao.pageList(queryPageBean);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return new PageResult(total,questionList);
    }

    //添加题目
    public void save(Question question) throws IOException {
        //判断是新增还是修改
        if(question.getId()==0){
            SqlSession sqlSession=null;
            try {
                sqlSession = SqlSessionFactoryUtils.openSqlSession();
                QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
                //补全数据
                question.setReviewStatus(Constants.QUESTION_PRE_REVIEW);//待审核
                question.setStatus(Constants.QUESTION_PRE_PUBLISH);//待发布
                question.setCreateDate(DateUtils.parseDate2String(new Date()));//发布时间
                //添加题目
                questionDao.add(question);
                List<QuestionItem> itemList = question.getQuestionItemList();
                //判断选项是否为空
                if (itemList != null&&itemList.size()>0) {
                    //创建接口，将题目选项数据保存到数据库
                    QuestionItemDao questionItemsDao= sqlSession.getMapper(QuestionItemDao.class);
                    for (QuestionItem questionItem : itemList) {
                        String content = question.getContent();
                        //判断题目内容是否为空
                        if(content!=null&&!"".equals(content)){
                            //设置题目的id,从question对象中获得
                            questionItem.setQuestionId(question.getId());
                            questionItemsDao.add(questionItem);
                        }
                    }
                }
                //保存标签数据，插入题目和标签之间的关系，向tr_qusetion_tag表中插入数据
                List<Tag> tagList = question.getTagList();
                if (tagList != null&&tagList.size()>0) {
                    TagDao tagDao = sqlSession.getMapper(TagDao.class);
                    for (Tag tag : tagList) {
                        tagDao.addQuestionAndTagRef(question.getId(),tag.getId());
                    }
                }
                //提交事物2
                SqlSessionFactoryUtils.commitAndClose(sqlSession);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }else {
            // TODO: 2020/10/22
        }
    }
}

