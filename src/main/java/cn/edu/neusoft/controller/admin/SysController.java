package cn.edu.neusoft.controller.admin;

import cn.edu.neusoft.entity.User;
import cn.edu.neusoft.entity.UserRole;
import cn.edu.neusoft.service.RoleService;
import cn.edu.neusoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Chen
 * @create 2019-04-30 15:11
 */
@Controller
@RequestMapping("admin/system")
public class SysController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    /**
     * 跳转到主页
     * @return
     */
    @RequestMapping(value = "main")
    public String main(){
        return "admin/main";
    }

    /**
     * 欢迎界面
     */
    @RequestMapping(value = "welcome")
    public String welcome(){
        return "admin/welcome";
    }

    /**
     * 管理员列表
     * @return
     */
    @RequestMapping("admin_list")
    public String admin_list(){
        return "admin/admin_list";
    }

    /**
     * 管理员添加
     * @return
     */
    @RequestMapping("admin_add")
    public String admin_add(){
        return "admin/admin_add";
    }

    /**
     * 跳转到更新管理员界面
     * @return
     */
    @RequestMapping("admin_update")
    public String admin_update(Long id, Model model){
        //查询用户信息，用于回显
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return  "admin/admin_update";
    }


    /**
     * 角色列表
     * @return
     */
    @RequestMapping("role_list")
    public String role_list(){
        return "admin/role_list";
    }

    /**
     * 角色添加
     * @return
     */
    @RequestMapping("role_add")
    public String role_add(){
        return "admin/role_add";
    }

    @RequestMapping("role_update")
    public String role_update(Long id, Model model){
        model.addAttribute("role",roleService.getRoleById(id));
        return  "admin/role_update";
    }

    /**
     * 权限列表
     * @return
     */
    @RequestMapping("permission_list")
    public String permission_list(){
        return "admin/permission_list";
    }

    /**
     * 无权限页面
     * @return
     */
    @RequestMapping(value = "nopermission")
    public String nopermission(){
        return "admin/nopermission";
    }
}
