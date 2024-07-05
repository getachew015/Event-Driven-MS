@ECHO OFF
rem # batch file to start kafka
rem first start kafka zookeeper service and then kafka server
echo starting zookeeper service
start call "bin\windows\zookeeper-server-start.bat config\zookeeper.properties"
timeout /t 5 /nobreak >nul
echo starting kafka servers on port 9092
start call "bin\windows\kafka-server-start.bat config\server.properties"