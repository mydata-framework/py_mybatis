import datetime

import py_mybatis

from py_mybatis import JavaEnv, PyMybatis

java_env = JavaEnv('dev')
java_env.append_class_path('/Users/liutao/project/githubs_my/py_mybatis/py_mybatis_demos/demo3')
java_env.start_jvm()

db1_mybatis = PyMybatis('db1_config/db_1mybatis-config.xml')
db2_mybatis = PyMybatis('db2_config/db2_mybatis-config.xml')
pg_mybatis = PyMybatis('db3_config/db3_mybatis-config.xml')

if __name__ == '__main__':
    name = 'name' + str(datetime.datetime.now())
    print(name)

    # 多个数据库同时读写, 并放到一个事务中
    with db1_mybatis.getTs() as db1_ts, db2_mybatis.getTs() as db2_ts, pg_mybatis.getTs() as db3_ts:
        insert_1 = db1_mybatis.insert(
            sqlSession=db1_ts,
            statementId='db1.insert_user',
            dictParam={'username': name},
            keyProperty='id'
        )
        insert_1_one = db1_mybatis.selectOne(
            sqlSession=db1_ts,
            statementId='db1.select_user_by_id',
            dictParam={'id': insert_1['id']}
        )
        print(insert_1_one)

        ##

        insert_2 = db2_mybatis.insert(
            sqlSession=db2_ts,
            statementId='db2.insert_user',
            dictParam={'username': name},
            keyProperty='id'
        )
        insert_2_one = db2_mybatis.selectOne(
            sqlSession=db2_ts,
            statementId='db2.select_user_by_id',
            dictParam={'id': insert_2['id']}
        )
        print(insert_2_one)

        ##

        pg_insert = pg_mybatis.insert(
            sqlSession=db3_ts,
            statementId='pg.insert_user',
            dictParam={'name': name},
            keyProperty='id'
        )
        pg_one = pg_mybatis.selectOne(
            sqlSession=db3_ts,
            statementId='pg.select_user_by_id',
            dictParam={'id': pg_insert['id']}
        )
        print(pg_one)
