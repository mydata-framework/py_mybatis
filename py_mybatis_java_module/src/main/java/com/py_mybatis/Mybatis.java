package com.py_mybatis;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py_mybatis.vo.PageVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 如果配合python
 * 1 给出配置, 返回 sqlSessionFactory
 * 2 给出 sqlSessionFactory, 返回 sqlSession
 * 3 给出 sqlSession, statementId, 参数, 返回Map 或 List<Map> 或 Map<pageNum, pageSize, count, listist>
 */
public class Mybatis {

    /**
     * 测试方法
     */
    public String ping() {
        return "pong";
    }

    /**
     * 测试方法
     */
    public String hello() {
        return "world";
    }

    /**
     * 获取32位UUID
     */
    public static String getUuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) throws IOException {
        framework_test();
    }

    private SqlSessionFactory sqlSessionFactory = null;


    /**
     * 基础Mybatis测试
     */
    private static void base_test() throws IOException {
        String resource = "demo1/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("id", 1);
        Object o = sqlSession.selectOne("UserMapper.selectById", param);

        if (o != null) {
            HashMap res = (HashMap) o;
            System.out.println(res);
        } else {
            System.out.println(o);
        }
    }

    private static void framework_test() throws IOException {
        Mybatis mybatis = new Mybatis();

        SqlSessionFactory sqlSessionFactory = mybatis.getSqlSessionFactory("demo1/mybatis-config.xml");
        System.out.println(sqlSessionFactory);

        SqlSession sqlSession = mybatis.getSqlSession(sqlSessionFactory);
        System.out.println(sqlSession);

        HashMap<String, Object> param = new HashMap<>();
        param.put("id", 1);

        String one = mybatis.selectOne(sqlSession, "UserMapper.selectById", JSON.toJSONString(param));
        System.out.println(one);
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
//        String s = mybatis.selectPage(sqlSession, "UserMapper.selectList", 2, 10, null);

    }


    /**
     * 获取 SqlSessionFactory
     */
    public SqlSessionFactory getSqlSessionFactory(String resource) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * 获取并设置 SqlSessionFactory
     */
    public SqlSessionFactory config(String resource) throws IOException {
        InputStream inputStream = null;
        if (resource.startsWith("/")) {
            inputStream = new FileInputStream(new File(resource));
        } else {
            inputStream = Resources.getResourceAsStream(resource);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.sqlSessionFactory = sqlSessionFactory;
        return sqlSessionFactory;
    }

    /**
     * 获取 SqlSession
     */
    public SqlSession getSqlSession(SqlSessionFactory sqlSessionFactory) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    /**
     * 获取 SqlSession
     */
    public SqlSession getSqlSession() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        return sqlSession;
    }

    /**
     * selectOne
     */
    public String selectOne(SqlSession sqlSession, String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        Object o = null;
        try {
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (o == null) {
            return "";
        } else {
            return JSON.toJSONString(o);
        }
    }

    /**
     * selectOne
     */
    public String selectOne(String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        Object o = null;
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (o == null) {
            return "";
        } else {
            return JSON.toJSONString(o);
        }
    }

    /**
     * selectList
     */
    public String selectList(SqlSession sqlSession, String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        List list = null;
        try {
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(statementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (list == null) {
            return "";
        } else {
            return JSON.toJSONString(list);
        }
    }

    /**
     * selectList
     */
    public String selectList(String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        List list = null;
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(statementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (list == null) {
            return "";
        } else {
            return JSON.toJSONString(list);
        }
    }

    /**
     * selectValue
     */
    public String selectValue(SqlSession sqlSession, String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        Object o = null;
        try {
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (o == null) {
            return "";
        } else {
            HashMap<String, Object> resMap = new HashMap<>(1);
            resMap.put("key", o);
            return JSON.toJSONString(resMap);
        }
    }

    /**
     * selectValue
     */
    public String selectValue(String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        Object o = null;
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (o == null) {
            return "";
        } else {
            HashMap<String, Object> resMap = new HashMap<>(1);
            resMap.put("key", o);
            return JSON.toJSONString(resMap);
        }
    }

    /**
     * insert
     */
    public String insert(SqlSession sqlSession, String statementId, String paramJson, boolean commit, String keyProperty) {
        System.out.println("param=>" + paramJson);
        int insert = 0;
        HashMap param = null;
        try {
            if (paramJson == null || "".equals(paramJson)) {
                insert = sqlSession.insert(statementId);
            } else {
                param = JSON.parseObject(paramJson, HashMap.class);
                insert = sqlSession.insert(statementId, param);
                if (commit) {
                    sqlSession.commit();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        HashMap<String, Object> resMap = new HashMap<>(1);
        resMap.put("insert", insert);
        if (param != null && keyProperty != null && !keyProperty.equals("")) {
            resMap.put(keyProperty, param.get(keyProperty));
        }
        return JSON.toJSONString(resMap);
    }

    /**
     * insert
     */
    public String insert(String statementId, String paramJson, boolean commit, String keyProperty) {
        System.out.println("param=>" + paramJson);
        int insert = 0;
        HashMap param = null;
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            if (paramJson == null || "".equals(paramJson)) {
                insert = sqlSession.insert(statementId);
            } else {
                param = JSON.parseObject(paramJson, HashMap.class);
                insert = sqlSession.insert(statementId, param);
                if (commit) {
                    sqlSession.commit();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        HashMap<String, Object> resMap = new HashMap<>(1);
        resMap.put("insert", insert);
        if (param != null && keyProperty != null && !keyProperty.equals("")) {
            resMap.put(keyProperty, param.get(keyProperty));
        }
        return JSON.toJSONString(resMap);
    }

    /**
     * update
     */
    public String update(SqlSession sqlSession, String statementId, String paramJson, boolean commit) {
        System.out.println("param=>" + paramJson);
        int update = 0;
        try {
            if (paramJson == null || "".equals(paramJson)) {
                update = sqlSession.update(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                update = sqlSession.update(statementId, param);
                if (commit) {
                    sqlSession.commit();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        HashMap<String, Object> resMap = new HashMap<>(1);
        resMap.put("update", update);
        return JSON.toJSONString(resMap);
    }

    /**
     * update
     */
    public String update(String statementId, String paramJson, boolean commit) {
        System.out.println("param=>" + paramJson);
        int update = 0;
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            if (paramJson == null || "".equals(paramJson)) {
                update = sqlSession.update(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                update = sqlSession.update(statementId, param);
                if (commit) {
                    sqlSession.commit();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        HashMap<String, Object> resMap = new HashMap<>(1);
        resMap.put("update", update);
        return JSON.toJSONString(resMap);
    }

    /**
     * selectPage
     */
    public String selectPage(SqlSession sqlSession, String countStatementId, String listStatementId, Integer pageNum, Integer pageSize, String paramJson) {
        System.out.println("param=>" + paramJson);
        PageVo pageVo = new PageVo();

        Object o = null;
        try {
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(countStatementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(countStatementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (o == null) {
            return "";
        } else {
            String count_string = o.toString();
            Long count_long = null;
            try {
                count_long = Long.valueOf(count_string);
            } catch (Exception e) {
                e.printStackTrace();
                return "err";
            }

            if (count_long == 0) {
                pageVo.setTotal(0L);
                pageVo.setTotalPage(0L);
                pageVo.setList(new ArrayList<>());
                return JSON.toJSONString(pageVo);
            }
            pageVo.setTotal(count_long);


            long limit_start = (pageNum - 1) * pageSize;
            long limit_size = pageSize;
            RowBounds rowBounds = new RowBounds((int) limit_start, (int) limit_size);

            List<Map<String, Object>> list = new ArrayList<>();
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(listStatementId, rowBounds);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(listStatementId, param, rowBounds);
            }

            long totalPage = (count_long + pageSize - 1) / pageSize;
            pageVo.setTotalPage(totalPage);
            pageVo.setList(list);

            return JSON.toJSONString(pageVo);
        }
    }

    /**
     * selectPage
     */
    public String selectPage(String countStatementId, String listStatementId, Integer pageNum, Integer pageSize, String paramJson) {
        System.out.println("param=>" + paramJson);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PageVo pageVo = new PageVo();
        Object o = null;
        try {
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(countStatementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(countStatementId, param);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
        if (o == null) {
            return "";
        } else {
            String count_string = o.toString();
            Long count_long = null;
            try {
                count_long = Long.valueOf(count_string);
            } catch (Exception e) {
                e.printStackTrace();
                return "err";
            }

            if (count_long == 0) {
                pageVo.setTotal(0L);
                pageVo.setTotalPage(0L);
                pageVo.setList(new ArrayList<>());
                return JSON.toJSONString(pageVo);
            }
            pageVo.setTotal(count_long);


            long limit_start = (pageNum - 1) * pageSize;
            long limit_size = pageSize;
            RowBounds rowBounds = new RowBounds((int) limit_start, (int) limit_size);

            List<Map<String, Object>> list = new ArrayList<>();
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(listStatementId, rowBounds);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(listStatementId, param, rowBounds);
            }

            long totalPage = (count_long + pageSize - 1) / pageSize;
            pageVo.setTotalPage(totalPage);
            pageVo.setList(list);

            return JSON.toJSONString(pageVo);
        }
    }

    /**
     * selectPage PageHelper
     */
    public String selectPage(SqlSession sqlSession, String statementId, Integer pageNum, Integer pageSize, String paramJson) {
        try {
            System.out.println("param=>" + paramJson);
            PageVo pageVo = new PageVo();
            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> list = new ArrayList<>();
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(statementId, param);
            }
            pageVo.setTotal(page.getTotal());
            pageVo.setTotalPage((long) page.getPages());
            pageVo.setList(list);
            return JSON.toJSONString(pageVo);
        } catch (Throwable throwable) {
            return "err";
        }
    }

    /**
     * selectPage PageHelper
     */
    public String selectPage(String statementId, Integer pageNum, Integer pageSize, String paramJson) {
        try {
            System.out.println("param=>" + paramJson);
            PageVo pageVo = new PageVo();
            SqlSession sqlSession = sqlSessionFactory.openSession();
            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> list = new ArrayList<>();
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(statementId, param);
            }
            pageVo.setTotal(page.getTotal());
            pageVo.setTotalPage((long) page.getPages());
            pageVo.setList(list);
            return JSON.toJSONString(pageVo);
        } catch (Throwable throwable) {
            return "err";
        }
    }


}
