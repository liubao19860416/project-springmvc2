package com.saick.base.pager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meidusa.fastjson.JSON;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 分页工具条封装类:使用Freemarker模版实现
 * 
 * @author Liubao
 * @2015年2月5日
 * 
 */
public class PagTagFreemarker extends SimpleTagSupport {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //每页显示的页码个数
    public static int DEFAULTNUMBERPAGES = 5;
    
    // 当前页码
    private int pageNum;
    // 每页记录数
    private int pageSize;
    // 总记录数
    private int totalCount;
    // 查询条件参数url
    private String parameterUrl;
    // 服务器分页查询数据地址
    private String pageQueryUrl;

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setParameterUrl(String parameterUrl) {
        this.parameterUrl = parameterUrl;
    }

    public void setPageQueryUrl(String pageQueryUrl) {
        this.pageQueryUrl = pageQueryUrl;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 输出标签内容
        // 1、 获取 JspWriter对象
        JspWriter out = this.getJspContext().getOut();
        PageContext pageContext = (PageContext) this.getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        //可以通过request获取其他的请求参数

        // 2、freemarker模板编程
        Configuration configuration = new Configuration();
        // 指定模板位置
        String directory = pageContext.getServletContext().getRealPath(
                "/WEB-INF/template");
        configuration.setDirectoryForTemplateLoading(new File(directory));
        // 获取模板对象
        Template template = configuration.getTemplate("pagTagFreemarker.ftl", "utf-8");
       
        // 3、 准备数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("totalCount", totalCount); // 总记录数
        dataMap.put("pageSize", pageSize); // 每页记录数
        dataMap.put("pageNum", pageNum); // 当前页码
        int totalPages = (int) ((totalCount + pageSize - 1) / pageSize);
        if(totalPages==0){
            totalPages=1;
        }
        dataMap.put("totalPages", totalPages); // 总页数
        dataMap.put("contextPath", request.getContextPath()); // web上下文 [项目名称]
        dataMap.put("parameterUrl", parameterUrl); // get请求参数
        dataMap.put("pageQueryUrl", pageQueryUrl); // 请求路径

        
        PageNumStartAndEnd startAndEnd=new PageNumStartAndEnd();
        //计算
        calculatePageNumStartAndEnd(pageNum,pageSize,totalPages,startAndEnd);

        // 输出连续页码 当前页中心 前5 后5格式
        int pageNumStart = startAndEnd.getPageNumStart();
        int pageNumEnd = startAndEnd.getPageNumEnd();
        
        dataMap.put("pageNumStart", pageNumStart);
        dataMap.put("pageNumEnd", pageNumEnd);
        
        if(logger.isInfoEnabled()){
            logger.info("获取的分页显示页码起始值为："+JSON.toJSONString(pageNumStart));
            logger.info("获取的分页显示页码结束值为："+JSON.toJSONString(pageNumEnd));
        }

        // 4、 合并输出
        try {
            // 输出到控制台
            // template.process(dataMap, new PrintWriter(System.out)); 
            template.process(dataMap, out);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException("模板加载失败！！！");
        }
    }

    /**
     * 通过当前页码，每页显示记录条数和总记录条数，计算对应的需要显示的DEFAULTNUMBERPAGES个页码的起始和结束角标
     */
    private void calculatePageNumStartAndEnd(int pageNum, int pageSize,
            int totalPages, PageNumStartAndEnd startAndEnd) {
        if(DEFAULTNUMBERPAGES%2==0){
            //偶数的情况，先转换为奇数去处理，最后还原为偶数即可；
            DEFAULTNUMBERPAGES=DEFAULTNUMBERPAGES+1;
            
            this.calculatePageNumStartAndEnd(pageNum, pageSize, totalPages, startAndEnd);
            
            if((startAndEnd.getPageNumEnd()-startAndEnd.getPageNumStart()+1)==DEFAULTNUMBERPAGES){
                //startAndEnd.setPageNumStart(startAndEnd.getPageNumStart()+1);
                if(startAndEnd.getPageNumStart()==1){
                    startAndEnd.setPageNumEnd(startAndEnd.getPageNumEnd()-1);
                }else{
                    if(startAndEnd.getPageNumEnd()==totalPages){
                        startAndEnd.setPageNumStart(startAndEnd.getPageNumStart()+1);
                    }else{
                        startAndEnd.setPageNumEnd(startAndEnd.getPageNumEnd()-1);
                    }
                }
            }
            //还原该值
            DEFAULTNUMBERPAGES=DEFAULTNUMBERPAGES-1;
        }else{
            //奇数
            //0.首先计算绝对值,获取当前页面的当前页码的前\后几个页码的那个间距值
            int abs = Math.abs(DEFAULTNUMBERPAGES/2);
            
            //1.根据总页数totalPages与指定值DEFAULTNUMBERPAGES值的关系,计算 pageNumEnd的值;
            if (totalPages >= DEFAULTNUMBERPAGES) {
                if(totalPages>=(pageNum+abs)){
                    startAndEnd.setPageNumEnd(pageNum+abs);
                }else{
                    startAndEnd.setPageNumEnd(totalPages);
                }
                if(startAndEnd.getPageNumEnd()<=DEFAULTNUMBERPAGES){
                    startAndEnd.setPageNumEnd(DEFAULTNUMBERPAGES);
                }
            }else{
                startAndEnd.setPageNumEnd(totalPages);
            }
            
            //2.根据当前页码pageNum与指定值abs值的关系,计算 pageNumStart的值;
            if (pageNum <=abs) {
                startAndEnd.setPageNumStart(1);
            }else{
                startAndEnd.setPageNumStart(pageNum-abs);
            }
            
            //3.根据当前结束页码与totalPages值的关系,重新确认计算 pageNumStart的值;
            if(startAndEnd.getPageNumEnd()==totalPages){
                if((totalPages-DEFAULTNUMBERPAGES)>0){
                    startAndEnd.setPageNumStart(totalPages-DEFAULTNUMBERPAGES+1);
                }
            }
        }
        
        //4.技术和偶数情况公用，计算总记录条数为0的情况
        if(0==totalPages){
            startAndEnd.setPageNumStart(1);
            startAndEnd.setPageNumEnd(1);
        }
        
    }
    
    //测试
    public static void main(String[] args) {
        System.out.println((int)Math.abs(DEFAULTNUMBERPAGES/2));
        System.out.println((int)Math.abs(5.5/2));
        System.out.println((int)Math.abs(6.7/2));
        System.out.println((int)Math.abs(7/2));
    }

}
