package cn.edu.neusoft.service.impl;

import cn.edu.neusoft.dao.UserDao;
import cn.edu.neusoft.dao.UserRoleDao;
import cn.edu.neusoft.entity.User;
import cn.edu.neusoft.entity.UserRole;
import cn.edu.neusoft.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-04-30 15:14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll() ;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<User> getUserByQueryMap(Map queryMap) {
        return userDao.getUserByQueryMap(queryMap);
    }

    @Override
    public int getUserCount(Map queryMap) {
        return userDao.getUserCount(queryMap);
    }

    /**
     * 删除用户，先删除用户，在删除用户关联的角色信息
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
        userRoleDao.deleteByUserId(id);
    }

    /**
     * 添加用户，添加用户所属角色
     * @param user
     * @param roleList
     */
    @Override
    public void addUser(User user, List<String> roleList) {
        //执行添加用户，返回添加的主键
        userDao.addUser(user);
        Long userId = user.getId();

        //遍历添加的角色id
        UserRole userRole = new UserRole();
        for (String s : roleList) {
            if (s != null && s!= "") {
                userRole.setUserId(userId);
                userRole.setRoleId(Long.valueOf(s));
                userRoleDao.insertUserRole(userRole);
            }
        }
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    /**
     * 更新用户，先更新用户信息，在把用户相关的角色信息删除，添加用户角色信息
     * @param user
     * @param roleList
     */
    @Override
    public void updateUser(User user, List<String> roleList) {
        //先更新用户
        userDao.updateUser(user);
        //在删除用户相关的角色
        userRoleDao.deleteByUserId(user.getId());
        //遍历重新添加角色
        UserRole userRole = new UserRole();
        for (String s : roleList) {
            if (s != null && s!= "") {
                userRole.setUserId(user.getId());
                userRole.setRoleId(Long.valueOf(s));
                userRoleDao.insertUserRole(userRole);
            }
        }

    }
}
