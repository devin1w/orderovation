package com.devin.seckill.infrastructure;

import com.devin.seckill.application.LockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//@Component
public class RedisSpinLock implements RedisLock {

    private static final int LOCK_TIMEOUT = 2 * 1000;
    private static final int ACQUIRE_TIMEOUT = 4 * 1000;
    private static final int POLL_INTERVAL = 3;

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
                    return true;
                }
                Thread.sleep(POLL_INTERVAL, new Random().nextInt(30));
            } while (System.nanoTime() - startTime < interval);
        } catch (Exception e) {
            throw new LockException("Acquire lock error");
        }
        return false;
    }

    @Override
    public void unlock(String key, String value) throws LockException {
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
        System.out.println("[Unlock]\t" + result);
    }
}
