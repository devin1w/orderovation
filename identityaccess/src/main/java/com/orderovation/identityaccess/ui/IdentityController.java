package com.orderovation.identityaccess.ui;

import com.orderovation.identityaccess.application.IdentityApplicationService;
import com.orderovation.identityaccess.ui.dto.Rsp;
import com.orderovation.identityaccess.ui.dto.UserDTO;
import com.orderovation.identityaccess.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author devin
 */
@RestController
public class IdentityController {

    @Autowired
    private IdentityApplicationService identityApplicationService;

    @RequestMapping(value = "/user/principal/name/{userName}", method = RequestMethod.GET)
    public User findPrincipalByUsername(@PathVariable String userName) throws Exception {
        return identityApplicationService.findByUsername(userName);
    }

    @RequestMapping(value = "/user/id/{userId}", method = RequestMethod.GET)
    public UserDTO getUserDetail(@PathVariable String userId) throws Exception {
        return identityApplicationService.getUserDetail(userId);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Rsp test() {
        return Rsp.success("Hello!");
    }

    @RequestMapping(value = "/search/user/{name}", method = RequestMethod.GET)
    public Rsp searchMember(@PathVariable String name) throws Exception {
        return Rsp.success(identityApplicationService.searchMemberLikeName(name));
    }

    @RequestMapping(value = "/admin/id/{id}", method = RequestMethod.GET)
    public Rsp findAdmin(@PathVariable String id) throws Exception {
        return Rsp.success(identityApplicationService.findAdmin(id));
    }
}
