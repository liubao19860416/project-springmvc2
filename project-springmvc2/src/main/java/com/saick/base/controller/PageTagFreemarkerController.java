package com.saick.base.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meidusa.fastjson.JSON;
import com.saick.base.MyWebUtils;
import com.saick.base.controller.util.MyStringUtils;
import com.saick.base.dao.entiy.User;
import com.saick.base.pager.Page;

/**
 * 分页标签测试Controller,Freemarker实现
 * 
 * @author Liubao
 * @2015年2月8日
 *
 */
@Controller
//@RequestMapping("/freemarker")
public class PageTagFreemarkerController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 测试：默认是单例模式
    public PageTagFreemarkerController() {
        super();
        System.out.println("PageTagFreemarkerController构造方法执行了。。。"
                + this.getClass().toString());
    }

    @RequestMapping("/page/freemarker1")
    public String showPageTag1(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        int pageNum = MyStringUtils.getIntValue(request.getParameter("pageNum"));
        int pageSize =  MyStringUtils.getIntValue(request.getParameter("pageSize"));
        if(pageNum<=0){
            pageNum=Page.DEFAULTPAGENUM;
        }
        if(pageSize<=0){
            pageSize=Page.DEFAULTPAGESIZE;
        }
        if(logger.isInfoEnabled()){
            logger.info("获取的分页请求参数当前页码为："+JSON.toJSONString(pageNum));
            logger.info("获取的分页请求参数每页记录条数为："+JSON.toJSONString(pageSize));
        }
        
        //这里通过查询数据库获取总记录条数
        int totalCount=100;
        
        //相当于查询数据库
        List<User> list = new ArrayList<User>();
        for (int i = (pageNum-1)*pageSize; i < (pageNum-1)*pageSize+pageSize; i++) {
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            list.add(user);
        }
        
        //初始化Page对象
        Page<User> page = new Page<User>(pageNum,pageSize,totalCount);
        
        //也可以获取起始角标
        //int pageIndex = page.getPageIndex();
        //这里通过查询数据库获取记录
        page.setRecords(list);
        
        //从request中获取对应的请求参数
        Map<String, Object> requestMap = MyWebUtils.getMapFromRequest1(request);
        Map<String, String> parameterMap=new HashMap<String, String>();
        if (requestMap != null&&!requestMap.isEmpty()) {
            for (String key : requestMap.keySet()) {
                String value = MyStringUtils.getStringValue(requestMap.get(key));
                parameterMap.put(key, value);
            }
        }
        if(logger.isInfoEnabled()){
            logger.info("通过request获取的请求参数parameterMap为："+JSON.toJSONString(parameterMap));
        }
        
//        parameterMap.clear();
//        parameterMap.put("BIR1", "001");
//        parameterMap.put("BIR2", "002");
//        parameterMap.put("BIR3", "003");
//        page.setParameterMap(parameterMap);
        
        //返回页面
        model.addAttribute("page", page);
        
        //直接打出到浏览器显示 TODO 
        //PrintWriter writer = response.getWriter();
        //writer.print("<script>alert('导出报表数据过多！请添加筛选条件！');</script>");
        
        //这里是全局页面刷新
        return "/page/freemarker1";
    }

    
}
