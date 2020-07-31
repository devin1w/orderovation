package com.devin.seckill.infrastructure;

import com.devin.seckill.application.LockException;

public interface RedisLock {
    boolean lock(String key, String value) throws LockException;

    void unlock(String key, String value) throws LockException;
}
