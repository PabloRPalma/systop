@echo off
if exist "..\tools" goto begin
echo [INFO] tools目录不存在，请下载all-in-one版本
goto end
:begin
set MAVEN_BAT=%cd%\..\tools\maven\apache-maven-2.0.9\bin\mvn.bat
cd ..\

echo [Step 1] 执行tools/nexus/nexus-webapp-1.1.0/bin/jsw/windows-x86-32/Nexus.bat,启动Nexus Maven私服
start tools\nexus\nexus-webapp-1.1.0\bin\jsw\windows-x86-32\Nexus.bat

echo [Step 2] 执行tools/database/start-db.bat 以Standalone形式启动Derby数据库
cd tools\database
start start-db.bat
cd ..\..\

echo [Step 3] 执行tools/tomcat/apache-tomcat-6.0.18/bin/startup.bat 启动Tomcat服务器
cd  tools\tomcat\apache-tomcat-6.0.18\bin\
start startup.bat
cd ..\..\..\..\

echo [Step 4] 执行mini-web中的sql初始化示例数据库
cd tools\database
call %MAVEN_BAT% compile
cd ..\..\

echo [Step 5] 安装SpringSide3 core 和archetypes到 本地Maven仓库.
call %MAVEN_BAT% clean install
 
echo [Step 6] 编译、测试、打包、部署mini-web项目到tomcat，访问路径为http://localhost:8080/mini-web
cd examples\mini-web
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 7] 编译、测试、打包、部署mini-service项目到tomcat，http://localhost:8080/mini-service
cd examples\mini-service
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 8] 编译、测试、打包、部署mini-service项目到tomcat，http://localhost:8080/extreme-web
cd examples\extreme-web
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 9] 启动IE浏览上述三个项目 .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/extreme-web

echo [INFO] SpringSide3.0 快速启动完毕.
:end
pause


