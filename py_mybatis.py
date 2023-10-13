# encoding: utf-8
# -*- coding: utf-8 -*-

import json
import platform
import sys

import jpype
import jpype.imports

#是否windows系统
is_windows = (platform.system() == "Windows")

#Java环境类
class JavaEnv:
    def __init__(self, env='users'):
        self.class_path_list = []
        self.env = env

    def append_class_path(self, class_path):
        self.class_path_list.append(class_path)

    def start_jvm(self):
        if jpype.isJVMStarted():
            pass
        else:
            if self.env == 'dev':
                self.__start_dev_JVM_()
            else:
                self.__start_users_JVM_()

    def __start_dev_JVM_(self):
        """
        开发环境下的jvm环境
        :return:
        """
        if is_windows:
            # mybatis
            mybatis_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\org\\mybatis\\mybatis\\3.5.10\\mybatis-3.5.10.jar'

            # mybatis plugins
            pagehelper_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\com\\github\\pagehelper\\pagehelper\\5.3.2\\pagehelper-5.3.2.jar'
            jsqlparser_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\com\\github\\jsqlparser\\jsqlparser\\4.5\\jsqlparser-4.5.jar'

            # db drivers
            p6spy_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\p6spy\\p6spy\\3.9.1\\p6spy-3.9.1.jar'
            mysql_connector_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\mysql\\mysql-connector-java\\8.0.29\\mysql-connector-java-8.0.29.jar'
            pgsql_connector_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\org\\postgresql\\postgresql\\42.6.0\\postgresql-42.6.0.jar'

            # support utils
            fastjson2_jar_path = 'C:\\Users\\liutao\\.m2\\repository\\com\\alibaba\\fastjson2\\fastjson2\\2.0.41\\fastjson2-2.0.41.jar'

            # project
            project_class_path = 'D:\\project\\githubs\\py_mybatis\\py_mybatis\\py_mybatis_java_module\\target\\classes'
            system_class_path = "D:\\Develop\\jdk\\jdk8\\lib\\tools.jar"
        else:
            # mybatis
            mybatis_jar_path = '/Users/liutao/.m2/repository/org/mybatis/mybatis/3.5.10/mybatis-3.5.10.jar'

            # mybatis plugins
            pagehelper_jar_path = '/Users/liutao/.m2/repository/com/github/pagehelper/pagehelper/5.3.2/pagehelper-5.3.2.jar'
            jsqlparser_jar_path = '/Users/liutao/.m2/repository/com/github/jsqlparser/jsqlparser/4.5/jsqlparser-4.5.jar'

            # db drivers
            p6spy_jar_path = '/Users/liutao/.m2/repository/p6spy/p6spy/3.9.1/p6spy-3.9.1.jar'
            mysql_connector_jar_path = '/Users/liutao/.m2/repository/mysql/mysql-connector-java/8.0.29/mysql-connector-java-8.0.29.jar'
            pgsql_connector_jar_path = '/Users/liutao/.m2/repository/org/postgresql/postgresql/42.6.0/postgresql-42.6.0.jar'

            # support utils
            fastjson2_jar_path = '/Users/liutao/.m2/repository/com/alibaba/fastjson2/fastjson2/2.0.41/fastjson2-2.0.41.jar'

            # project
            project_class_path = "/Users/liutao/project/githubs_my/py_mybatis/py_mybatis_java_module/target/classes"
            system_class_path = '/Users/liutao/.jenv/versions/11.0/lib/dt.jar:/Users/liutao/.jenv/versions/11.0/lib/tools.jar'

        # 初始化jvm,运行jvm
        jpype.startJVM(
            classpath=[
                # mybatis
                mybatis_jar_path,
                # mybatis plugins
                pagehelper_jar_path,
                jsqlparser_jar_path,
                # db drivers
                p6spy_jar_path,
                mysql_connector_jar_path,
                pgsql_connector_jar_path,
                # support utils
                fastjson2_jar_path,
                # project
                project_class_path,
                system_class_path,
                # 业务开发追加地址
                *self.class_path_list
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
                *self.class_path_list
            ]
        )


# 程序主入口类
class PyMybatis:

    def __init__(self, config_xml_name) -> None:
        """
        class_path 默认为配置文件的绝对路径
        :param class_path:
        """
        super().__init__()
        self.config_xml_name = config_xml_name

        Mybatis = jpype.JClass('com.pymybatis.Mybatis')
        self.mybatis = Mybatis()
        self.sqlSessionFactory = self.mybatis.config(config_xml_name)

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

    def getTs(self):
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
