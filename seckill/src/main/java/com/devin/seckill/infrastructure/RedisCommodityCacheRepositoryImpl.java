package com.devin.seckill.infrastructure;

import com.devin.seckill.domain.model.commodity.CommodityCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCommodityCacheRepositoryImpl implements CommodityCacheRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer findStockById(String commodityId) {
        return Integer.parseInt((String) redisTemplate.opsForValue().get("COMMODITY_STOCK_" + commodityId));
    }

    @Override
    public Boolean findCustomerByCommodityId(String commodityId, String customerId) {
        return redisTemplate.opsForSet().isMember("COMMODITY_CUSTOMER_" + commodityId, customerId);
    }

    @Override
    public void decreaseStockById(String commodityId) {
        redisTemplate.opsForValue().decrement("COMMODITY_STOCK_" + commodityId, 1);
    }

    @Override
    public void addCustomerByCommodityId(String commodityId, String customerId) {
        redisTemplate.opsForSet().add("COMMODITY_CUSTOMER_" + commodityId, customerId);
    }
}
