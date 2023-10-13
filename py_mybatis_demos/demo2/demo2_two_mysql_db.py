import py_mybatis

from py_mybatis import JavaEnv, PyMybatis

java_env = JavaEnv('dev')
java_env.append_class_path('/Users/liutao/project/githubs_my/py_mybatis/py_mybatis_demos/demo2')
java_env.start_jvm()

db1_mybatis = PyMybatis('db1_config/db_1mybatis-config.xml')
db2_mybatis = PyMybatis('db2_config/db2_mybatis-config.xml')

if __name__ == '__main__':
    insert_1 = db1_mybatis.insert(
        statementId='db1.insert_user',
        dictParam={'username': 'db1_nama_哈哈'},
        keyProperty='id'
    )
    print(insert_1)
    insert_1_one = db1_mybatis.selectOne(statementId='db1.select_user_by_id', dictParam={'id': insert_1['id']})
    print(insert_1_one)

    insert_2 = db2_mybatis.insert(
        statementId='db2.insert_user',
        dictParam={'username': 'db2_nama_xixixixix'},
        keyProperty='id'
    )
    print(insert_2)
    insert_2_one = db2_mybatis.selectOne(statementId='db2.select_user_by_id', dictParam={'id': insert_2['id']})
    print(insert_2_one)
