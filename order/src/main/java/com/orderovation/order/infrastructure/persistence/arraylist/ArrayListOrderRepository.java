package com.orderovation.order.infrastructure.persistence.arraylist;

import com.orderovation.order.domain.model.order.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author devin
 */
//@Component
public class ArrayListOrderRepository {

    private List<Order> orders = new ArrayList<>();

//    @Override
    public List<Order> findAll() {
        return orders;
    }
}
