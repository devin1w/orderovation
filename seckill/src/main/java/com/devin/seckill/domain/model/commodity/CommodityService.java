package com.devin.seckill.domain.model.commodity;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommodityService {

    public String nextIdentity() {
        return UUID.randomUUID().toString();
    }

    public Commodity addNewCommodity(String name, String description) {
        return new Commodity(nextIdentity(), name, 0, 0, 0);
    }
}
