package cn.edu.neusoft.service.impl;

import cn.edu.neusoft.dao.PermissionDao;
import cn.edu.neusoft.entity.Permission;
import cn.edu.neusoft.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-01 20:31
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<String> getAllResources() {
        return permissionDao.getAllResources();
    }

    @Override
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }

    @Override
    public List<String> getAllPermissionsByUserId(Long id) {
        return permissionDao.getAllPermissionsByUserId(id);
    }

    @Override
    public List<Permission> getPermissionListByPage(Map queryMap) {
        return permissionDao.getPermissionListByPage(queryMap);
    }

    @Override
    public int getPermissonCount() {
        return permissionDao.getPermissonCount();
    }

    @Override
    public List<Long> getPermissionsIdByRoleId(Long id) {
        return permissionDao.getPermissionsIdByRoleId(id);
    }
}
