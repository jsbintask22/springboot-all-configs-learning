package cn.jsbintask.springbootallconfigs.controller;

import cn.jsbintask.springbootallconfigs.domain.User;
import cn.jsbintask.springbootallconfigs.domain.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 17:10
 */
@RestController
public class UserController {
    @Resource
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
