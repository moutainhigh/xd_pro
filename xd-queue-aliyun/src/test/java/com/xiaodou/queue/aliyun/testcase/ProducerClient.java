package com.xiaodou.queue.aliyun.testcase;

/***************************************************
运行示例代码的前置条件：

 通过下面两种方式可以引入依赖(任选一种)
    1、Maven方式引入依赖
        <dependency>
          <groupId>com.aliyun.openservices</groupId>
          <artifactId>ons-client</artifactId>
          <version>1.1.5</version>
        </dependency>
        
    2、下载依赖Jar包
        http://onsteam.oss-cn-hangzhou.aliyuncs.com/aliyun-ons-client-java.zip
        
 ***************************************************/

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;


public class ProducerClient {

    public static void main(String[] args) {
       Properties properties = new Properties();
       properties.put(PropertyKeyConst.ProducerId, "PID_xd_jz_regist_mes");
       properties.put(PropertyKeyConst.AccessKey, "8ykPgdET5LJD6egk");
       properties.put(PropertyKeyConst.SecretKey, "8Fbqmmarka3pQbfqUMKajSCanrUUMm");
       Producer producer = ONSFactory.createProducer(properties);
           
       //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
       producer.start();
       Message msg = new Message(
            //Message Topic
            "xd_jz_regist_mes", 
            //Message Tag,
            //可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤        
            "TagA",
            //Message Body
            //任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
            "Hello ONS".getBytes()
        );
        
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过ONS Console查询消息并补发。
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_100");
        
        //发送消息，只要不抛异常就是成功
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);

        // 在应用退出前，销毁Producer对象
        // 注意：如果不销毁也没有问题
        producer.shutdown();
    }
}
