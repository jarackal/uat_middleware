#!/bin/bash

current_dir=`dirname $0`
app_dir=`cd $current_dir &> /dev/null; pwd -P`

function uat_web_service() {
    local action=
    local app="uat_web_service"
    local environment=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:e:" option; do
        case $option in
            a)
                action="$OPTARG"
            ;;

            e)
                environment="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$action" ] || [ -z "$environment" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <restart|start|stop> -e <development|production|qa|staging>"
    else
        uat_setup_install -a $app
        uat_app_service_check -a $action -e $environment

        local interepreter="/usr/bin/java"
        local classpath=`uat_app_service_classpath -d $app_dir/lib/java`
        local library_path="$app_dir/lib/native"
        local parameters=""

        case "$environment" in
            "development")
                local debug_port=`uat_app_service_debug_port -a $app`
                parameters="-agentlib:jdwp='server=y,transport=dt_socket,address=$debug_port,suspend=n'"
            ;;
        esac

        case "$environment" in
            "production")
                parameters="$parameters \
                    -Xms512m \
                    -Xmx1024m \
                    -XX:MaxPermSize=1024m"
            ;;

            *)
                parameters="$parameters \
                    -Xms256m \
                    -Xmx512m \
                    -XX:MaxPermSize=512m"
            ;;
        esac

        parameters="$parameters \
                    -classpath $classpath \
                    -Dapp.environment=$environment \
                    -Dapp.type=$app \
                    -Djava.library.path=$library_path \
                    -Dspring.configurations=spring/$app/${app}.spring.xml \
                    -Duser.timezone=UTC \
                    -server \
                    -Xloggc:/var/log/uat/$app/${app}_gc.log \
                    -XX:+AggressiveOpts \
                    -XX:+PrintGCApplicationConcurrentTime \
                    -XX:+PrintGCApplicationStoppedTime \
                    -XX:+PrintGCDetails \
                    -XX:+PrintGCTimeStamps \
                    -XX:+UseBiasedLocking \
                    -XX:+UseParallelGC \
                    -verbose:gc \
                    com.investingchannel.uat.webservice.main.RunService"

        uat_app_service -a "$app" -c "$action" -i "$interepreter" -p "$parameters"
    fi

    OPTIND=$optind
}

source $app_dir/uat_app_service.sh
source $app_dir/uat_setup.sh
uat_web_service "$@"