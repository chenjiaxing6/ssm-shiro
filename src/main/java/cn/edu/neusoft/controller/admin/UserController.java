package cn.edu.neusoft.controller.admin;

import cn.edu.neusoft.common.ParamUtils;
import cn.edu.neusoft.entity.PageBean;
import cn.edu.neusoft.entity.Role;
import cn.edu.neusoft.entity.User;
import cn.edu.neusoft.realm.PermissionName;
import cn.edu.neusoft.service.RoleService;
import cn.edu.neusoft.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.registry.infomodel.EmailAddress;
import java.util.*;

/**
 * @author Chen
 * @create 2019-04-30 15:13
 */
@Controller
@RequestMapping("admin/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 登录
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("login")
    public String login(Model model, HttpServletRequest req){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            return "admin/main";
        }
        //如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        //根据shiro返回的异常类路径判断，抛出指定异常信息
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //最终会抛给异常处理器
                model.addAttribute("errorMsg", "账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                model.addAttribute("errorMsg", "用户名/密码错误");
            } else {
                //最终在异常处理器生成未知错误.
                model.addAttribute("errorMsg", "其他异常信息");
            }
        }
        //此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
        //登陆失败还到login页面
        return "admin/login";
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:user/login";
    }

    /**
     * 分页查询全部用户
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "getUserList")
    @ResponseBody
    @RequiresPermissions("user:list")
    @PermissionName("管理员列表")
    public Map<String,Object> getUserList(PageBean pageBean){
        Map<String,Object> queryMap = new HashMap<String, Object>();
        Map<String,Object> resultMap = new HashMap<String, Object>();

        //传入参数查询
        queryMap.put("limit",pageBean.getLimit());
        queryMap.put("offset",pageBean.getOffset());
        List<User> userList = userService.getUserByQueryMap(queryMap);
        int count = userService.getUserCount(queryMap);

        //封装返回数据
        resultMap.put("data",userList);
        resultMap.put("count",count);
        resultMap.put("code","0");
        resultMap.put("msg","成功");
        return  resultMap;
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    @RequiresPermissions("user:delete")
    @PermissionName("管理员删除")
    public Map<String,Object> deleteUser(@RequestParam(required = true) Long id){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if (id != null){
            userService.deleteById(id);
            resultMap.put("msg","删除成功");
            resultMap.put("type","success");
        }else {
            resultMap.put("msg","删除失败");
            resultMap.put("type","error");
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "addUser")
    @PermissionName("管理员添加")
    @RequiresPermissions("user:add")
    public Map<String,Object> addUser(String userInfo){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        //把传过来的数组根据进行切分，查询传过来几个数据
        int num = ParamUtils.paramNum(userInfo);
        
        //将json字符串转换为json对象，取出添加的用户名，密码等管理员的信息
        JSONObject jsonObject = JSON.parseObject(userInfo);
        User user = new User();
        user.setUsername(jsonObject.getString("username"));
        user.setPassword(jsonObject.getString("password"));
        user.setEmail(jsonObject.getString("email"));
        user.setPhone(jsonObject.getString("phone"));

        //获取传过来的角色id，放入数组中
        List<String> roleArr = new ArrayList<String>();
        for (int i = 0; i <num ; i++) {
            String key = "ids["+i+"]";
            roleArr.add(jsonObject.getString(key));
        }

        userService.addUser(user,roleArr);
        resultMap.put("type","success");
        resultMap.put("msg","添加成功！");
        return resultMap;
    }

    /**
     * 根据用户ID返回角色SN 的json数据
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("getRoleSnByUserId")
    public List<String> getRoleSnByUserId(Long id){
        return roleService.getRoleByUserId(id);
    }

    @RequestMapping("updateUser")
    @ResponseBody
    @RequiresPermissions("user:update")
    @PermissionName("管理员更新")
    public Map<String,Object> updateUser(String userInfo){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        //把传过来的数组根据进行切分，查询传过来几个数据
        int num = ParamUtils.paramNum(userInfo);

        //将json字符串转换为json对象，取出添加的用户名，密码等管理员的信息
        JSONObject jsonObject = JSON.parseObject(userInfo);
        User user = new User();
        user.setId(Long.valueOf(jsonObject.getString("id")));
        user.setUsername(jsonObject.getString("username"));
        user.setPassword(jsonObject.getString("password"));
        user.setEmail(jsonObject.getString("email"));
        user.setPhone(jsonObject.getString("phone"));


        //获取传过来的角色id，放入数组中
        List<String> roleArr = new ArrayList<String>();
        for (int i = 0; i <num ; i++) {
            String key = "ids["+i+"]";
            roleArr.add(jsonObject.getString(key));
        }

        userService.updateUser(user,roleArr);
        resultMap.put("type","success");
        resultMap.put("msg","添加成功！");
        return resultMap;
    }
}
