@echo off
if exist "..\tools" goto begin
echo [INFO] toolsĿ¼�����ڣ�������all-in-one�汾
goto end
:begin
set MAVEN_BAT=%cd%\..\tools\maven\apache-maven-2.0.9\bin\mvn.bat
cd ..\

echo [Step 1] ִ��tools/nexus/nexus-webapp-1.1.0/bin/jsw/windows-x86-32/Nexus.bat,����Nexus Maven˽��
start tools\nexus\nexus-webapp-1.1.0\bin\jsw\windows-x86-32\Nexus.bat

echo [Step 2] ִ��tools/database/start-db.bat ��Standalone��ʽ����Derby���ݿ�
cd tools\database
start start-db.bat
cd ..\..\

echo [Step 3] ִ��tools/tomcat/apache-tomcat-6.0.18/bin/startup.bat ����Tomcat������
cd  tools\tomcat\apache-tomcat-6.0.18\bin\
start startup.bat
cd ..\..\..\..\

echo [Step 4] ִ��mini-web�е�sql��ʼ��ʾ�����ݿ�
cd tools\database
call %MAVEN_BAT% compile
cd ..\..\

echo [Step 5] ��װSpringSide3 core ��archetypes�� ����Maven�ֿ�.
call %MAVEN_BAT% clean install
 
echo [Step 6] ���롢���ԡ����������mini-web��Ŀ��tomcat������·��Ϊhttp://localhost:8080/mini-web
cd examples\mini-web
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 7] ���롢���ԡ����������mini-service��Ŀ��tomcat��http://localhost:8080/mini-service
cd examples\mini-service
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 8] ���롢���ԡ����������mini-service��Ŀ��tomcat��http://localhost:8080/extreme-web
cd examples\extreme-web
call %MAVEN_BAT% tomcat:undeploy
call %MAVEN_BAT% clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 9] ����IE�������������Ŀ .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/extreme-web

echo [INFO] SpringSide3.0 �����������.
:end
pause


