package com.devin.seckill.infrastructure;

import com.devin.seckill.application.LockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 单机版
 * @author devin
 */
@Component
public class RedisBlockLock implements RedisLock, MessageListener {

    private static final int LOCK_TIMEOUT = 800;
    private static final int ACQUIRE_TIMEOUT = 1000;

    private HashMap<String, Object> blockers = new HashMap<>(16);
    private Thread currentThread;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean lock(String key, String value) throws LockException {
        long startTime = System.nanoTime();
        long interval = TimeUnit.MILLISECONDS.toNanos(ACQUIRE_TIMEOUT);
        try {
            do {
                Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
                if (aBoolean != null && aBoolean) {
                    blockers.putIfAbsent(key, key);
                    currentThread = Thread.currentThread();
                    return true;
                }
                Object lock = blockers.get(key);
                if (lock == null) {
                    throw new IllegalStateException();
                }
                synchronized (lock) {
                    lock.wait();
                }
            } while (System.nanoTime() - startTime < interval);
        } catch (Exception e) {
            unlock(key, value);
            throw new LockException("Acquire lock error");
        }
        return false;
    }

    @Override
    public void unlock(String key, String value) throws LockException {
        if (currentThread == Thread.currentThread()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                    "then return redis.call('del', KEYS[1]) " +
                    "else return 0 end";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setScriptText(script);
            redisScript.setResultType(Long.class);

            Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
            if (result == null) {
                throw new LockException("Release lock error");
            }
            redisTemplate.convertAndSend("seckill", key);
            System.out.println("[Unlock]\t" + result);
        }
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Object lock = blockers.get(new String(message.getBody()));
        if (lock != null) {
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }
}
