#! /usr/bin/env python
# -*- coding: utf-8 -*_
# Author: ***<***gmail.com>
import glob
from distutils.core import setup

import setuptools



setup(
    name='py_mybatis',  # 包的名字
    version='0.0.2',  # 版本号
    description='my_mybatis',  # 描述
    author='liutao',  # 作者
    author_email='msliutao@foxmail.com',  # 你的邮箱**
    url='https://github.com/mydata-framework/py_mybatis',  # 可以写github上的地址，或者其他地址

    # 打包到安装包中配置
    py_modules=[
        'py_mybatis',
        'py_mybatis_demo'
    ],
    data_files=[
        (
            'py_mybatis_demo_config',
            [
                'py_mybatis_demo_config/mybatis-3-config.dtd',
                'py_mybatis_demo_config/mybatis-3-mapper.dtd',
                'py_mybatis_demo_config/mybatis-config.xml',
                'py_mybatis_demo_config/spy.properties',
                'py_mybatis_demo_config/UserMapper.xml'
            ]
        ),
        (
            'py_mybatis_java_lib',
            [
                'py_mybatis_java_lib/fastjson-1.2.79.jar',
                'py_mybatis_java_lib/jsqlparser-4.5.jar',
                'py_mybatis_java_lib/mybatis-3.5.10.jar',
                'py_mybatis_java_lib/mysql-connector-java-8.0.29.jar',
                'py_mybatis_java_lib/p6spy-3.9.1.jar',
                'py_mybatis_java_lib/pagehelper-5.3.2.jar',
                'py_mybatis_java_lib/py_mybatis_java_module-0.0.1-SNAPSHOT.jar'
            ]
        )
    ],
    # 依赖包
    install_requires=[
        'JPype1==1.4.1'
    ],
    classifiers=[
        'Development Status :: 4 - Beta',  # Beta 阶段
        'Operating System :: Microsoft :: Windows',  # 你的操作系统
        'Operating System :: POSIX :: Linux',
        'Operating System :: MacOS',
        'Intended Audience :: Developers',  # Developers 是一个常见的项目受众分类标签，用于指示项目的目标受众是开发人员。
        'License :: OSI Approved :: Apache Software License',  # BSD认证
        'Programming Language :: Python',  # 支持的语言
        'Programming Language :: Python :: 3',  # python版本 。。。
        'Topic :: Software Development :: Libraries'
    ],

    zip_safe=True,
)

## 在setup.py目录下执行
## python  setup.py  sdist
## cd dist
## pip install py_mybatis-0.0.1.tar.gz

## python setup.py bdist_wheel
## python setup.py sdist bdist_wheel
## 最终生成一个dist文件夹，在文件夹里面就有一个创建好的安装包，格式为xxx.tar.gz的压缩包

## pip list   # 显示所有已安装的包

## fq pip install --upgrade setuptools wheel
## fq pip install --upgrade pip
## pip install --no-use-pep517 py_mybatis-0.0.1.tar.gz
