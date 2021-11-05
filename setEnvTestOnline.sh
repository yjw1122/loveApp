#!/bin/bash
# 设置环境变量

# 使用指南：
# 1. 修改本文件中的 MEM_OPTS 和 LOGDIR 变量。
# 2. 参数： ACTIVE 配置文件标识,及环境标识。
# 3. 修改应用启动脚本，增加 "source ./setEnv.sh"，或者将本文件内容复制进应用启动脚本里.
# 4. 修改应用启动脚本，使用输出的JAVA_OTPS变量，如java -jar xxx的应用启动语句，修改为 java $JAVA_OPTS -jar xxx。

# 使用方法
usage() {
   echo "Usage: sh $0 SYSTEM_NAME PORT [ACTIVE]"
   echo "\n"
   echo "\t SYSTEM_NAME jar名称"
   echo "\t PORT 端口号"
   echo "\t ACTIVE 配置文件标识,及环境标识"
   echo "\n"
   echo "\n"
   echo "示例："
   echo "\t 启动：sh $0 isv-manager 10881 isv-open0"
}

# 判断参数
if [ $# -lt 2 ]; then
    usage
    exit 1;
fi

# 环境标识
SYSTEM_NAME=$1
PORT=$2
ACTIVE=$3

# change the jvm error log and backup gc log dir here
LOGDIR="./logs"

# 确定 'ifconfig' 命令位置
IFCONFIGEXE="/usr/sbin/ifconfig"
if [ ! -x "$IFCONFIGEXE" ]; then
    PSEXE="/bin/ifconfig"
    if [ ! -x "$IFCONFIGEXE" ]; then
      echo "Unable to locate 'ifconfig'."
      echo "Please report this message along with the location of the command on your system."
      exit 1
    fi
fi

# jmx 本机ip
JMX_HOSTNAME=$(${IFCONFIGEXE} -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:")

# 性能环境及测试环境特殊配置
if [[ $ACTIVE != *prod* ]]; then
  ## Memory Options
  MEM_OPTS="-server -Xms256m -Xmx512m"
  ## JMX Options
  JMX_OPTS="-Djava.rmi.server.hostname=${JMX_HOSTNAME} -Dcom.sun.management.jmxremote.port=2${PORT:1} -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
  ## Remote debug
  DEBUG_OPTS="-Djavax.net.debug=ssl -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=3${PORT:1}"
else
  MEM_OPTS="-server -Xms256m -Xmx512m"
fi

# 启动时预申请内存
MEM_OPTS="$MEM_OPTS -XX:+AlwaysPreTouch"

## GC Options
GC_OPTS="-XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly"

# System.gc() 使用CMS算法
GC_OPTS="$GC_OPTS -XX:+ExplicitGCInvokesConcurrent"

# CMS中的下列阶段并发执行
GC_OPTS="$GC_OPTS -XX:+ParallelRefProcEnabled -XX:+CMSParallelInitialMarkEnabled"

# 根据应用的对象生命周期设定，减少事实上的老生代对象在新生代停留时间，加快YGC速度
GC_OPTS="$GC_OPTS -XX:MaxTenuringThreshold=3"

# 如果OldGen较大，加大YGC时扫描OldGen关联的卡片，加快YGC速度，默认值256较低
GC_OPTS="$GC_OPTS -XX:+UnlockDiagnosticVMOptions -XX:ParGCCardsPerStrideChunk=1024"

## GC log Options, only for JDK7/JDK8 ##

# GC日志文件
GC_LOG_FILE=${LOGDIR}/gc-${JMX_HOSTNAME##*.}-${PORT}.log

# 备份之前的GC日志
if [ -f ${GC_LOG_FILE} ]; then
  GC_LOG_BACKUP=${LOGDIR}/gc-${JMX_HOSTNAME##*.}-${PORT}-$(date +'%Y%m%d%H%M%S').log
  echo "saving gc log ${GC_LOG_FILE} to ${GC_LOG_BACKUP}"
  mv ${GC_LOG_FILE} ${GC_LOG_BACKUP}
fi

#打印GC日志，包括时间戳，晋升老生代失败原因，应用实际停顿时间(含GC及其他原因)
GCLOG_OPTS="-Xloggc:${GC_LOG_FILE} -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintPromotionFailure -XX:+PrintGCApplicationStoppedTime"

## Optimization Options##
OPTIMIZE_OPTS="-XX:-UseBiasedLocking -XX:AutoBoxCacheMax=20000 -Djava.security.egd=file:/dev/./urandom"

## Trouble shooting Options##
SHOOTING_OPTS="-XX:+PrintCommandLineFlags -XX:-OmitStackTraceInFastThrow -XX:ErrorFile=${LOGDIR}/hs_err_${JMX_HOSTNAME##*.}-${PORT}.log"

## Other Options##
OTHER_OPTS="-Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"

## All together ##
export JAVA_OPTS="$MEM_OPTS $GC_OPTS $GCLOG_OPTS $OPTIMIZE_OPTS $SHOOTING_OPTS $OTHER_OPTS $JMX_OPTS $DEBUG_OPTS"

echo JAVA_OPTS=$JAVA_OPTS
