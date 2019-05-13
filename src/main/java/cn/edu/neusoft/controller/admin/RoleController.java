package cn.edu.neusoft.controller.admin;

import cn.edu.neusoft.common.ParamUtils;
import cn.edu.neusoft.entity.PageBean;
import cn.edu.neusoft.entity.Permission;
import cn.edu.neusoft.entity.Role;
import cn.edu.neusoft.realm.PermissionName;
import cn.edu.neusoft.service.PermissionService;
import cn.edu.neusoft.service.RoleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-03 17:23
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController{
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 查询角色列表用于添加用户时显示
     * @return
     */
    @ResponseBody
    @RequestMapping("getRoleList")
    public Map<String,Object> getRoleList(){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data",roleService.getRoleList());
        resultMap.put("type","success");
        resultMap.put("msg","角色信息加载成功！");
        return resultMap;
    }

    /**
     * 分页查询角色列表
     * @return
     */
    @ResponseBody
    @RequestMapping("getRoleListByPage")
    @PermissionName("角色列表")
    @RequiresPermissions("role:list")
    public Map<String,Object> getRoleListByPage(PageBean pageBean){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset",pageBean.getOffset());
        queryMap.put("limit",pageBean.getLimit());
        //查询数据

        resultMap.put("data",roleService.getRoleListByPage(queryMap));
        resultMap.put("count",roleService.getCount());
        resultMap.put("code","0");
        resultMap.put("msg","成功");

        return resultMap;
    }


    /**
     * 添加角色
     * @param roleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("addRole")
    @PermissionName("角色添加")
    @RequiresPermissions("role:add")
    public Map<String,Object> addRole(String roleInfo){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        //把传过来的数组根据进行切分，查询传过来几个数据
        int num = ParamUtils.paramNum(roleInfo);


        //将json字符串转换为json对象，取出添加的用户名，密码等管理员的信息
        JSONObject jsonObject = JSON.parseObject(roleInfo);
        Role role = new Role();
        role.setName(jsonObject.getString("name"));
        role.setSn(jsonObject.getString("sn"));
        role.setDesc(jsonObject.getString("desc"));

        //获取传过来的角色id，放入数组中
        List<String> permissionArr = new ArrayList<String>();
        for (int i = 0; i <num ; i++) {
            String key = "ids["+i+"]";
            permissionArr.add(jsonObject.getString(key));
        }

        roleService.addRoleAndPermission(role,permissionArr);

        resultMap.put("type","success");
        resultMap.put("msg","添加成功！");
        return resultMap;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping("deleteRole")
    @ResponseBody
    @RequiresPermissions("role:delete")
    @PermissionName("角色删除")
    public Map<String,Object> deleteRole(@RequestParam(required = true) Long id){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if (id != null){
            roleService.deleteById(id);
            resultMap.put("msg","删除成功");
            resultMap.put("type","success");
        }else {
            resultMap.put("msg","删除失败");
            resultMap.put("type","error");
        }
        return resultMap;
    }

    /**
     * 获取当前用户的权限
     * @param id
     * @return
     */
    @RequestMapping("getPermissionByRoleId")
    @ResponseBody
    public List<Long> getPermissionByRoleId(Long id){
        List<Long> permissions = permissionService.getPermissionsIdByRoleId(id);
        return permissions;
    }

    /**
     * 更新角色
     * @param roleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("roleUpdate")
    @PermissionName("角色添加")
    @RequiresPermissions("role:update")
    public Map<String,Object> roleUpdate(String roleInfo){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        //把传过来的数组根据进行切分，查询传过来几个数据
        int num = ParamUtils.paramNum(roleInfo);


        //将json字符串转换为json对象，取出添加的用户名，密码等管理员的信息
        JSONObject jsonObject = JSON.parseObject(roleInfo);
        Role role = new Role();
        role.setId(jsonObject.getLong("id"));
        role.setName(jsonObject.getString("name"));
        role.setSn(jsonObject.getString("sn"));
        role.setDesc(jsonObject.getString("desc"));

        //获取传过来的角色id，放入数组中
        List<String> permissionArr = new ArrayList<String>();
        for (int i = 0; i <num ; i++) {
            String key = "ids["+i+"]";
            permissionArr.add(jsonObject.getString(key));
        }

        roleService.updateRoleAndPermission(role,permissionArr);
        resultMap.put("type","success");
        resultMap.put("msg","添加成功！");
        return resultMap;
    }

    /**
     * 批量删除角色
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteAllRole")
    @PermissionName("角色批量删除")
    @RequiresPermissions("role:deleteAll")
    public Map<String,Object> deleteAllRole(String info){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String[] ids = info.split(",");

        roleService.deleteAllRole(ids);
        resultMap.put("msg","删除成功");
        resultMap.put("type","success");
        return resultMap;
    }

}
