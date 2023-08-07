package com.pymybatis.test;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        framework_test();
    }

    /**
     * 基础Mybatis测试
     */
    private static void base_test() throws IOException {
//        String resource = "demo1/mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        HashMap<String, Object> param = new HashMap<String, Object>();
//        param.put("id", 1);
//        Object o = sqlSession.selectOne("UserMapper.selectById", param);
//
//        if (o != null) {
//            HashMap res = (HashMap) o;
//            System.out.println(res);
//        } else {
//            System.out.println(o);
//        }
    }

    private static void framework_test() throws IOException {
//        Mybatis mybatis = new Mybatis();
//
//        SqlSessionFactory sqlSessionFactory = mybatis.getSqlSessionFactory("demo1/mybatis-config.xml");
//        System.out.println(sqlSessionFactory);
//
//        SqlSession sqlSession = mybatis.getSqlSession(sqlSessionFactory);
//        System.out.println(sqlSession);
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("id", 1);
//
//        String one = mybatis.selectOne(sqlSession, "UserMapper.selectById", JSON.toJSONString(param));
//        System.out.println(one);
//
//        String list = mybatis.selectList(sqlSession, "UserMapper.selectList", JSON.toJSONString(param));
//        System.out.println(list);
//
//        String value = mybatis.selectValue(sqlSession, "UserMapper.selectId", JSON.toJSONString(param));
//        System.out.println(value);
//
//        String page = mybatis.selectPage(sqlSession, "UserMapper.selectPage_count", "UserMapper.selectPage_list", 2, 10, null);
//        System.out.println(page);
//
//        param = new HashMap<>();
//        param.put("username", "qi long zu2 2");
//        String insertUser = mybatis.insert(sqlSession, "insertUser", JSON.toJSONString(param), true, "id");
//        System.out.println(insertUser);
//        String page = mybatis.selectPageByPageHelper(sqlSession, "UserMapper.selectList", 2, 10, null);
//        System.out.println(page);
    }
}
