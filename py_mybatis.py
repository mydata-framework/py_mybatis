# encoding: utf-8
# -*- coding: utf-8 -*-

import json
import platform
import sys
import jpype
import jpype.imports

# 是否windows系统
is_windows = (platform.system() == "Windows")


class PyMybatis:
    def __init__(self, class_path) -> None:
        """
        class_path 默认为配置文件的绝对路径
        :param class_path:
        """
        super().__init__()
        self.class_path = class_path
        self.config_name = None
        self.mybatis = None
        self.sqlSessionFactory = None
        self.py_mybatis_java_lib_path = None
        self.env = 'users'

    def _set_env(self, env='dev'):
        self.env = env

    def __start_dev_JVM_(self):
        """
        开发环境下的jvm环境
        :return:
        """
        if is_windows:
            dev_project_class_path = 'D:\\project\\githubs\\py_mybatis\\py_mybatis\\py_mybatis_java_module\\target\\classes'
            dev_system_class_path = "D:\\Develop\\jdk\\jdk8\\lib\\tools.jar"
            dev_mysql_connector_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\mysql\\mysql-connector-java\\8.0.29\\mysql-connector-java-8.0.29.jar'
            dev_p6spy_connector_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\p6spy\\p6spy\\3.9.1\\p6spy-3.9.1.jar'
            dev_mybatis_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\org\\mybatis\\mybatis\\3.5.10\\mybatis-3.5.10.jar'
            dev_fastjson_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\com\\alibaba\\fastjson\\1.2.79\\fastjson-1.2.79.jar'
            dev_page_helper_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\com\\github\\pagehelper\\pagehelper\\5.3.2\\pagehelper-5.3.2.jar'
            dev_jsqlparser_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\com\\github\\jsqlparser\\jsqlparser\\4.5\\jsqlparser-4.5.jar'
            jpype.startJVM(
                classpath=[
                    dev_system_class_path,
                    dev_project_class_path,
                    dev_mysql_connector_jar_path,
                    dev_p6spy_connector_jar_path,
                    dev_mybatis_jar_path,
                    dev_jsqlparser_jar_path,
                    dev_page_helper_jar_path,
                    dev_fastjson_jar_path,
                    self.class_path
                ]
            )
        else:
            dev_project_class_path = "/Users/liutao/project/githubs_my/py_mybatis/py_mybatis_java_module/target/classes"
            dev_system_class_path = '/Users/liutao/.jenv/versions/11.0/lib/dt.jar:/Users/liutao/.jenv/versions/11.0/lib/tools.jar'
            dev_mysql_connector_jar_path = '/Users/liutao/.m2/repository/mysql/mysql-connector-java/8.0.29/mysql-connector-java-8.0.29.jar'
            dev_p6spy_connector_jar_path = '/Users/liutao/.m2/repository/p6spy/p6spy/3.9.1/p6spy-3.9.1.jar'
            dev_mybatis_jar_path = '/Users/liutao/.m2/repository/org/mybatis/mybatis/3.5.10/mybatis-3.5.10.jar'
            dev_fastjson_jar_path = '/Users/liutao/.m2/repository/com/alibaba/fastjson/1.2.79/fastjson-1.2.79.jar'
            dev_page_helper_jar_path = '/Users/liutao/.m2/repository/com/github/pagehelper/pagehelper/5.3.2/pagehelper-5.3.2.jar'
            dev_jsqlparser_jar_path = '/Users/liutao/.m2/repository/com/github/jsqlparser/jsqlparser/4.5/jsqlparser-4.5.jar'
            jpype.startJVM(
                classpath=[
                    dev_system_class_path,
                    dev_project_class_path,
                    dev_mysql_connector_jar_path,
                    dev_p6spy_connector_jar_path,
                    dev_mybatis_jar_path,
                    dev_jsqlparser_jar_path,
                    dev_page_helper_jar_path,
                    dev_fastjson_jar_path,
                    self.class_path
                ]
            )

    def __start_users_JVM_(self):
        """
        用户pip install 环境下的jvm环境
        :return:
        """
        python_installation_path = sys.executable
        if is_windows:
            split = python_installation_path.split('python.exe')
            python_path = split[0]
            self.py_mybatis_java_lib_path = python_path + 'py_mybatis_java_lib\\*'
        else:
            split = python_installation_path.split('/bin/')
            python_path = split[0]
            self.py_mybatis_java_lib_path = python_path + '/py_mybatis_java_lib/*'

        jpype.startJVM(
            classpath=[
                jpype.getDefaultJVMPath(),
                self.py_mybatis_java_lib_path,
                self.class_path
            ]
        )

    def config(self, config_name):
        self.config_name = config_name
        if self.env == 'dev':
            self.__start_dev_JVM_()
        else:
            self.__start_users_JVM_()

        Mybatis = jpype.JClass('com.pymybatis.Mybatis')
        self.mybatis = Mybatis()
        self.sqlSessionFactory = self.mybatis.config(config_name)

    def getSqlSession(self, autoCommit=True):
        sqlSession = self.mybatis.getSqlSession(self.sqlSessionFactory)
        sqlSession.getConnection().setAutoCommit(autoCommit)
        return sqlSession

    def beginTransaction(self, sqlSession):
        connection = sqlSession.getConnection()
        connection.setAutoCommit(False)

    def commit(self, sqlSession):
        sqlSession.commit()

    def close(self, sqlSession):
        sqlSession.close()

    def getTransactionedSqlSession(self):
        return self.mybatis.getTransactionedSqlSession()

    def selectOne(self, sqlSession=None, statementId=None, dictParam=None):
        if dictParam is None:
            dictParam = ''
        else:
            dictParam = json.dumps(dictParam, ensure_ascii=False).encode('utf-8')

        __java_long_String = None
        if sqlSession is None:
            __java_long_String = self.mybatis.selectOne(statementId, dictParam)
        else:
            __java_long_String = self.mybatis.selectOne(sqlSession, statementId, dictParam)

        __str = str(__java_long_String)

        if __str == 'err':
            raise RuntimeError("err")

        if __str == '':
            return None

        __dict_one = json.loads(__str)
        return __dict_one

    def selectList(self, sqlSession=None, statementId=None, dictParam=None):
        if dictParam is None:
            dictParam = ''
        else:
            dictParam = json.dumps(dictParam, ensure_ascii=False).encode('utf-8')

        __java_long_String = None
        if sqlSession is None:
            __java_long_String = self.mybatis.selectList(statementId, dictParam)
        else:
            __java_long_String = self.mybatis.selectList(sqlSession, statementId, dictParam)

        __str = str(__java_long_String)

        if __str == 'err':
            raise RuntimeError("err")

        if __str == '':
            return None

        __dict_list = json.loads(__str)
        return __dict_list

    def selectValue(self, sqlSession=None, statementId=None, dictParam=None):
        if dictParam is None:
            dictParam = ''
        else:
            dictParam = json.dumps(dictParam, ensure_ascii=False).encode('utf-8')

        __java_long_String = None
        if sqlSession is None:
            __java_long_String = self.mybatis.selectValue(statementId, dictParam)
        else:
            __java_long_String = self.mybatis.selectValue(sqlSession, statementId, dictParam)

        __str = str(__java_long_String)

        if __str == 'err':
            raise RuntimeError("err")

        if __str == '':
            return None

        __dict_one = json.loads(__str)
        return __dict_one

    def insert(self, sqlSession=None, statementId=None, dictParam=None, keyProperty=''):
        if dictParam is None:
            dictParam = ''
        else:
            dictParam = json.dumps(dictParam, ensure_ascii=False).encode('utf-8')

        __java_long_String = None
        if sqlSession is None:
            __java_long_String = self.mybatis.insert(statementId, dictParam, keyProperty)
        else:
            __java_long_String = self.mybatis.insert(sqlSession, statementId, dictParam, keyProperty)

        __str = str(__java_long_String)

        if __str == 'err':
            raise RuntimeError("err")

        if __str == '':
            return None

        __dict_one = json.loads(__str)
        return __dict_one

    def update(self, sqlSession=None, statementId=None, dictParam=None):
        if dictParam is None:
            dictParam = ''
        else:
            dictParam = json.dumps(dictParam, ensure_ascii=False).encode('utf-8')

        __java_long_String = None
        if sqlSession is None:
            __java_long_String = self.mybatis.update(statementId, dictParam)
        else:
            __java_long_String = self.mybatis.update(sqlSession, statementId, dictParam)

        __str = str(__java_long_String)

        if __str == 'err':
            raise RuntimeError("err")

        if __str == '':
            return None

        __dict_one = json.loads(__str)
        return __dict_one

    def selectPage(self, sqlSession=None, countStatementId=None, listStatementId=None, pageNum=None, pageSize=None,
                   dictParam=None):
        if dictParam is None:
            dictParam = ''
        else:
            dictParam = json.dumps(dictParam, ensure_ascii=False).encode('utf-8')

        __java_long_String = None
        if sqlSession is None:
            __java_long_String = self.mybatis.selectPage(countStatementId, listStatementId, pageNum, pageSize,
                                                         dictParam)
        else:
            __java_long_String = self.mybatis.selectPage(sqlSession, countStatementId, listStatementId, pageNum,
                                                         pageSize, dictParam)

        __str = str(__java_long_String)

        if __str == 'err':
            raise RuntimeError("err")

        if __str == '':
            return None

        __dict_one = json.loads(__str)
        return __dict_one

    def selectPageByPageHelper(self, sqlSession=None, statementId=None, pageNum=None, pageSize=None, dictParam=None):
        if dictParam is None:
            dictParam = ''
        else:
            dictParam = json.dumps(dictParam, ensure_ascii=False).encode('utf-8')

        __java_long_String = None

        if sqlSession is None:
            __java_long_String = self.mybatis.selectPageByPageHelper(statementId, pageNum, pageSize, dictParam)
        else:
            __java_long_String = self.mybatis.selectPageByPageHelper(sqlSession, statementId, pageNum, pageSize,
                                                                     dictParam)

        __str = str(__java_long_String)

        if __str == 'err':
            raise RuntimeError("err")

        if __str == '':
            return None

        __dict_one = json.loads(__str)
        return __dict_one
