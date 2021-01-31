package com.itheima.mm.service;

import com.itheima.mm.dao.CompanyDao;
import com.itheima.mm.pojo.Company;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class CompanyService {
    public List<Company> complexList() throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
        List<Company> companyList=companyDao.complexList();
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return companyList;
    }
}
