@echo off
echo [INFO] 确保设置PATH系统变量到maven2.0.9或以上版本的bin目录.

echo [INOF] 安装SpringSide3的jar 和 archetype 到 本地Maven仓库.
cd ..\
call mvn clean install

pause


