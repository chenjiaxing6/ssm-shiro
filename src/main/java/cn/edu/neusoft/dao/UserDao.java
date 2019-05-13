package cn.edu.neusoft.dao;

import cn.edu.neusoft.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-04-30 15:15
 */
@Repository
public interface UserDao {

    List<User> findAll();

    User getUserByUsername(String username);

    List<User> getUserByQueryMap(Map queryMap);

    int getUserCount(Map queryMap);

    void deleteById(Long id);

    void addUser(User user);

    User getUserById(Long id);

    void updateUser(User user);
}
