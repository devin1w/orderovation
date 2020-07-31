package com.devin.seckill.ui;

import com.devin.seckill.application.CommodityApplicationService;
import com.devin.seckill.application.LockException;
import com.devin.seckill.application.OrderApplicationService;
import com.devin.seckill.ui.dto.BuyFormDTO;
import com.devin.seckill.ui.dto.CommodityFormDTO;
import com.devin.seckill.ui.dto.Rsp;
import com.devin.seckill.ui.dto.RspEnum;
import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @Autowired
    private CommodityApplicationService commodityApplicationService;
    @Autowired
    private OrderApplicationService orderApplicationService;

    @RequestMapping(value = "/commodity", method = RequestMethod.GET)
    public Rsp findCommodity() {
        return Rsp.success(commodityApplicationService.findCommodity());
    }

    @RequestMapping(value = "/commodity", method = RequestMethod.PUT)
    public Rsp addCommodity(@RequestBody CommodityFormDTO commodityFormDTO) {
        commodityApplicationService.addCommodity(commodityFormDTO);
        return findCommodity();
    }

    @RequestMapping(value = "/commodity", method = RequestMethod.POST)
    public Rsp updateCommodity(@RequestBody CommodityFormDTO commodityFormDTO) {
        commodityApplicationService.updateCommodity(commodityFormDTO);
        return findCommodity();
    }

    @RequestMapping(value = "/commodity/id/{id}", method = RequestMethod.DELETE)
    public Rsp deleteCommodity(@PathVariable String id) {
        commodityApplicationService.deleteCommodity(id);
        return findCommodity();
    }

    @RequestMapping(value = "/buyCommodity", method = RequestMethod.POST)
    public Rsp buyCommodity(@RequestBody BuyFormDTO buyFormDTO) throws Exception {
        System.out.println("[Controller]\t" + System.currentTimeMillis());
        try {
            orderApplicationService.buyCommodity(buyFormDTO);
        } catch (CannotAcquireLockException | LockException e) {
            return Rsp.error(RspEnum.SYS_ERROR, e.getMessage());
        } catch (MySQLTransactionRollbackException e) {
            e.printStackTrace();
            throw e;
        }
        return Rsp.success(System.currentTimeMillis());
    }

//    @RequestMapping(value = "/buyCommodity", method = RequestMethod.POST)
//    public Rsp buyCommodity(@RequestBody BuyFormDTO buyFormDTO) throws Exception {
//        System.out.println("[Controller]\t" + System.currentTimeMillis());
//        int ACQUIRE_TIMEOUT = 4 * 1000;
//        long startTime = System.currentTimeMillis();
//
//        while (System.currentTimeMillis() - startTime < ACQUIRE_TIMEOUT) {
//            try {
//                orderApplicationService.buyCommodity(buyFormDTO);
//                return Rsp.success();
//            } catch (CannotAcquireLockException e) {
//                Thread.sleep(3);
//            }
//        }
//        return Rsp.error(RspEnum.SYS_ERROR, "System busy");
//    }

}
