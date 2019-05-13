package cn.edu.neusoft.service;

import cn.edu.neusoft.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-02 13:45
 */
public interface RoleService {
    List<String> getRoleByUserId(Long id);

    List<Role> getRoleList();

    int getCount();

    List<Role> getRoleListByPage(Map queryMap);

    void addRoleAndPermission(Role role,List<String> permissions);

    void deleteById(Long id);

    Role getRoleById(Long id);

    void updateRoleAndPermission(Role role, List<String> permissionArr);

    void deleteAllRole(String[] ids);
}
