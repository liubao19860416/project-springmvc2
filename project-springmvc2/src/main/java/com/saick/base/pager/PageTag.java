package com.saick.base.pager;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页工具条封装类：只带页码和每页显示条数查询条件，不带其他查询参数的分页工具类
 * 
 * @author Liubao
 * @2015年2月5日
 * 
 */
public class PageTag extends SimpleTagSupport {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 当前页页码,必填
    private int pageNum;
    // 每页显示记录条数
    private int pageSize;
    // 总记录条数,必填
    private int totalCount;

    // 请求参数和url
    private String parameterUrl;
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
        // 1、 获取 JspWriter对象
        PageContext pageContext = (PageContext) this.getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        String contextPath = request.getContextPath();
        JspWriter out = this.getJspContext().getOut();

        int totalPages = (totalCount + pageSize - 1) / pageSize;
        // 2、生成div输出分页工具条
        out.write("<div align=\"left\">");
        // 输出页码、总记录、每页记录数、总页数信息
        out.write("<span>总记录数:" + totalCount + "条,</span>");
        out.write("<span>每页记录数:" + pageSize + "条,</span>");
        out.write("<span>总页数:" + totalPages + "页,</span>");
        out.write("<span>当前第:" + pageNum + "页.&nbsp;&nbsp;</span>");

        // 输出首页 、上一页
        if (pageNum == 1) {
            // 已经是第一页（首页）
            out.write("<a href=\"javascript:void(0)\">首页</a>");
            out.write("<a href=\"javascript:void(0)\">上一页</a>");
        } else {
            // 不是第一页
            out.write("<a href=\"" + contextPath + pageQueryUrl
                    + "?pageNum=1&pageSize=" + pageSize + parameterUrl
                    + "\">首页</a>");
            out.write("<a href=\"" + contextPath + pageQueryUrl + "?pageNum="
                    + (pageNum - 1) + "&pageSize=" + pageSize + parameterUrl
                    + "\">上一页</a>");
        }
        // 输出连续页码 当前页中心 前5 后5,这里用常量代替
        int begin = 1;
        int end = totalPages;

        // 让每页显示5个页码(最多)
        if (totalPages >= 5) {
            // 是为了让页码显示为5个或者最大值
            if (pageNum + Page.DEFAULTNUMBER <= 5) {
                begin = 1;
                end = 5;
            } else {
                begin = pageNum - Page.DEFAULTNUMBER;
                end = pageNum + Page.DEFAULTNUMBER;
                /**
                 * 这里需要再做一个判断,对end和begin确保现实的页码也是5个;
                 */
                if (end >= totalPages) {
                    end = totalPages;
                    begin = totalPages - 4;
                }
            }
        } else {
            if (pageNum - Page.DEFAULTNUMBER >= 1) {
                begin = pageNum - Page.DEFAULTNUMBER;
            } else {
                begin = 1;
            }
            if (pageNum + Page.DEFAULTNUMBER <= totalPages) {
                end = pageNum + Page.DEFAULTNUMBER;
            } else {
                end = totalPages;
            }
        }

        for (int i = begin; i <= end; i++) {
            if (i == pageNum) {
                // 当前页不给链接
                out.write("<a>[ " + i + " ]</a>");
            } else {
                // 不是当前页
                out.write("<a href=\"" + request.getContextPath() + pageQueryUrl
                        + "?pageNum=" + i + "&pageSize=" + pageSize
                        + parameterUrl + "\">[ " + i + "] </a>");
            }
        }

        // 下一页、尾页
        if (pageNum == totalPages) {
            // 已经是最后一页
            out.write("<a href=\"javascript:void(0)\">下一页</a>");
            out.write("<a href=\"javascript:void(0)\">尾页</a>");
        } else {
            // 不是最后一页
            out.write("<a href=\"" + request.getContextPath() + pageQueryUrl
                    + "?pageNum=" + (pageNum + 1) + "&pageSize=" + pageSize
                    + parameterUrl + "\">下一页</a>");
            out.write("<a href=\"" + request.getContextPath() + pageQueryUrl
                    + "?pageNum=" + totalPages + "&pageSize=" + pageSize
                    + parameterUrl + "\">尾页</a>");
        }

        // 直接跳转到第几页
        out.write("<input type=\"text\" size=\"2\" name=\"selectedPageNum2\"/>");
        // 设置总页数和被选中的页码的隐藏域;
        out.write("<input type=\"hidden\" name=\"selectedPageNum1\" value=\""
                + parameterUrl + "\"/>");
        out.write("<input type=\"hidden\"  id=\"totalPageCount\" value=\""
                + totalPages + "\"/>");
        out.write("<input type=\"button\" id=\"selectedPageNum1\" value=\"go\" size=\"2\" />");

        out.write("</div>");
        out.flush();
    }
}
