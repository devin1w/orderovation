package com.devin.seckill.infrastructure;

import com.devin.seckill.application.CommodityApplicationService;
import com.devin.seckill.domain.model.commodity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 库存预热
 * @author devin
 */
@Component
public class StockLoader implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommodityApplicationService commodityApplicationService;

    @Override
    public void run(String... args) throws Exception {
        List<Commodity> commodityList = commodityApplicationService.findCommodity();

        if (CollectionUtils.isEmpty(commodityList)) {
            return;
        }

        for (Commodity commodity : commodityList) {
            redisTemplate.opsForValue().set("COMMODITY_STOCK_" + commodity.getId(), commodity.getStock());
        }
    }
}
