#!/bin/bash

uat_user="uat"
uat_home_dir="/home/$uat_user"
uat_log_dir="/var/log/$uat_user"
uat_var_dir="/var/$uat_user"

umask 002

#
# uat_setup
#

function uat_setup() {
    local install=
    local apps=
    local uninstall=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts "iua:" option; do
        case $option in
            a)
                apps="$OPTARG"
            ;;

            i)
                install="install"
            ;;

            u)
                uninstall="uninstall"
            ;;

            *)
                invalid_opt="$OPTARG"
            ;;
        esac
    done

    if [ -z "$apps" ] || ( [ -z "$install" ] && [ -z "$uninstall" ] ) || [ -n "$invalid_opt" ] ; then
        echo "Usage: $0 <-a apps> <-i|-u>"
    else
        if [ -n "$uninstall" ]; then
            uat_setup_uninstall -a "$apps"
        fi

        if [ -n "$install" ]; then
            uat_setup_install -a "$apps"
        fi
    fi

    OPTIND=$optind
}

#
# uat_setup_install
#

function uat_setup_install() {
    local apps=
    local process=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:p" option; do
        case $option in
            a)
                apps="$OPTARG"
            ;;

            p)
                process="process"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$apps" ] || [ -n "$invalid_opt" ] ; then
        echo "Usage: $0 <-m apps>"
    else
        if ! id $uat_user &> /dev/null; then
            useradd -d $uat_home_dir -ms /bin/bash $uat_user
        fi

        for dir in $uat_home_dir $uat_log_dir $uat_var_dir; do
            if [ ! -d "$dir" ]; then
                mkdir $dir
            fi

            chown $uat_user:$uat_user $dir
        done
        chmod 775 $uat_log_dir $uat_var_dir
        chmod 755 $uat_home_dir

        for app in $apps; do
            if ! id $app &> /dev/null; then
                useradd -d $uat_home_dir -G $uat_user -s /bin/false $app
            fi

            for dir in $uat_home_dir/$app $uat_log_dir/$app $uat_var_dir/$app; do
                if [ ! -d "$dir" ]; then
                    mkdir $dir
                fi

                chown $app:$uat_user $dir
            done

            if [ -n "$process" ]; then
                local process_dir=$uat_var_dir/$app/process

                if [ ! -d "$process_dir" ]; then
                    mkdir $process_dir
                fi

                find $process_dir -type d | xargs chmod 775
                find $process_dir -type d | xargs chown $app:$uat_user
            fi
        done
    fi

    OPTIND=$optind
}

#
# uat_setup_unintall
#

function uat_setup_uninstall() {
    local apps=
    local invalid_opt=
    local optind=$OPTIND
    OPTIND=1

    while getopts ":a:" option; do
        case $option in
            a)
                apps="$OPTARG"
            ;;

            *)
                invalid_opt="$option"
            ;;
        esac
    done

    if [ -z "$apps" ] || [ -n "$invalid_opt" ] ; then
        echo "Usage: $0 <-m apps>"
    else
        for app in $apps; do
            userdel -f $app

            for dir in $uat_home_dir/$app $uat_log_dir/$app $uat_var_dir/$app; do
                rm -rf $dir
            done
        done
    fi

    OPTIND=$optind
}
