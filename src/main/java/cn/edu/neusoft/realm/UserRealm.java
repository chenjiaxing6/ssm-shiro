package cn.edu.neusoft.realm;

import cn.edu.neusoft.entity.User;
import cn.edu.neusoft.service.PermissionService;
import cn.edu.neusoft.service.RoleService;
import cn.edu.neusoft.service.UserService;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen
 * @create 2019-04-30 15:27
 */
public class UserRealm extends AuthorizingRealm {
    @Setter
    private UserService userService;
    @Setter
    private RoleService roleService;
    @Setter
    private PermissionService permissionService;


    @Override
    public String getName() {
        return "UserRealm";
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<String> permissions = new ArrayList<String>();
        List<String> roles = new ArrayList<String>();

        if ("admin".equals(user.getUsername())){
            //让超级管理员拥有所有权限
            permissions.add("*:*");
            //查询所有角色
            roles = roleService.getRoleByUserId(user.getId());
        }else {
            //根据用户ID查询该用户具有的角色
            roles = roleService.getRoleByUserId(user.getId());
            //根据用户ID查询该用户具有的权限
            permissions = permissionService.getAllPermissionsByUserId(user.getId());
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
        info.addRoles(roles);
        return info;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取登录的用户名， 查询数据库返回用户信息
        String username = (String) token.getPrincipal();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return info;
    }

}
