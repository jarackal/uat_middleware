#!/bin/bash

uat_user="uat"
uat_home_dir="/home/$uat_user"
uat_log_dir="/var/log/$uat_user"
uat_var_dir="/var/$uat_user"

#
# set umask
#

umask 002

#
# uat_app_service
#

function uat_app_service() {
    local action=
    local app=
    local interpreter=
    local parameters=
    local user=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:c:i:p:u:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            c)
                action="$OPTARG"
            ;;

            i)
                interpreter="$OPTARG"
            ;;

            p)
                parameters="$OPTARG"
            ;;

            u)
                user="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$action" ] || [ -z "$app" ] || [ -z "$interpreter" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <app> -c <pid|restart|start|stop|status> -i <interpreter> [-p <parameters>] [-u <user>]"
    else
        if [ -z "$user" ]; then
            user=$app
        fi

        case "$action" in
            pid)
                uat_app_service_pid -a "$app" -i "$interpreter" -p "$parameters" -u "$user"
            ;;

            restart)
                uat_app_service_restart -a "$app" -i "$interpreter" -p "$parameters" -u "$user"
            ;;

            run)
                uat_app_service_run -a "$app" -i "$interpreter" -p "$parameters" -u "$user"
            ;;

            start)
                uat_app_service_start -a "$app" -i "$interpreter" -p "$parameters" -u "$user"
            ;;

            status)
                uat_app_service_status -a "$app" -i "$interpreter" -p "$parameters" -u "$user"
            ;;

            stop)
                uat_app_service_stop -a "$app" -i "$interpreter" -p "$parameters" -u "$user"
            ;;

            *)
                echo "Invalid action: $action"
            ;;
        esac
    fi

    OPTIND=$optind
}

#
# uat_app_service_check
#

function uat_app_service_check() {
    local action=
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
        echo "Usage: $0 -a <action> -e <enviroment>"
        exit
    else
        case $action in
            pid|restart|status|start|stop)
            ;;

            *)
                echo "Invalid action: $action"
                exit
            ;;
        esac

        case $environment in
            development|production|qa|staging|"test")
            ;;

            *)
                echo "Invalid environment: $environment"
                exit
            ;;
        esac
    fi

    OPTIND=$optind
}

#
# uat_app_service_classpath
#

function uat_app_service_classpath() {
    local dir=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":d:" option; do
        case $option in
            d)
                dir="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$dir" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -d <dir>"
    elif [ ! -d "$dir" ]; then
        echo "Directory $dir does not exist"
    else
        local classpath=`find $dir -maxdepth 1 -name "*.jar" | sort | xargs -I {} echo -n {}":"`
        local app_dir=`uat_app_service_dir`

        echo "$app_dir/web:$classpath:$app_dir/resources"
    fi

    OPTIND=$optind
}

#
# uat_app_service_debug_port
#

function uat_app_service_debug_port() {
    local app=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$app" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -A <app>"
    else
        app_uid=`id -u $app`
        echo 1$app_uid
    fi

    OPTIND=$optind
}

#
# uat_app_service_dir
#

function uat_app_service_dir() {
   local current_dir=`dirname $0`
   local app_dir=`cd $current_dir &> /dev/null; pwd -P`

   echo $app_dir
}

#
# uat_app_service_pid
#

function uat_app_service_pid() {
    local app=
    local interpreter=
    local parameters=
    local user=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:i:p:u:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            i)
                interpreter="$OPTARG"
            ;;

            p)
                parameters="$OPTARG"
            ;;

            u)
                user="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$app" ] || [ -z "$interpreter" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <app> -i <interpreter> [-p <parameters>] [-u <user>]"
    else
        if [ -z "$user" ]; then
            user=$app
        fi

        local pid_file="$uat_log_dir/$app/$app.pid"

        if [ -f "$pid_file" ]; then
            local pid=`cat $pid_file`

            if ps -p $pid 2>&1 >/dev/null; then
                echo $pid
            fi
        else
            pgrep -u $user `basename $interpreter` | grep $app | head -n1
        fi
    fi

    OPTIND=$optind
}

#
# uat_app_service_restart
#

function uat_app_service_restart() {
    local app=
    local interpreter=
    local parameters=
    local user=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:i:p:u:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            i)
                interpreter="$OPTARG"
            ;;

            p)
                parameters="$OPTARG"
            ;;

            u)
                user="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$app" ] || [ -z "$interpreter" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <app> -i <interpreter> [-p <parameters>] [-u <user>]"
    else
        uat_app_service_stop "$@" && uat_app_service_start "$@"
    fi

    OPTIND=$optind
}

#
# uat_app_service_start
#

function uat_app_service_run() {
    local app=
    local interpreter=
    local parameters=
    local invalid_opt=
    local user=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:i:p:u:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            i)
                interpreter="$OPTARG"
            ;;

            p)
                parameters="$OPTARG"
            ;;

            u)
                user="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$app" ] || [ -z "$interpreter" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <app> -i <interpreter> [-p <parameters>] [-u <user>]"
    else
        local app_dir=`uat_app_service_dir`

        if [ -z "$user" ]; then
            user=$app
        fi

        sudo -u $user bash -c "cd $app_dir; exec $interpreter $parameters"
    fi

    OPT_IND=$optind
}


#
# uat_app_service_start
#

function uat_app_service_start() {
    local app=
    local interpreter=
    local parameters=
    local invalid_opt=
    local user=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:i:p:u:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            i)
                interpreter="$OPTARG"
            ;;

            p)
                parameters="$OPTARG"
            ;;

            u)
                user="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$app" ] || [ -z "$interpreter" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <app> -i <interpreter> [-p <parameters>] [-u <user>]"
    else
        local pid=`uat_app_service_pid "$@"`

        if [ -n "$pid" ]; then
            echo "$app is already running with pid $pid"
        else
            if [ -z "$user" ]; then
                user=$app
            fi

            local app_dir=`uat_app_service_dir`
            local pid_file="$uat_log_dir/$app/$app.pid"
            local stderr_log="$uat_log_dir/$app/${app}_stderr.log"
            local stdout_log="$uat_log_dir/$app/${app}_stdout.log"

            sudo -u $user nohup &> /dev/null bash -c "cd $app_dir; exec $interpreter $parameters > $stdout_log 2> $stderr_log" &
            local pid="$!"

            echo "$pid" > $pid_file
            local start_pid=`uat_app_service_pid "$@"`

            if [ -n "$start_pid" ]; then
                echo "started $app with pid $start_pid"
            else
                echo "could not start $app"
            fi
        fi
    fi

    OPT_IND=$optind
}

#
# uat_serivice_status
#

function uat_app_service_status() {
    local app=
    local interpreter=
    local parameters=
    local user=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:i:p:u:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            i)
                interpreter="$OPTARG"
            ;;

            p)
                parameters="$OPTARG"
            ;;

            u)
                user="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$app" ] || [ -z "$interpreter" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <app> -i <interpreter> [-p <parameters>] [-u <user>]"
    else
        local pid=`uat_app_service_pid "$@"`

        if [ -n "$pid" ]; then
            echo "$app is running with pid $pid"
        else
            echo "$app is not running"
        fi
    fi

    OPTIND=$optind
}

#
# uat_app_service_stop
#

function uat_app_service_stop() {
    local app=
    local interpreter=
    local parameters=
    local user=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:i:p:u:" option; do
        case $option in
            a)
                app="$OPTARG"
            ;;

            i)
                interpreter="$OPTARG"
            ;;

            p)
                parameters="$OPTARG"
            ;;

            u)
                user="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$app" ] || [ -z "$interpreter" ] || [ -n "$invalid_opt" ]; then
        echo "Usage: $0 -a <app> -i <interpreter> [-p <parameters>] [-u user]"
    else
        local pid=`uat_app_service_pid "$@"`

        if [ -z "$pid" ]; then
            echo "$app is not running"
        else
            kill $pid
            sleep 5

            local stop_pid=`uat_app_service_pid "$@"`

            if [ -z "$stop_pid" ]; then
                local pid_file="$uat_log_dir/$app/$app.pid"

                echo "stopped $app with pid $pid"
                rm -rf $pid_file
            else
                echo "could not stop $app with pid $pid"
            fi
        fi
    fi

    OPTIND=$optind
}
