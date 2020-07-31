package com.devin.seckill.application;

import com.devin.seckill.domain.model.commodity.Commodity;
import com.devin.seckill.domain.model.commodity.CommodityRepository;
import com.devin.seckill.domain.model.commodity.CommodityService;
import com.devin.seckill.ui.dto.CommodityFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityApplicationService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private CommodityService commodityService;

    public List<Commodity> findCommodity() {
        return commodityRepository.findAll();
    }

    public void addCommodity(CommodityFormDTO commodityFormDTO) {
        String name = commodityFormDTO.getName();
        String description = commodityFormDTO.getDescription();
        Commodity commodity = commodityService.addNewCommodity(name, description);

        commodityRepository.save(commodity);
    }

    public void updateCommodity(CommodityFormDTO commodityFormDTO) {

    }

    public void deleteCommodity(String id) {
        commodityRepository.deleteById(id);
    }
}
