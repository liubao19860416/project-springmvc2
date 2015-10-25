package com.saick.base.pager;

import java.io.Serializable;

/**
 * 计算需要显示的页码范围的实体类
 * 
 * @author Liubao
 * @2015年2月8日
 * 
 */
public class PageNumStartAndEnd implements Serializable {

    private static final long serialVersionUID = 8509375387004885677L;

    public PageNumStartAndEnd() {
        super();
    }

    public PageNumStartAndEnd(int pageNumStart, int pageNumEnd) {
        super();
        this.pageNumStart = pageNumStart;
        this.pageNumEnd = pageNumEnd;
    }

    // 需要显示的页码范围起始值;
    private int pageNumStart=1;
    // 需要显示的页码范围结束值
    private int pageNumEnd=1;

    public int getPageNumStart() {
        return pageNumStart;
    }

    public int getPageNumEnd() {
        return pageNumEnd;
    }

    public void setPageNumStart(int pageNumStart) {
        this.pageNumStart = pageNumStart;
    }

    public void setPageNumEnd(int pageNumEnd) {
        this.pageNumEnd = pageNumEnd;
    }

}
