package com.devin.seckill.domain.model.commodity;

public interface CommodityCacheRepository {
    Integer findStockById(String commodityId);

    Boolean findCustomerByCommodityId(String commodityId, String customerId);

    void decreaseStockById(String commodityId);

    void addCustomerByCommodityId(String commodityId, String customerId);
}
