package com.devin.seckill.domain.model.commodity;

import com.devin.seckill.application.BusiException;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author devin
 */
@Entity
@Table(name = "oo_seckill_commodity")
public class Commodity {
    @Id
    private String id;
    private String name;
    private Integer stock;
    private Integer sale;
    private Integer version;

    public Commodity() {
    }

    public Commodity(String id, String name, Integer stock, Integer sale, Integer version) {
        this.setId(id);
        this.setName(name);
        this.setStock(stock);
        this.setSale(sale);
        this.setVersion(version);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalStateException("The name may not be set to null");
        }
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        if (stock == null) {
            throw new IllegalStateException("The stock may not be set to null");
        }
        this.stock = stock;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        if (sale == null) {
            throw new IllegalStateException("The sale may not be set to null");
        }
        this.sale = sale;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        if (version == null) {
            throw new IllegalStateException("The version may not be set to null");
        }
        this.version = version;
    }

    public void updateStock() throws BusiException {
        if (stock <= 0) {
            throw new BusiException("Goods[" + id + "] are sold out");
        }
        this.setStock(stock - 1);
        this.setSale(sale + 1);
    }
}
