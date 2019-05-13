package cn.edu.neusoft.dao;

import cn.edu.neusoft.entity.RolePermission;
import org.springframework.stereotype.Repository;

/**
 * @author Chen
 * @create 2019-05-08 20:53
 */
@Repository
public interface RolePermissionDao {
    void addRolePermission(RolePermission rolePermission);

    void deleteByRoleId(Long id);

    void deleteAllByRoleId(String[] ids);
}
