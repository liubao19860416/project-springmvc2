package com.saick.base.pager;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meidusa.fastjson.JSON;

/**
 * 封装的分页参数实体
 * 
 * @author Liubao
 * @2015年2月5日
 * 
 */
public class Page<T> implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static final long serialVersionUID = 9219066557017706402L;
    
    // 输出连续页码 当前页中心 前5 后5,这里用常量代替
    public static final int DEFAULTNUMBER = 2;
    public static final int DEFAULTPAGENUM = 1;
    public static final int DEFAULTPAGESIZE = 5;
    
    // 当前显示的页码,默认是1;
    private int pageNum = DEFAULTPAGENUM;
    // 每页显示条数;默认是5;
    private int pageSize = DEFAULTPAGESIZE;
    // 每页开始的索引.计算出来;索引是从0开始的;
    private int pageIndex;
    // 总页数,在方法中计算出来
    private int totalPages;

    // 通过request获取
    private Map<String, String> parameterMap;
    // key=value&key=value&key=value...
    // 这个参数的主要作用是在条件查询,换页的时候,提供显示数据的参数的方法; 也就是实现带条件的分页效果显示;
    private String parameterUrl;

    // 每页结果具体显示的记录,需要从数据库查询获取;
    private List<T> records;
    // 总记录数（满足当前条件）,需要从数据库查询获取
    private int totalCount;
    
    /**
     * 空构造方法，手动进行set所有的属性进行赋值
     * 
     * 参见下面的构造方法中的计算方法
     */
    @Deprecated
    public Page() {
        super();
    }

    /**
     * 构造方法初始化参数,当跳转页面的时候,需要知道跳转到哪一页和总的记录条数;
     * @param pageSize 
     */
    public Page(int pageNum, int pageSize,int totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageIndex = (pageNum - 1) * pageSize;
        this.totalCount = totalCount;
        // 方式1:计算总共的页数
        this.totalPages = totalCount % pageSize == 0 ? totalCount / pageSize
                : totalCount / pageSize + 1;
        // 方式2:计算总共的页数 
        //this.totalPages = (totalCount + pageSize - 1) / pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public void setParameterUrl(String parameterUrl) {
        this.parameterUrl = parameterUrl;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 在下面的更新的项目中实现; 并且将现在的map更换为从request中获取,增强其扩展性; 
     */
    public String getParameterUrl() throws UnsupportedEncodingException {
        this.parameterUrl = "";
        if(parameterMap!=null&&!parameterMap.isEmpty()){
            for (Entry<String, String> entry : parameterMap.entrySet()) {
                String parameterName = entry.getKey();
                String parameterValue = entry.getValue();
                // 手动对中文内容编码 TODO 
//                parameterValue = URLEncoder.encode(parameterValue, "utf-8");
                if (parameterUrl.equals("")) {
                    parameterUrl += "&" +  parameterName + "=" + parameterValue;
                } else {
                    parameterUrl += "&" + parameterName + "=" + parameterValue;
                }
            }
        }
        if(logger.isInfoEnabled()){
            logger.info("GET方式访问的URL请求参数为："+JSON.toJSONString(parameterUrl));
        }
        return parameterUrl;
    }

}
