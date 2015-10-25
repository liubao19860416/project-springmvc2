package com.saick.base.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 防盗链接的自定义标签:必须通过指定页面,才能访问指定资源; site="http://localhost":表示可以访问的网站地址
 * page="/index.jsp":表示不可以访问的连接主页
 * 
 */
public class RefererURLTag extends SimpleTagSupport {
    private String site;// 防盗链规则(开始网站地址)
    private String page;// 默认跳转主页

    public void setSite(String site) {
        this.site = site;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext
                .getResponse();

        String referer = request.getHeader("Referer");
        System.out.println("设置的防盗链网址开头[site]的格式为1:" + site);

        if (referer == null || !referer.startsWith(site)) {

            System.out.println("您的referer地址是:[" + referer
                    + "],不能够直接访问!自动跳转到指定的" + page + "页面");

            if (page.startsWith(request.getContextPath())) {
                System.out.println("page的格式为1:" + page);
                response.sendRedirect(page);
                // return;
            } else if (page.startsWith("/")) {
                System.out.println("page的格式为2:" + page);
                response.sendRedirect(request.getContextPath() + page);
                // return;
            } else {
                System.out.println("page的格式为3:" + page);
                response.sendRedirect(request.getContextPath() + "/" + page);
                // return;
            }
            // System.out.println("您的referer地址是:[" + referer
            // + "],符合规则,自动跳转到指定资源!");
            throw new SkipPageException();
        }
    }

}
