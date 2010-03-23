@echo off
"java" -classpath hsqldb-1.8.0.7.jar org.hsqldb.Server -database.0 ../webapp/WEB-INF/hsqldb/db
@echo on
