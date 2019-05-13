package cn.edu.neusoft.controller.admin;

import cn.edu.neusoft.common.WebUtilsPro;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chen
 * @create 2019-05-02 19:26
 */
public abstract class BaseController {


    /**
     * 权限异常
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
        if (WebUtilsPro.isAjaxRequest(request)) {
            // 输出JSON
            Map<String,Object> map = new HashMap<>();
            map.put("type", "error");
            map.put("msg", "无权限");
            writeJson(map, response);
            return null;
        } else {
            return "redirect:/system/403";
        }
    }

    /**
     * 输出JSON
     *
     * @param response
     */
    private void writeJson(Map<String,Object> map, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(String.valueOf(new JSONObject(map)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
