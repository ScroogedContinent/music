package cn.org.continent.base.entity;

import cn.org.continent.constant.SearchParamConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.*;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/24 12:10
 */
public class DataTable<T> implements Serializable {
    private static final long serialVersionUID = 2252240868205663450L;

    /**
     * 搜素条件
     */
    // TODO: 2018/8/24 暂时未支持
    //private Object searchParam;

    /**
     * 全文搜索字段
     */
    private String fulltext;
    /**
     * 表中全文搜索字段
     */
    private String[] ftCols;
    /**
     * 返回的列表
     */
    private List<T> rows = new ArrayList<>();
    /**
     * 排序条件
     */
    private Map<String, String> sorts;
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 页码大小
     */
    private int pageSize;
    /**
     * 总数
     */
    private int total;

    public DataTable() {
    }

    public DataTable(SearchParam searchParam) {
        this(searchParam, new String[]{});
    }

    /**
     * 构造带分页的查询条件
     * @param searchParam
     * @param ftCols
     */
    public DataTable(SearchParam searchParam, String[] ftCols) {
        /**
         * 页码和每页显示设置
         */
        this.pageNum = searchParam.getPageNum();
        this.pageSize = searchParam.getPageSize();

        createSorts(searchParam.getSorts());

        /**
         * 多字段全文搜索内容
         */
        setFulltext(searchParam.getFulltext());

        Optional.ofNullable(ftCols).ifPresent(s -> {
            setFtCols(ftCols);
        });
    }

    /**
     * 构造排序条件
     * @param sorts
     */
    protected void createSorts(String[] sorts) {
        Map<String, String> sortsMap = new HashMap<>();

        Optional.ofNullable(sorts).ifPresent(s ->{
            for (String sort : s ) {
                if(sort.startsWith(SearchParamConstant.SEARCH_SORT_DESC_CONNECT)){
                    //降序
                    sortsMap.put(sort.replaceFirst(SearchParamConstant.SEARCH_SORT_DESC_CONNECT, ""), SearchParamConstant.SEARCH_SORT_DESC);
                }else{
                    //升序
                    sortsMap.put(sort, SearchParamConstant.SEARCH_SORT_ASC);
                }
            }
        });
        this.setSorts(sortsMap);
    }

    @JsonIgnore
    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    @JsonIgnore
    public String[] getFtCols() {
        return ftCols;
    }

    public void setFtCols(String[] ftCols) {
        this.ftCols = ftCols;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @JsonIgnore
    public Map<String, String> getSorts() {
        return sorts;
    }

    public void setSorts(Map<String, String> sorts) {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
