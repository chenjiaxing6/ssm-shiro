package cn.edu.neusoft.service;

import cn.edu.neusoft.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-01 20:31
 */
public interface PermissionService {

    List<String> getAllResources();

    void addPermission(Permission permission);

    List<String> getAllPermissionsByUserId(Long id);

    List<Permission> getPermissionListByPage(Map queryMap);

    int getPermissonCount();

    List<Long> getPermissionsIdByRoleId(Long id);
}
