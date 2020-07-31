package com.devin.seckill.domain.model.order;

import com.devin.seckill.domain.model.commodity.Commodity;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author devin
 */
@Entity
@Table(name = "oo_seckill_order")
public class Order {
    @Id
    private String id;
    private String customerId;
    private String commodityId;
    private Date createDate;

    public Order() {
        this.customerId = "张三";
        this.createDate = new Date();
    }

    public Order(String id, String customerId, String commodityId, Date createDate) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setCommodityId(commodityId);
        this.setCreateDate(createDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalStateException("The id may not be set to null");
        }
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        if (StringUtils.isEmpty(customerId)) {
            throw new IllegalStateException("The customerId may not be set to null");
        }
        this.customerId = customerId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        if (StringUtils.isEmpty(commodityId)) {
            throw new IllegalStateException("The commodityId may not be set to null");
        }
        this.commodityId = commodityId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        if (createDate == null) {
            throw new IllegalStateException("The createDate may not be set to null");
        }
        this.createDate = createDate;
    }
}
