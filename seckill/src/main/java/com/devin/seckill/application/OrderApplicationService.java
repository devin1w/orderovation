package com.devin.seckill.application;

import com.devin.seckill.domain.model.DomainEventPublisher;
import com.devin.seckill.domain.model.commodity.Commodity;
import com.devin.seckill.domain.model.commodity.CommodityCacheRepository;
import com.devin.seckill.domain.model.commodity.CommodityRepository;
import com.devin.seckill.domain.model.order.*;
import com.devin.seckill.infrastructure.RedisLock;
import com.devin.seckill.infrastructure.RedisSpinLock;
import com.devin.seckill.ui.dto.BuyFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderApplicationService {

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CommodityCacheRepository commodityCacheRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void buyCommodity(BuyFormDTO buyFormDTO) throws Exception {

        long start = System.currentTimeMillis();

        String customerId = buyFormDTO.getCustomerId();
        String commodityId = buyFormDTO.getId();

        String requestId = UUID.randomUUID().toString();
        if (!redisLock.lock(commodityId, requestId)) {
            long end = System.currentTimeMillis();
            System.out.println("[Busy]\twhole: " + (end - start) + ",\tlock: " + (end - start) +
                    ",\tprocess: 0,\trelease: 0");
            throw new LockException("System busy");
        }
        long lock = System.currentTimeMillis();

        Integer stock = commodityCacheRepository.findStockById(commodityId);
        if (stock <= 0) {
            long process = System.currentTimeMillis();
            redisLock.unlock(commodityId, requestId);
            long end = System.currentTimeMillis();
            System.out.println("[Sold out]\twhole: " + (end - start) + ",\tlock: " + (lock - start) +
                    ",\tprocess: " + (process - lock) + ",\trelease: " + (end - process) + ", \t" + System.currentTimeMillis());
            throw new BusiException("Goods[" + commodityId + "] are sold out");
        }

        Boolean hasBought = commodityCacheRepository.findCustomerByCommodityId(commodityId, customerId);
        if (hasBought) {
            long process = System.currentTimeMillis();
            redisLock.unlock(commodityId, requestId);
            long end = System.currentTimeMillis();
            System.out.println("[Bought]\twhole: " + (end - start) + ",\tlock: " + (lock - start) +
                    ",\tprocess: " + (process - lock) + ",\trelease: " + (end - process));
            throw new BusiException("Customer[" + customerId + "] has bought commodity[" + commodityId + "]");
        }

        commodityCacheRepository.decreaseStockById(commodityId);
        commodityCacheRepository.addCustomerByCommodityId(commodityId, customerId);
        long process = System.currentTimeMillis();
        redisLock.unlock(commodityId, requestId);
        DomainEventPublisher.publisher().publishEventAsync(new OrderToCreateEvent(customerId, commodityId));
        long end = System.currentTimeMillis();
        System.out.println("[Success]\twhole: " + (end - start) + ",\tlock: " + (lock - start) +
                ",\tprocess: " + (process - lock) + ",\trelease: " + (end - process));
    }

//    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.SERIALIZABLE)
//    public void buyCommodity(BuyFormDTO buyFormDTO) throws Exception {
//
//        long start = System.currentTimeMillis();
//
//        String customerId = buyFormDTO.getCustomerId();
//        String id = buyFormDTO.getId();
//
//        System.out.println("[Service][" + customerId + "]\tStart");
//        Commodity commodity = commodityRepository.findById(id).orElseThrow(() -> new BusiException("Commodity[" + id + "] does not exist"));
//        Integer stock = commodity.getStock();
//        if (stock <= 0) {
//            long end = System.currentTimeMillis();
//            System.out.println("[Sold out][" + customerId + "]\twhole: " + (end - start) +
//                    ",\tprocess: " + (end - start) + ", \t" + System.currentTimeMillis());
//            throw new BusiException("Goods[" + id + "] are sold out");
//        }
//
//        long count = orderRepository.count(new OrderOfCustomerAndCommodity(customerId, id));
//        if (count > 0) {
//            long end = System.currentTimeMillis();
//            System.out.println("[Bought]\twhole: " + (end - start) +
//                    ",\tprocess: " + (end - start));
//            throw new BusiException("Customer[" + customerId + "] has bought commodity[" + id + "]");
//        }
//        commodity.setStock(stock - 1);
//        Order order = orderService.newOrder(customerId, id);
//
//        commodityRepository.save(commodity);
//        orderRepository.save(order);
//        long end = System.currentTimeMillis();
//        System.out.println("[Success]\twhole: " + (end - start) +
//                ",\tprocess: " + (end - start));
//    }

    @Transactional(rollbackFor = Throwable.class)
    public void createOrder(String customerId, String commodityId) {
        Order order = orderService.newOrder(customerId, commodityId);
        orderRepository.save(order);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void syncCommodityStock(String commodityId) throws BusiException {
        Integer newStock = commodityCacheRepository.findStockById(commodityId);
        Commodity commodity = commodityRepository.findById(commodityId)
                .orElseThrow(() -> new BusiException("Commodity[" + commodityId + "] does not exist"));
        commodity.setStock(newStock);
        commodityRepository.save(commodity);
    }
}
