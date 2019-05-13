package cn.edu.neusoft.dao;

import cn.edu.neusoft.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-02 13:32
 * 角色管理数据持久层接口
 */
@Repository
public interface RoleDao {

    List<String> getRoleByUserId(Long id);

    List<Role> getRoleList();

    int getCount();

    List<Role> getRoleListByPage(Map queryMap);

    void addRole(Role role);

    void deleteRole(Long id);

    Role getRoleById(Long id);

    void updateRole(Role role);

    void deleteAllRole(String[] ids);
}
