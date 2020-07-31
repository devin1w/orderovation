package com.orderovation.identityaccess.application;

import com.orderovation.identityaccess.domain.model.*;
import com.orderovation.identityaccess.ui.dto.UserDTO;
import com.orderovation.identityaccess.ui.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author 韦盛友
 */
@Service
public class IdentityApplicationService {

    @Autowired
    private UserRepository userRepository;

    public String nextIdentity() {
        return UUID.randomUUID().toString();
    }

    public UserDTO getUserDetail(String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusiException("用户[" + userId + "]不存在"));
        UserDTO userDTO = BeanUtil.copyObject(user, UserDTO.class);
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public User findByUsername(String username) throws Exception {
        List<User> users = userRepository.findAll(new UserWithUsernameSpecification(username));
        if (users.size() == 0) {
            throw new BusiException("用户[" + username + "]不存在");
        }
        return users.get(0);
    }

    public List<UserDTO> searchMemberLikeName(String name) throws Exception {
        List<User> userList = userRepository.findAll(new UserLikeNicknameSpecification(name));
        List<UserDTO> userDTOList = userList.stream().map((user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUsername(user.getUsername());
            userDTO.setNickname(user.getNickname());
            return userDTO;
        })).collect(Collectors.toList());
        return userDTOList;
    }

    public UserDTO findAdmin(String id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new BusiException("用户[" + id + "]不存在"));
        if (!"admin".equals(user.getUsername())) {
            throw new BusiException("管理员[" + id + "]不存在");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setNickname(user.getNickname());
        return userDTO;
    }

    public void generateTestUsers(Integer num) {
        if (num <= 0) {
            throw new IllegalStateException("num不为正数");
        }
        List<User> users = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setUserId(nextIdentity());
            user.setUsername("test");
            users.add(user);
        }
        userRepository.saveAll(users);
    }

    public void deleteTestUsers() {
        userRepository.deleteAll(userRepository.findAll(new UserWithUsernameSpecification("test")));
    }
}
