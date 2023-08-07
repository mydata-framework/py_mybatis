package com.pymybatis.ext;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class TransactionedSqlSession implements SqlSession {

    private SqlSession sqlSession;

    public TransactionedSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public <T> T selectOne(String statement) {
        return sqlSession.selectOne(statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return sqlSession.selectOne(statement, parameter);
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return sqlSession.selectList(statement);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return sqlSession.selectList(statement, parameter);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return sqlSession.selectList(statement, parameter, rowBounds);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return sqlSession.selectMap(statement, mapKey);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return sqlSession.selectMap(statement, parameter, mapKey);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return sqlSession.selectMap(statement, parameter, mapKey, rowBounds);
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement) {
        return sqlSession.selectCursor(statement);
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement, Object parameter) {
        return sqlSession.selectCursor(statement, parameter);
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
        return sqlSession.selectCursor(statement, parameter, rowBounds);
    }

    @Override
    public void select(String statement, Object parameter, ResultHandler handler) {
        sqlSession.select(statement, parameter, handler);
    }

    @Override
    public void select(String statement, ResultHandler handler) {
        sqlSession.select(statement, handler);
    }

    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        sqlSession.select(statement, parameter, rowBounds, handler);
    }

    @Override
    public int insert(String statement) {
        return sqlSession.insert(statement);
    }

    @Override
    public int insert(String statement, Object parameter) {
        return sqlSession.insert(statement, parameter);
    }

    @Override
    public int update(String statement) {
        return sqlSession.update(statement);
    }

    @Override
    public int update(String statement, Object parameter) {
        return sqlSession.update(statement, parameter);
    }

    @Override
    public int delete(String statement) {
        return sqlSession.delete(statement);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return sqlSession.delete(statement, parameter);
    }

    @Override
    public void commit() {
        sqlSession.commit();
    }

    @Override
    public void commit(boolean force) {
        sqlSession.commit(force);
    }

    @Override
    public void rollback() {
        sqlSession.rollback();
    }

    @Override
    public void rollback(boolean force) {
        sqlSession.rollback(force);
    }

    @Override
    public List<BatchResult> flushStatements() {
        return sqlSession.flushStatements();
    }

    @Override
    public void close() {
        System.out.println("TransactionedSqlSession:close()");
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void clearCache() {
        sqlSession.clearCache();
    }

    @Override
    public Configuration getConfiguration() {
        return sqlSession.getConfiguration();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return sqlSession.getMapper(type);
    }

    @Override
    public Connection getConnection() {
        return sqlSession.getConnection();
    }
}
