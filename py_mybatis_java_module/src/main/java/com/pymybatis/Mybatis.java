package com.pymybatis;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pymybatis.ext.TransactionedSqlSession;
import com.pymybatis.vo.PageVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
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


    private SqlSessionFactory sqlSessionFactory = null;


    /**
     * 获取并设置 SqlSessionFactory
     */
    public SqlSessionFactory config(String resource) throws IOException {
        System.out.println("resource:" + resource);
        Path path = Paths.get(resource);
        boolean isAbsolutePath = path.isAbsolute();
        System.out.println("isAbsolutePath:" + isAbsolutePath);

        InputStream inputStream = null;
        if (isAbsolutePath) {
            inputStream = new FileInputStream(new File(resource));
        } else {
            inputStream = Resources.getResourceAsStream(resource);
        }
        System.out.println("inputStream:" + inputStream);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        System.out.println("sqlSessionFactory:" + sqlSessionFactory);
        this.sqlSessionFactory = sqlSessionFactory;
        return sqlSessionFactory;
    }

    /**
     * 获取 SqlSessionFactory
     */
    private SqlSessionFactory getSqlSessionFactory(String resource) throws IOException {
        Path path = Paths.get(resource);
        boolean isAbsolutePath = path.isAbsolute();

        InputStream inputStream = null;
        if (isAbsolutePath) {
            inputStream = new FileInputStream(new File(resource));
        } else {
            inputStream = Resources.getResourceAsStream(resource);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
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
     * 用于可以try(这种方式可以自动提交事务)
     * 获得自动能够自动commit 和 close 的 SqlSession
     */
    public TransactionedSqlSession getTransactionedSqlSession() throws SQLException {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        sqlSession.getConnection().setAutoCommit(false);
        return new TransactionedSqlSession(sqlSession);
    }

    /**
     * selectOne
     */
    public String selectOne(SqlSession sqlSession, String statementId, String paramJson) {
        try {
            System.out.println("param=>" + paramJson);
            Object o = null;
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
            if (o == null) {
                return "";
            } else {
                return JSON.toJSONString(o);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * selectOne
     */
    public String selectOne(String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.getConnection().setAutoCommit(true);

            Object o = null;

            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
            sqlSession.close();

            if (o == null) {
                return "";
            } else {
                return JSON.toJSONString(o);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * selectList
     */
    public String selectList(SqlSession sqlSession, String statementId, String paramJson) {
        try {
            System.out.println("param=>" + paramJson);
            List list = null;
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(statementId, param);
            }
            if (list == null) {
                return "";
            } else {
                return JSON.toJSONString(list);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * selectList
     */
    public String selectList(String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);

        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.getConnection().setAutoCommit(true);

            List list = null;
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(statementId, param);
            }
            sqlSession.close();

            if (list == null) {
                return "";
            } else {
                return JSON.toJSONString(list);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * selectValue
     */
    public String selectValue(SqlSession sqlSession, String statementId, String paramJson) {
        try {
            System.out.println("param=>" + paramJson);
            Object o = null;
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
            if (o == null) {
                return "";
            } else {
                HashMap<String, Object> resMap = new HashMap<>(1);
                resMap.put("key", o);
                return JSON.toJSONString(resMap);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * selectValue
     */
    public String selectValue(String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.getConnection().setAutoCommit(true);

            Object o = null;

            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(statementId, param);
            }
            sqlSession.close();

            if (o == null) {
                return "";
            } else {
                HashMap<String, Object> resMap = new HashMap<>(1);
                resMap.put("key", o);
                return JSON.toJSONString(resMap);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * insert
     */
    public String insert(SqlSession sqlSession, String statementId, String paramJson, String keyProperty) {
        try {
            System.out.println("param=>" + paramJson);
            int insert = 0;
            HashMap param = null;
            if (paramJson == null || "".equals(paramJson)) {
                insert = sqlSession.insert(statementId);
            } else {
                param = JSON.parseObject(paramJson, HashMap.class);
                insert = sqlSession.insert(statementId, param);
                if (sqlSession.getConnection().getAutoCommit()) {
                    sqlSession.commit();
                }
            }
            HashMap<String, Object> resMap = new HashMap<>(1);
            resMap.put("insert", insert);
            if (param != null && keyProperty != null && !keyProperty.equals("")) {
                resMap.put(keyProperty, param.get(keyProperty));
            }
            return JSON.toJSONString(resMap);
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * insert
     */
    public String insert(String statementId, String paramJson, String keyProperty) {
        System.out.println("param=>" + paramJson);
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.getConnection().setAutoCommit(true);

            int insert = 0;
            HashMap param = null;
            if (paramJson == null || "".equals(paramJson)) {
                insert = sqlSession.insert(statementId);
            } else {
                param = JSON.parseObject(paramJson, HashMap.class);
                insert = sqlSession.insert(statementId, param);

            }
            //if (sqlSession.getConnection().getAutoCommit()) {
            sqlSession.commit();
            //}
            sqlSession.close();

            HashMap<String, Object> resMap = new HashMap<>(1);
            resMap.put("insert", insert);
            if (param != null && keyProperty != null && !keyProperty.equals("")) {
                resMap.put(keyProperty, param.get(keyProperty));
            }
            return JSON.toJSONString(resMap);
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * update
     */
    public String update(SqlSession sqlSession, String statementId, String paramJson) {
        try {
            System.out.println("param=>" + paramJson);
            int update = 0;
            if (paramJson == null || "".equals(paramJson)) {
                update = sqlSession.update(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                update = sqlSession.update(statementId, param);
                if (sqlSession.getConnection().getAutoCommit()) {
                    sqlSession.commit();
                }
            }
            HashMap<String, Object> resMap = new HashMap<>(1);
            resMap.put("update", update);
            return JSON.toJSONString(resMap);
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * update
     */
    public String update(String statementId, String paramJson) {
        System.out.println("param=>" + paramJson);
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.getConnection().setAutoCommit(true);
            int update = 0;

            if (paramJson == null || "".equals(paramJson)) {
                update = sqlSession.update(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                update = sqlSession.update(statementId, param);
            }
            //if (sqlSession.getConnection().getAutoCommit()) {
            sqlSession.commit();
            //}
            sqlSession.close();

            HashMap<String, Object> resMap = new HashMap<>(1);
            resMap.put("update", update);
            return JSON.toJSONString(resMap);
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }

    }

    /**
     * selectPage
     */
    public String selectPage(SqlSession sqlSession, String countStatementId, String listStatementId, int pageNum, int pageSize, String paramJson) {
        System.out.println("param=>" + paramJson);
        try {
            PageVo pageVo = new PageVo();
            Object o = null;
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(countStatementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(countStatementId, param);
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
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * selectPage
     */
    public String selectPage(String countStatementId, String listStatementId, int pageNum, int pageSize, String paramJson) {
        System.out.println("param=>" + paramJson);

        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.getConnection().setAutoCommit(true);

            PageVo pageVo = new PageVo();
            Object o = null;
            if (paramJson == null || "".equals(paramJson)) {
                o = sqlSession.selectOne(countStatementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                o = sqlSession.selectOne(countStatementId, param);
            }

            if (o == null) {
                sqlSession.close();
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
                sqlSession.close();

                long totalPage = (count_long + pageSize - 1) / pageSize;
                pageVo.setTotalPage(totalPage);
                pageVo.setList(list);

                return JSON.toJSONString(pageVo);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return "err";
        }
    }

    /**
     * selectPage PageHelper
     */
    public String selectPageByPageHelper(SqlSession sqlSession, String statementId, int pageNum, int pageSize, String paramJson) {
        try {
            System.out.println("sqlSession=>" + sqlSession + ",pageNum=>" + pageNum + ",pageSize=>" + pageSize + ",param=>" + paramJson);
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
    public String selectPageByPageHelper(String statementId, int pageNum, int pageSize, String paramJson) {
        System.out.println("pageNum=>" + pageNum + ",pageSize=>" + pageSize + ",param=>" + paramJson);
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.getConnection().setAutoCommit(true);

            PageVo pageVo = new PageVo();
            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<Map<String, Object>> list = new ArrayList<>();
            if (paramJson == null || "".equals(paramJson)) {
                list = sqlSession.selectList(statementId);
            } else {
                HashMap param = JSON.parseObject(paramJson, HashMap.class);
                list = sqlSession.selectList(statementId, param);
            }
            sqlSession.close();

            pageVo.setTotal(page.getTotal());
            pageVo.setTotalPage((long) page.getPages());
            pageVo.setList(list);
            return JSON.toJSONString(pageVo);
        } catch (Throwable throwable) {
            return "err";
        }
    }

}
