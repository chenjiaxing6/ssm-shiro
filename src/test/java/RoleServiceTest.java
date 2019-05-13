import cn.edu.neusoft.dao.RolePermissionDao;
import cn.edu.neusoft.dao.UserDao;
import cn.edu.neusoft.dao.UserRoleDao;
import cn.edu.neusoft.entity.RolePermission;
import cn.edu.neusoft.entity.User;
import cn.edu.neusoft.entity.UserRole;
import cn.edu.neusoft.service.PermissionService;
import cn.edu.neusoft.service.RoleService;
import cn.edu.neusoft.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Chen
 * @create 2019-05-02 13:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-mybatis.xml")
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Test
    public void getRoleByUseridTest(){
        List<String> role = roleService.getRoleByUserId(1l);
        for (String s : role) {
            System.out.println(role);
        }
    }

    @Test
    public void getPermissionByUserId(){
        List<String> permissions = permissionService.getAllPermissionsByUserId(1l);
        for (String permission : permissions) {
            System.out.println(permission);
        }
    }

    @Test
    public void deleteUser(){
        userRoleDao.deleteByUserId(21l);
    }

    @Test
    public void addRolePermission(){
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(1l);
        rolePermission.setPermissionId(2l);
        rolePermissionDao.addRolePermission(rolePermission);
    }
}
