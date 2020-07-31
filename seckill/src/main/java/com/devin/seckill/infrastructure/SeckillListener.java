package com.devin.seckill.infrastructure;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class SeckillListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println(new String(pattern) + "主题发布：" + new String(message.getBody()));
    }
}
