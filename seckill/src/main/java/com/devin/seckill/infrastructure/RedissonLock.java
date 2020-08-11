package com.devin.seckill.infrastructure;

import com.devin.seckill.application.LockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLock implements RedisLock {

    private static final int LOCK_TIMEOUT = 1000;
    private static final int ACQUIRE_TIMEOUT = 2 * 1000;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean lock(String key, String value) throws LockException {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(ACQUIRE_TIMEOUT, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        } catch (Exception e) {
            throw new LockException("Acquire lock error");
        }
    }

    @Override
    public void unlock(String key, String value) throws LockException {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
