package com.orderovation.order.ui;

import com.orderovation.order.application.OrderApplicationService;
import com.orderovation.order.infrastructure.exception.ContextAdapterException;
import com.orderovation.order.ui.dto.Rsp;
import com.orderovation.order.ui.dto.SubmitOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author devin
 */
@RestController
public class OrderRestController {

    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Rsp test() {
        return Rsp.success(restTemplate.getForEntity("http://ORGANIZATION-SERVICE/hello", String.class).getBody());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Rsp findAll() {
        return Rsp.success(orderApplicationService.findAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Rsp initiate(@RequestBody SubmitOrderDTO submitOrderDTO) throws Exception {
        orderApplicationService.initiate(submitOrderDTO);
        return Rsp.success(orderApplicationService.findAll());
    }
}
