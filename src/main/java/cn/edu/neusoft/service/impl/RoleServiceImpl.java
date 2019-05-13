package cn.edu.neusoft.service.impl;

import cn.edu.neusoft.dao.RoleDao;
import cn.edu.neusoft.dao.RolePermissionDao;
import cn.edu.neusoft.entity.Role;
import cn.edu.neusoft.entity.RolePermission;
import cn.edu.neusoft.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-02 13:45
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private  RoleDao roleDao ;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public List<String> getRoleByUserId(Long id) {
        return roleDao.getRoleByUserId(id);
    }

    @Override
    public List<Role> getRoleList() {
        return roleDao.getRoleList();
    }

    @Override
    public int getCount() {
        return roleDao.getCount();
    }

    @Override
    public List<Role> getRoleListByPage(Map queryMap) {
        return roleDao.getRoleListByPage(queryMap);
    }

    @Override
    public void addRoleAndPermission(Role role, List<String> permissions) {
        //先添加角色
        roleDao.addRole(role);
        Long roleId =role.getId();

        //遍历添加权限
        RolePermission rolePermission = new RolePermission();
        for (String permissionId : permissions) {
            if (permissionId!= null && permissionId != ""){
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(Long.valueOf(permissionId));
                rolePermissionDao.addRolePermission(rolePermission);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        roleDao.deleteRole(id);
        rolePermissionDao.deleteByRoleId(id);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public void updateRoleAndPermission(Role role, List<String> permissionArr) {
        roleDao.updateRole(role);
        rolePermissionDao.deleteByRoleId(role.getId());
        RolePermission rolePermission = new RolePermission();
        for (String s : permissionArr) {
            if (s != null && s != ""){
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(Long.valueOf(s));
                rolePermissionDao.addRolePermission(rolePermission);
            }
        }
    }

    @Override
    public void deleteAllRole(String[] ids) {
        roleDao.deleteAllRole(ids);
        rolePermissionDao.deleteAllByRoleId(ids);
    }
}
