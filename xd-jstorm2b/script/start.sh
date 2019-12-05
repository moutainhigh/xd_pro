#!/bin/bash
BASE=`pwd`
basedir=/home/work/xiaodou
baseop=xd-jstorm2b
DIR_TMP=$basedir/.runtime/$baseop
PID_FILE=$DIR_TMP/pid.file
RETVAL=0
mkdir -p $DIR_TMP

function status
{
    PID=`cat $PID_FILE 2>/dev/null`
    if [ -f $PID_FILE ] && [ "$PID" != "" ] && [  -d /proc/$PID ];then
        echo "Instance is [RUNNING]."
        RETVAL=0
    else
        echo "Instance is [STOPPED]."
        RETVAL=1
    fi
}

function start
{
    status > /dev/null 2>&1
    if [ $RETVAL -ne 0 ];then
        nohup /home/zkjs/jstorm-2.1.1/bin/jstorm jar $BASE/xd-jstorm2b.jar com.xiaodou.logmonitor.MainTopology > $BASE/myout.file 2>&1 &
        echo $! > $PID_FILE
        echo "Instance is [STARTED]."
        RETVAL=$?
    else
        echo "ERR: Instance is already running."
        exit 1
    fi
}

function stop
{
    if [ -f $PID_FILE ];then
        cat $PID_FILE | xargs -n 1 kill -9
    fi
    echo "Instance is [STOPPED]."
    RETVAL=$?
}


#See how we called
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart|reload)
        stop
        sleep 6
        start
        RETVAL=$?
        ;;
    status)
        status
        RETVAL=$?
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart|reload|status}"
        exit 1
esac

exit $RETVAL
