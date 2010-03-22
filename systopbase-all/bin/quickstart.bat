@echo off
echo "[INFO] 确保设置PATH系统变量到maven2.0.9或以上版本的bin目录."

echo [Step 1] 安装SpringSide3 core 和archetypes到 本地Maven仓库.
cd ..\
call mvn clean install

echo [Step 2] 执行tools/database/start-db.bat 以Standalone形式启动Derby数据库
cd tools\database
start start-db.bat
cd ..\..\

echo [Step 3] 执行mini-web中的sql初始化示例数据库
cd tools\database
call mvn compile
cd ..\..\

echo [Step 4] 请将tools/configures/tomcat/conf/tomcat-user.xml 复制到你的tomcat目录，然后手工启动tomcat(端口为8080)，完成后请按任意键继续
pause


echo [Step 5] 编译、测试、打包、部署mini-web项目到tomcat，访问路径为http://localhost:8080/mini-web
cd examples\mini-web
call mvn tomcat:undeploy
call mvn clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 6] 编译、测试、打包、部署mini-service项目到tomcat，http://localhost:8080/mini-service
cd examples\mini-service
call mvn tomcat:undeploy
call mvn clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 7] 编译、测试、打包、部署mini-service项目到tomcat，http://localhost:8080/extreme-web
cd examples\extreme-web
call mvn tomcat:undeploy
call mvn clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 8] 启动IE浏览上述三个项目 .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/extreme-web

echo [INFO] SpringSide3.0 快速启动完毕.
pause


