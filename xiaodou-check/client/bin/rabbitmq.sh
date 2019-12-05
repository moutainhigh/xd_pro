#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  
 
if  [[ `eval ${rabbitmq_cmd}` == 200 ]] ; then
    echo "rabbitMq is running" 
else
    #如果rabbitMq服务不是运行中，重启,等待后发送启动日志
    echo "###################rabbitmq is restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$rabbitmq_log_file
	sendMail "${local_ip} rabbitmq restart at $(date +'%Y-%m-%d %H:%M:%S')" "${local_ip} rabbitmq restart at $(date +'%Y-%m-%d %H:%M:%S')"
    eval $rabbitmq_start
    sleep 5
	if  [[ `eval ${rabbitmq_cmd}` == 200 ]] ; then
	    echo "rabbitMq restart success"
	else
		sendMail "${local_ip} rabbitmq状态异常" "${local_ip} rabbitmq状态异常，请登录服务器解决"
	fi
fi
#获取所有队列的名字和每个队列中的消息数量，存入'queueNum'数组中  
declare -A queueJson   
queueIndex=0 
for QUEUE in $(rabbitmqctl list_queues |grep -v 'Listing queues ...' | awk -F' ' '{print $1}');  
do   
    #统计每个消息队列的数量  
    queueJson[$QUEUE]=$(rabbitmqctl list_queues |grep $QUEUE | awk -F' ' '{print $2}')  
    nums=${queueJson[$QUEUE]}  
    # -ge    
    if [[ $nums -ge $maxNum ]]; then  
        #存key  
        queueName[$queueIndex]=$QUEUE  
        queueIndex=`expr $queueIndex + 1`  
    fi  
done   
#如果有异常,发送邮件  
exceptionNum=${#queueName[@]}  
if [[ $exceptionNum -gt 0 ]]; then  
    #有队列阻塞，exceptionName存放的为堵塞队列的名称,发送邮件  
    #内容  
    email_content="队列阻塞情况:"  
    for name in ${queueName[*]}  
    do  
        email_content=$email_content"\\n${name}:${queueJson[${name}]}"  
    done  
    echo "###################count at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$rabbitmq_log_file   
    echo -e $email_content  >>$rabbitmq_log_file  
    #发送邮件  
    sendMail "${local_ip}消息堆积异常"  ${email_content} 
fi   

######################end######################### 
