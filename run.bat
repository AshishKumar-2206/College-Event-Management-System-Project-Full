@echo off

set JAR=mysql-connector-j-9.6.0\mysql-connector-j-9.6.0\mysql-connector-j-9.6.0.jar

javac -cp ".;%JAR%" Main.java db\*.java model\*.java dao\*.java ui\*.java util\*.java

if %errorlevel% neq 0 (
    echo Compilation Failed!
    pause
    exit
)

java -cp ".;%JAR%" Main

pause