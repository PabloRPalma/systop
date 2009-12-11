echo [INFO] Copy dependencies to web/WEB-INF/lib.
set local_driver=%cd:~0,2%
set local_path=%cd%

set exec_path=%0
set exec_path=%exec_path:~0,-13%"
set exec_driver=%exec_path:~1,2%

%exec_driver%
cd %exec_path%/..

call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime
call mvn dependency:copy-dependencies -DoutputDirectory=web/WEB-INF/lib  -DincludeScope=runtime

%local_driver%
cd %local_path%

pause