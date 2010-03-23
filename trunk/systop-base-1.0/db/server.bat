@echo off
"java" -classpath ../lib/hsqldb-1.8.0.7/hsqldb-1.8.0.7.jar org.hsqldb.Server -database.0 "%userprofile%\database\db" -dbname.0 db 
@echo on
