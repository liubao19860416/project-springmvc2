package com.saick.base.tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saick.base.dao.entiy.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 用户登录验证自定义标签
 * 
 * @author Liubao
 * @2015年2月8日
 * 
 */
public class UserLoginCheckTag extends SimpleTagSupport {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private User userinfo;
    private String logininfo;

    public User getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(User userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 输出标签内容
        // 1、 获取 JspWriter对象
        JspWriter out = this.getJspContext().getOut();
        PageContext pageContext = (PageContext) this.getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext
                .getResponse();
        response.setContentType("text/html;charset=utf-8");

        // 2、freemarker模板编程
        Configuration configuration = new Configuration();
        // 指定模板位置
        String directory = pageContext.getServletContext().getRealPath(
                "/WEB-INF/template");
        configuration.setDirectoryForTemplateLoading(new File(directory));
        // 获取模板对象
        Template template = configuration.getTemplate("userLoginCheck.ftl",
                "utf-8");

        // 获取session中的当前登录用户信息
        userinfo = (User) request.getSession().getAttribute("userinfo");

        // 3、 准备数据存储的map
        Map<String, Object> dataMap = new HashMap<String, Object>();

        if (userinfo == null||StringUtils.isBlank(userinfo.getUsername())) {
            userinfo = new User();
            // 设置默认用户名
            userinfo.setUsername("");
            dataMap.put("logininfo", "<a href=\"javascript:void(0)\">请先登录!!!</a>");
        }

        // 在这里也可以对用户的权限进行控制
        List<User> userinfos = new ArrayList<User>();

        /**
         * 这里需要获取用户的权限信息; TODO 
         */
        if (userinfos.contains(userinfo)) {
            // System.out.println("具备权限");
        } else {
            // System.out.println("不具备权限");
        }

        // 3、 准备数据
        dataMap.put("userinfo", userinfo);  

        // 4、 合并输出
        try {
            // template.process(dataMap, new PrintWriter(System.out)); // 输出到控制台
            template.process(dataMap, out);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException("模板错误！");
        }

    }
}
