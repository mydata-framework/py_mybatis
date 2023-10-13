pip uninstall py-mybatis

cd py_mybatis_java_module

mvn clean

mvn package

cd ..

mv ./py_mybatis_java_module/target/py_mybatis_java_module-0.0.1-SNAPSHOT.jar ./py_mybatis_java_lib/py_mybatis_java_module-0.0.1-SNAPSHOT.jar

rm -rf ./dist

rm -rf ./py_mybatis.egg-info

rm -rf ./__pycache__

python  setup.py  sdist

cd dist

pip install py_mybatis-0.0.3.tar.gz

