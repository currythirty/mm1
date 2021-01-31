package com.itheima.mm.service;

import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class CourseService {
    public PageResult pageList(QueryPageBean queryPageBean) throws IOException {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);

        //搜索总数量
        Long total = courseDao.total(queryPageBean);

        //分页查询，查询到的学科列表
        List<Course> courseList=courseDao.pageList(queryPageBean);
        //提交关闭
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        //返回数据
        return new PageResult(total,courseList);
    }

    public void add(Course course) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        courseDao.add(course);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public void edit(Course course) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        courseDao.edit(course);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);

    }

    public void delete(String id) throws IOException {
        SqlSession session = null;
        try {
            session = SqlSessionFactoryUtils.openSqlSession();
            CourseDao courseDao = session.getMapper(CourseDao.class);
            //如果学科下，关联的有标签，不允许删除学科（标签t_tag表中有外键，指向学科表主键）
            Long tagCount = courseDao.tagCount(id);
            if (tagCount > 0) {
                throw new RuntimeException("学科中有关联的标签，不允许删除");
            }
            //如果学科下，关联的有目录，不允许删除学科（标签t_catalog表中有外键，指向学科表主键）
            Long catalogCount = courseDao.catalogCount(id);
            if (catalogCount > 0) {
                throw new RuntimeException("学科中关联的有二级目录，不允许删除");
            }
            //如果学科下，关联的有题目，不允许删除学科（标签t_question表中有外键，指向学科表主键）
            Long questionCount = courseDao.questionCount(id);
            if (questionCount > 0) {
                throw new RuntimeException("学科中有关联的题目，不允许删除");
            }

            //删除学科
            courseDao.delete(id);
        } finally {
            SqlSessionFactoryUtils.commitAndClose(session);
        }
    }

    //查询学科下拉菜单
    public List<Course> simpleList() throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        List<Course> courseList=courseDao.simpleList();
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return courseList;
    }

    //加载所有学科及其关联的目录和标签，只获取其中的id和name属性值
    //1.查询学科关联的目录列表、标签列表
    public List<Course> complexList() throws IOException {
        SqlSession session = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = session.getMapper(CourseDao.class);
        List<Course> courseList = courseDao.complexList();
        SqlSessionFactoryUtils.commitAndClose(session);
        return courseList;
    }
}
