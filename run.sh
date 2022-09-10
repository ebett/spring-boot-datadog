#/bin/bash

JAR_FILE_NAME=api-example.jar

JAVA_EXEC_PATH=/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/bin/java

#DATA DOG
export DD_AGENT_HOST=localhost
export DD_ENV=local
export DD_SERVICE=api-example
export DD_VERSION=1.0
export DD_PROFILING_ENABLED=true
export DD_LOGS_INJECTION=true
export DD_TRACE_SAMPLE_RATE=1

# Logback variables for LogstashTcpSocketAppender see logback-spring.xml
#export DD_API_KEY=<your DD api key>
# https://docs.datadoghq.com/logs/log_collection/?tab=host#supported-endpoints
#export DD_LOG_SITE=intake.logs.datadoghq.com
#export DD_LOG_SITE_PORT=10516

JAVA_OPTIONS="${JAVA_OPTIONS} -javaagent:./agent/dd-java-agent.jar -XX:FlightRecorderOptions=stackdepth=256"

$JAVA_EXEC_PATH ${JAVA_OPTIONS} -jar build/libs/$JAR_FILE_NAME