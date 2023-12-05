#!/bin/sh
SCRIPT_DIR="${BASH_SOURCE-$0}"
SCRIPT_DIR="$(dirname "${SCRIPT_DIR}")"
# shellcheck disable=SC2164
SERVICE_HOME="$(cd "${SCRIPT_DIR}"; pwd)"

mvn clean package -Pdevelopment -Dmaven.test.skip=true

cd $SERVICE_HOME/target/ || exit
SERVICE_HOME=$SERVICE_HOME/target/
pwd
JAR_NAME=`ls  *jar`
  echo "JAR_NAME = ${JAR_NAME}"

chmod 755 $JAR_NAME

if [ -z "${JAR_NAME}" -o "$JAR_NAME" == " " ];then
  echo "未找到可执行的jar"
  exit 1
fi

pid=`ps -ef |grep ${JAR_NAME} |grep -v grep|awk '{print $2}'`
if [ -n "${pid}" ]; then
kill -9 $pid
 echo "已杀死 $pid"
fi

#gc log
GC_LOG="$SERVICE_HOME/gc.$(date "+%Y%m%d%H%M%S.%N").log"

JVM_OPTS="-verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$GC_LOG -XX:CMSInitiatingOccupancyFraction=40 -XX:+UseCMSInitiatingOccupancyOnly -XX:+CMSClassUnloadingEnabled -XX:+DisableExplicitGC -XX:+PrintPromotionFailure -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$SERVICE_HOME/oom.dump"

nohup java -jar $JVM_OPTS ${SERVICE_HOME}/${JAR_NAME} > ${SERVICE_HOME}/business.log &ps
echo  "turbo-example 服务启动完成..."
echo  "测试地址: http://localhost:8080/test/execute"
echo  "数据地址: http://localhost:8080/h2"
#tail -f ${SERVICE_HOME}/business.log