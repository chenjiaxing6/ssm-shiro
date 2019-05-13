package cn.edu.neusoft.service;

import cn.edu.neusoft.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-04-30 15:14
 */
public interface UserService {
    List<User> findAll();

    User getUserByUsername(String username);

    List<User> getUserByQueryMap(Map queryMap);

    int getUserCount(Map queryMap);

    void deleteById(Long id);

    void addUser(User user,List<String> roleList);

    User getUserById(Long id);

    void updateUser(User user,List<String> roleList);
}
