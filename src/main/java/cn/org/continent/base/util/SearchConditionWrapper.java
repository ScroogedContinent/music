package cn.org.continent.base.util;

import cn.org.continent.annotation.Fulltext;
import cn.org.continent.constant.SearchParamConstant;
import cn.org.continent.util.ListOptionHelper;
import cn.org.continent.util.StringKit;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 条件查询构造
 * @date 2018/8/26 10:14
 */
public class SearchConditionWrapper {

    private static final String BUILD_SQL_STR = "sqlStr";
    private static final String BUILD_SQL_PARAMS = "sqlParam";

    public static void loadSearchParam(Object searchParams, Condition condition){
        Optional.ofNullable(searchParams).ifPresent(s ->{

            /**
             * 常规多字段联合查询
             * 默认是字符模糊，数字类型为等于
             */
            // TODO: 2018/8/26
           // if( s instanceof JSON)
        });
    }

    public static void loadFulltext(Class clazz, String fulltext, Condition condition){
        Set<String> columnSet = new HashSet<>();

        Annotation clazzAnnotation = clazz.getAnnotation(Fulltext.class);
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        for ( Field field : fields ) {
            if( null != clazzAnnotation || null != field.getAnnotation(Fulltext.class)){
                columnSet.add(field.getName());
            }
        }

        Method[] methods = clazz.getDeclaredMethods();
        for ( Method method : methods ) {
            if( null!= clazzAnnotation && null != method.getAnnotation(Fulltext.class)){
                try {
                    Object resultObj = method.invoke(clazz.newInstance(), (Object[]) null);
                    if( null != resultObj && resultObj instanceof String[] ){
                        columnSet.addAll(Stream.of((String[]) resultObj).collect(Collectors.toSet()));
                    }else if( null != resultObj && resultObj instanceof Collection ){
                        columnSet.addAll((Collection) resultObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        loadFulltext(columnSet, fulltext, condition);
    }

    /**
     * 加载多字段全文检索
     * @param collection
     * @param fulltext
     * @param condition
     */
    public static void loadFulltext(Collection<String> collection, String fulltext, Condition condition){
        if( !StringUtils.isEmpty(fulltext) ){
            String[] ftext = ListOptionHelper.stringToArrayByBlank(fulltext);

            int count = 0;
            for(String column : collection){
                String ulColumn = StringKit.toUnderlineCase(column);

                if( count == 0 ){
                    condition.andNew();
                }

                int index = 0;
                for ( String val : ftext ) {
                    if( !(count == 0 && index == 0) ){
                        condition.or();
                    }
                    condition.like(ulColumn, val);
                    index++;
                }
                count++;
            }
        }
    }

    /**
     * 加载 排序条件
     * @param sorts
     * @param condition
     */
    public static void loadSort(Map<String, String> sorts, Condition condition){

        if( MapUtils.isNotEmpty(sorts) ){
            StringBuffer buffer = new StringBuffer();
            sorts.forEach((k,v)->{
                buffer.append(StringKit.toUnderlineCase(k));
                buffer.append(SearchParamConstant.SEARCH_SORT_ASC_LOWER.equals(v.toLowerCase()) ? " " + SearchParamConstant.SEARCH_SORT_ASC + " , " : " " + SearchParamConstant.SEARCH_SORT_DESC + " , ");
            });
            condition.orderBy(buffer.toString().trim().substring(0, buffer.length()-2));
        }
    }
}
