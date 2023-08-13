import py_mybatis
from py_mybatis import PyMybatis

if py_mybatis.is_windows:
    pyMybatis = PyMybatis('D:\\project\\githubs\\py_mybatis\\py_mybatis\\py_mybatis_demo_config_win')
    pyMybatis._set_env()
    pyMybatis.config('mybatis-config.xml')
else:
    pyMybatis = PyMybatis('/Users/liutao/project/githubs_my/py_mybatis/py_mybatis_demo_config')
    pyMybatis._set_env()
    pyMybatis.config('mybatis-config.xml')



def test1():
    one = pyMybatis.selectOne(statementId='UserMapper.selectById', dictParam={'id': 1})
    print(one)

    list = pyMybatis.selectList(statementId='UserMapper.selectList')
    print(list)

    value = pyMybatis.selectValue(statementId='UserMapper.selectId', dictParam={'id': 1})
    print(value)

    insert = pyMybatis.insert(statementId='UserMapper.insertUser', dictParam={'username': 'liutao 001'}, keyProperty='id')
    print(insert)

    update = pyMybatis.update(statementId="UserMapper.updateUser", dictParam={'username': 'liutao 002', 'id': 40})
    print(update)

    page = pyMybatis.selectPageByPageHelper(statementId='selectList', pageNum=1, pageSize=10)
    print(page)

    page = pyMybatis.selectPage(countStatementId='selectPage_count', listStatementId='selectPage_list', pageNum=1, pageSize=10)
    print(page)


def test2():
    sqlSession = pyMybatis.getSqlSession(autoCommit=False)
    # pyMybatis.beginTransaction(sqlSession)

    one = pyMybatis.selectOne(sqlSession=sqlSession, statementId='UserMapper.selectById', dictParam={'id': 1})
    print(one)

    two = pyMybatis.selectOne(sqlSession=sqlSession, statementId='UserMapper.selectById', dictParam={'id': 2})
    print(two)

    list = pyMybatis.selectList(sqlSession=sqlSession, statementId='UserMapper.selectList')
    print(list)

    value = pyMybatis.selectValue(sqlSession=sqlSession, statementId='UserMapper.selectId', dictParam={'id': 1})
    print(value)

    insert = pyMybatis.insert(sqlSession=sqlSession, statementId='UserMapper.insertUser', dictParam={'username': 'liutao 007'}, keyProperty='id')
    print(insert)

    update = pyMybatis.update(sqlSession=sqlSession, statementId="UserMapper.updateUser", dictParam={'username': 'liutao 008', 'id': insert['id']} )
    print(update)

    page = pyMybatis.selectPageByPageHelper(sqlSession=sqlSession, statementId='selectList', pageNum=1, pageSize=10)
    print(page)

    page = pyMybatis.selectPage(sqlSession=sqlSession, countStatementId='selectPage_count', listStatementId='selectPage_list',  pageNum=1, pageSize=10)
    print(page)

    pyMybatis.commit(sqlSession)
    pyMybatis.close(sqlSession)


def test3():
    with pyMybatis.getTransactionedSqlSession() as sqlSession:
        insert = pyMybatis.insert(sqlSession=sqlSession, statementId='UserMapper.insertUser', dictParam={'username': 'liutao 007'}, keyProperty='id')
        print(insert)


if __name__ == '__main__':
    test1()
    print( '============================================================================================================' )
    test2()
    print( '============================================================================================================' )
    test3()
    print( '============================================================================================================' )
