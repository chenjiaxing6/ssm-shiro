package cn.edu.neusoft.controller.admin;

import cn.edu.neusoft.entity.PageBean;
import cn.edu.neusoft.entity.Permission;
import cn.edu.neusoft.realm.PermissionName;
import cn.edu.neusoft.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-01 20:28
 */
@Controller
@RequestMapping("admin/permission")
public class PermissionController extends BaseController{

    @Autowired
    private PermissionService permissionService;

    //请求映射处理映射器
    //springmvc在启动时候将所有贴有请求映射标签：RequestMapper方法收集起来封装到该对象中
    @Autowired
    private RequestMappingHandlerMapping rmhm;


    /**
     * 加载权限
     * @return
     */
    @ResponseBody
    @RequestMapping("reload")
    public Map<String,Object> reload(){
        Map<String,Object> map = new HashMap<String, Object>();
        //将系统中所有权限表达式加载进入数据库
        //0：从数据库中查询出所有权限表达式，然后对比，如果已经存在了，跳过，不存在添加
        List<String> resourcesList = permissionService.getAllResources();
        //1:获取controller中所有带有@RequestMapper标签的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();
        Collection<HandlerMethod> methods = handlerMethods.values();
        for (HandlerMethod method : methods) {
            //2：遍历所有方法，判断当前方法是否贴有@RequiresPermissions权限控制标签
            RequiresPermissions anno = method.getMethodAnnotation(RequiresPermissions.class);
            if(anno != null){
                //3：如果有，解析得到权限表达式，封装成Permission对象保存到Permission表中
                //权限表达式
                String resource = anno.value()[0];

                //去除重复的
                if(resourcesList.contains(resource)){
                    continue;
                }
                Permission p = new Permission();
                p.setResource(resource);
                //设置权限名称
                p.setName(method.getMethodAnnotation(PermissionName.class).value());
                //保存
                permissionService.addPermission(p);
            }
        }

        map.put("type","success");
        map.put("msg","加载成功！");
        return map;
    }

    /**
     * 分页查询权限
     * @param pageBean
     * @return
     */
    @RequiresPermissions("permission:list")
    @PermissionName("权限列表")
    @ResponseBody
    @RequestMapping(value = "getPermissionList")
    public Map<String,Object> getPermissionList(PageBean pageBean){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("offset",pageBean.getOffset());
        queryMap.put("limit",pageBean.getLimit());
        //查询数据
        List<Permission> data = permissionService.getPermissionListByPage(queryMap);
        int count = permissionService.getPermissonCount();

        resultMap.put("data",data);
        resultMap.put("count",count);
        resultMap.put("code","0");
        resultMap.put("msg","成功");
        return resultMap;
    }


}
