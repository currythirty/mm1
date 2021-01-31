package com.itheima.mm.service;

import com.itheima.mm.dao.DictDao;
import com.itheima.mm.pojo.Dict;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class DictService {
    public List<Dict> complexList() throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DictDao dictDao = sqlSession.getMapper(DictDao.class);
        List<Dict> dictList=dictDao.complexList();
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return dictList;
    }
}
