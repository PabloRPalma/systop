@echo off
set local_driver=%cd:~0,2%
set local_path=%cd%

set exec_path=%0
set exec_path=%exec_path:~0,-13%"
set exec_driver=%exec_path:~1,2%
%exec_driver%
cd %exec_path%
echo [INFO] ʹ��maven����pom.xml ��������jar��/lib

call mvn dependency:tree dependency:copy-dependencies -DoutputDirectory=lib 

%local_driver%
cd %local_path%
pause