@echo off
echo "[INFO] ȷ������PATHϵͳ������maven2.0.9�����ϰ汾��binĿ¼."

echo [Step 1] ��װSpringSide3 core ��archetypes�� ����Maven�ֿ�.
cd ..\
call mvn clean install

echo [Step 2] ִ��tools/database/start-db.bat ��Standalone��ʽ����Derby���ݿ�
cd tools\database
start start-db.bat
cd ..\..\

echo [Step 3] ִ��mini-web�е�sql��ʼ��ʾ�����ݿ�
cd tools\database
call mvn compile
cd ..\..\

echo [Step 4] �뽫tools/configures/tomcat/conf/tomcat-user.xml ���Ƶ����tomcatĿ¼��Ȼ���ֹ�����tomcat(�˿�Ϊ8080)����ɺ��밴���������
pause


echo [Step 5] ���롢���ԡ����������mini-web��Ŀ��tomcat������·��Ϊhttp://localhost:8080/mini-web
cd examples\mini-web
call mvn tomcat:undeploy
call mvn clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 6] ���롢���ԡ����������mini-service��Ŀ��tomcat��http://localhost:8080/mini-service
cd examples\mini-service
call mvn tomcat:undeploy
call mvn clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 7] ���롢���ԡ����������mini-service��Ŀ��tomcat��http://localhost:8080/extreme-web
cd examples\extreme-web
call mvn tomcat:undeploy
call mvn clean test war:exploded tomcat:exploded
cd ..\..\

echo [Step 8] ����IE�������������Ŀ .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/extreme-web

echo [INFO] SpringSide3.0 �����������.
pause


