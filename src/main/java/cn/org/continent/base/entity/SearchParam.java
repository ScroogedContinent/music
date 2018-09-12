package cn.org.continent.base.entity;

import cn.org.continent.constant.SearchParamConstant;

import java.io.Serializable;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 搜索分页条件构造器
 * @date 2018/8/24 11:02
 */
public class SearchParam implements Serializable {

    /**
     * 全文检索字段
     */
    private String fulltext;
    /**
     * 排序
     */
    private String[] sorts;
    /**
     * 当前页码
     */
    private int pageNum = SearchParamConstant.SEARCH_DEFAULT_PAGE_NUM;
    /**
     * 页码大小
     */
    private int pageSize = SearchParamConstant.SEARCH_DEFAULT_PAGE_SIZE;

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String[] getSorts() {
        return sorts;
    }

    public void setSorts(String[] sorts) {
        this.sorts = sorts;
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

    @Override
    public String toString() {
        return String.format("{pageSize:%s ,pageNum:%s ,sorts:%s ,fulltext:%s}", getPageSize(), getPageNum(), getSorts(), getFulltext());
    }

}
