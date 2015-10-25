package com.saick.base.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meidusa.fastjson.JSON;
import com.saick.base.dao.entiy.User;

/**
 * 登录校验标签测试Controller,Freemarker实现
 * 
 * @author Liubao
 * @2015年2月8日
 *
 */
@Controller
public class UserLoginCheckController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 测试：默认是单例模式
    public UserLoginCheckController() {
        super();
        System.out.println("UserLoginCheckController构造方法执行了。。。"
                + this.getClass().toString());
    }

    @RequestMapping("/page/userLoginCheck")
    public String userLoginCheck(String username,String password, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        User userinfo=new User();
        if(StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(password)){
            userinfo.setUsername(username);
            userinfo.setPassword(password);
        }else{
            userinfo.setUsername("");
            userinfo.setPassword("");
        }
        if(logger.isInfoEnabled()){
            logger.info("通过request获取的请求参数User为："+JSON.toJSONString(userinfo));
        }
        
        //返回页面
        request.getSession().setAttribute("userinfo", userinfo);
        
        //直接打出到浏览器显示 TODO 
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print("<script>alert('导出报表数据过多！请添加筛选条件！');</script>");
        writer.flush();
        return "/page/welcome";
    }
    
    @RequestMapping("/page/userLogin")
    public String userLogin(Model model) throws Exception {
        return "/page/userLoginCheck";
    }

    
}
