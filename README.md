# py-mybatis

#### 介绍

python下,一个简单好用的orm框架.
<br>
<br>
在平时工作中,我需要同时连接mysql和postgresql或其他的数据库, 
<br>
可能是做一些简单的数据整合, 导出excel等操作,
<br>
而同时我又不太想学习 SQLAlchemy, 也不能说做一个Django项目来使用它的ORM,
<br>
所以我选择简单好用的 PyMySQL 和 psycopg2-binary 分别连接和操作 mysql 和 postgresql,
<br>
PyMySQL 和 psycopg2-binary 的操作方式完全不同,
<br>
并且, PyMySQL 和 psycopg2-binary 对查询一条数据的返回方式不同, 一个是dict, 一个是元组,
<br>
虽然我的需求完成了, 但是没有统一的api, 这种方式我不喜欢, 
<br>
所以我需要一个, 支持连接多种数据库, 具有统一的api操作查询, 并且使用同样简单方便的工具,
<br>
它不能太重, 要轻量, 简单, 一看就会的那种!!!
<br>
所以, 我用了这两天把py-mybatis整出来, 用来满足平时的使用, 
<br>
开源,希望能帮助到具有相同需要的人,需要这个工具的人;
<br>
<br>
注意: 由于我是一个java开发者,使用python从学习到写代码也就这两周时间,
<br>
所以我为了简单并站在巨人的肩膀上, 我使用python对java框架mybatis封装了一层,
<br>
如果你好奇,这里给你给你mybatis官网: https://mybatis.org/mybatis-3/zh/index.html
<br>
所以, 这要求你需要java环境, 如何安装java也是非常简单的事情!
<br>
这么简单的事情相信大家都能搞定, 我这里提供两种下载入口:
<br>
java下载方式1: https://www.oracle.com/java/technologies/downloads/
<br>
java下载方式2: https://adoptium.net/zh-CN/temurin/releases/
<br>
<br>
<br>















### 快速上手

- 1 安装
- 2 配置
- 3 代码

#### 安装

```shell
pip install py-mybatis
```

#### 配置

1 你需要创建一个配置目录,可以是在你的项目中创建例如:
<br>py_mybatis_config ;

2 将配置文件放入 py_mybatis_config 目录,
<br>配置文件已经在安装时放入到了你的python安装目录的 py_mybatis_demo_config 目录中,
<br>例如python安装在/Users/liutao/.pyenv/versions/3.9.9/
<br>则在该目录下的py_mybatis_demo_config 目录中;
<br>当然在当前git项目中也存在这个目录;

3 你需要修改你的项目中的配置目录下 mybatis-config.xml 配置文件,
<br>如果你是mysql则仅需要修改url, username, password 中的内容,
<br>value为你需要填入数据库链接信息;
<br>如果是其他数据库则需要根据情况做对应修改,这需要再后续的章节进行赘述;

#### 代码

```python
## 1 引入包
from py_mybatis import PyMybatis
## 2 指定配置文件目录的全路径
pyMybatis = PyMybatis('/Users/liutao/project/githubs_my/py_mybatis/py_mybatis_demo_config')
## 3 指定配置文件名称
pyMybatis.config('mybatis-config.xml')
## 4 查询 (sql写在UserMapper.xml中)
one = pyMybatis.selectOne(statementId='UserMapper.selectById', dictParam={'id': 1})
list = pyMybatis.selectList(statementId='UserMapper.selectList')
```
<br>
<br>
<br>

### python开发者-快速上手

#### 安装
```shell
pip install py-mybatis
```

#### 配置
已奖配置文件放在python安装目录, 也可以在git项目中的py_mybatis_demo_config目录下获取到
<br>拷贝一份到项目中, 并修改数据库链接信息

#### 代码
已经提供了 py_mybatis_demo.py 案例代码, 相信你一看就明白;
<br>
<br>
<br>

### java开发者-使用python-快速上手
java开发者的福利
<br>
使用py-mybatis, 需要java环境, 而这是我们先天优势, 我们本身就有java环境 
<br>
需要会mybatis, 相信大家都会, 即使不会提供了demo, 基本的操作没啥问题 
<br>
py-mybatis的原理是用python包装了一层mybatis, 所以只要你会mybatis 就会使用py-mybatis
<br>
<br>
<br>


### 关于其他高级使用方法的说明
1 如何使用其他数据库?
<br>
因为使用的mybatis, 所以只要mybatis支持, 当前项目就支持, 但是值得注意的是, 我没有依赖除mysql之外的驱动, 所以你需要下载对应的驱动jar包放到配置目录下,因为配置目录也是一个classpath;
<br>
<br>
2 其他高级动态sql的使用
<br>
mybatis支持很多动态sql, 高级的用法等, 这个看mybatis官网文档就行, https://mybatis.org/mybatis-3/zh/index.html
<br>
<br>
2 感觉使用到了java很鸡肋
<br>
这需要根据你自身的情况, 我目前没有这个感觉, 而且java的加持你的查询会有一定提升, 我目前也是把他当做工具来用, 如果你也是需要一个工具, 可以尝试尝试, 相信我 你不会失望!



<br>
<br>
<br>
<br>
<br>


