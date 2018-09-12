package cn.org.continent.constant;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 常量定义
 * @date 2018/8/24 11:10
 */
public class SearchParamConstant {
    //分页查询默认页码,大小
    public static final int SEARCH_DEFAULT_PAGE_NUM = 1;
    public static final int SEARCH_DEFAULT_PAGE_SIZE = 3;

    //查询连接符
    public static final String SEARCH_JOIN_AND = "and";
    public static final String SEARCH_JOIN_OR = "or";

    //升序
    public static final String SEARCH_SORT_ASC = "ASC";
    public static final String SEARCH_SORT_ASC_LOWER = "asc";

    //降序
    public static final String SEARCH_SORT_DESC_CONNECT = "-";
    public static final String SEARCH_SORT_DESC = "DESC";
    public static final String SEARCH_SORT_DESC_LOWER = "desc";
}
