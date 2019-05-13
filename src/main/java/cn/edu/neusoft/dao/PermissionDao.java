package cn.edu.neusoft.dao;

import cn.edu.neusoft.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-01 20:29
 */
@Repository
public interface PermissionDao {

    void addPermission(Permission permission);

    List<String> getAllResources();

    List<String> getAllPermissionsByUserId(Long id);

    List<Long> getPermissionsIdByRoleId(Long id);

    List<Permission> getPermissionListByPage(Map queryMap);

    int getPermissonCount();
}
