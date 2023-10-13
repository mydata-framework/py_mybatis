

pip uninstall py-mybatis

cd py_mybatis_java_module

mvn clean

mvn package

cd ..

mv ./py_mybatis_java_module/target/py_mybatis_java_module-0.0.1-SNAPSHOT.jar ./py_mybatis_java_lib/py_mybatis_java_module-0.0.1-SNAPSHOT.jar

rm -rf ./dist

rm -rf ./py_mybatis.egg-info

rm -rf ./__pycache__


## 然后移除 py_mybatis_demos py_mybatis_java_module 目录进行后续