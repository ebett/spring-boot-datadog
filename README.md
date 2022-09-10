# Spring boot - StatsD Datadog client
Topics

* Configure the environment variables in the script run.sh
* Install data dog agent in your computer
* Configure the agent
* Build the project
* Start Datadog agent
* Start the application

### Configure run.sh variables
Set the variables JAVA_EXEC_PATH and DD_AGENT_HOST according to your preferences

### Install de Datadog agent in your computer
Download and install the agent following this site:

https://docs.datadoghq.com/getting_started/agent

### Configure the agent
Copy the agent config file in the project to the datadog agent config directory:
- cp agent/datadog.yaml /opt/datadog-agent/etc

Or, edit the file /opt/datadog-agent/etc/datadog.yaml and  configure these properties:
```
#Create a new API key here: https://app.datadoghq.com/account/settings
api_key: <your datadog api key>

site: us5.datadoghq.com

tags:
  - env: local
  - service: api-example

apm_config:
    
    enabled: true
    
    env: local

logs_enabled: true
```

### Logs
Service configuration
```
mkdir /opt/datadog-agent/etc/conf.d/api-example.d
vim /opt/datadog-agent/etc/conf.d/api-example.d/conf.yaml
```

```
#Log section
logs:

    # - type : file (mandatory) type of log input source (tcp / udp / file)
    #   port / path : (mandatory) Set port if type is tcp or udp. Set path if type is file
    #   service : (mandatory) name of the service owning the log
    #   source : (mandatory) attribute that defines which integration is sending the log
    #   sourcecategory : (optional) Multiple value attribute. Can be used to refine the source attribute
    #   tags: (optional) add tags to each log collected

  - type: file
    path: ~/api-example.log
    service: api-example
    source: java
    sourcecategory: sourcecode
    #For multiline logs, if they start with a timestamp with format yyyy-mm-dd uncomment the below processing rule
    #log_processing_rules:
    #   - type: multi_line
    #     pattern: \d{4}\-(0?[1-9]|1[012])\-(0?[1-9]|[12][0-9]|3[01])
    #     name: new_log_start_with_date
```

### Build the project
In the root of the project run:
```
./gradlew clean build
```
### Start Datadog agent
https://docs.datadoghq.com/agent/guide/agent-commands/?tab=agentv6v7

### Start the application
```
./run.sh
```
### Additional Links
These additional references should also help you:

* [Datadog - Getting Started](https://docs.datadoghq.com/getting_started)
* [Tracing Java Applications](https://docs.datadoghq.com/tracing/trace_collection/dd_libraries/java/?code-lang=java&tab=containers)
* [Metrics](https://docs.datadoghq.com/api/latest/metrics/#submit-metrics)

#Donate
https://www.paypal.com/donate/?hosted_button_id=V2T3W6GKYTAYG
