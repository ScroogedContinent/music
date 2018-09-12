package cn.org.continent.base.service.impl;

import cn.org.continent.annotation.OrderField;
import cn.org.continent.base.entity.DataTable;
import cn.org.continent.base.service.IBaseService;
import cn.org.continent.base.util.SearchConditionWrapper;
import cn.org.continent.util.ClassKit;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/24 12:02
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public DataTable<T> pageSearch(DataTable<T> dataTable) {
        return pageSearch(dataTable, new Condition());
    }

    @Override
    public DataTable<T> pageSearch(DataTable<T> dataTable, Condition condition) {
        if( null == dataTable ){
            dataTable = new DataTable<>();
        }
        if( null == condition ){
            condition = new Condition();
        }

        //页码、页大小
        Page<T> page = new Page<>(dataTable.getPageNum(), dataTable.getPageSize());

        //多字段全文搜索
        SearchConditionWrapper.loadFulltext(currentModelClass(), dataTable.getFulltext(), condition);
        //排序
        SearchConditionWrapper.loadSort(getDataSorts(dataTable.getSorts()), condition);

        //执行分页查询
        selectPage(page, condition);
        dataTable.setTotal((int)page.getTotal());
        dataTable.setRows(page.getRecords());
        return dataTable;
    }

    @Override
    public List<T> searchList(DataTable<T> dataTable) {
        return searchList(dataTable, new Condition());
    }

    @Override
    public List<T> searchList(DataTable<T> dataTable, Condition condition) {
        if(null != dataTable){
            //多字段全文搜索
            SearchConditionWrapper.loadFulltext(currentModelClass(), dataTable.getFulltext(), condition);
            //排序
            SearchConditionWrapper.loadSort(getDataSorts(dataTable.getSorts()), condition);
        }
        return this.selectList(condition);
    }

    /**
     * 判断当前的用户有没有发排序的字段
     * @param sorts
     * @return
     */
    public Map<String, String> getDataSorts(Map<String, String> sorts){
        if(MapUtils.isEmpty(sorts)){
            return handleSortsField();
        }
        return sorts;
    }

    /**
     * 获取父类中带有@OrderField注解的字段，作为默认的排序字段
     * @return
     */
    public Map<String, String> handleSortsField(){
        Map<String, String> map = new HashMap<>();
        Type[] actualTypeArguements = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        for( Type type : actualTypeArguements ) {
            Class<?> clazz = (Class<?>) type;
            List<Field> fields = ClassKit.getClassFieldByAnnotation(clazz, OrderField.class);
            try {
                fields.forEach(field -> {
                    if( field.getAnnotation(OrderField.class).primary() ){
                        map.clear();
                        map.put(field.getName(), field.getAnnotation(OrderField.class).value());
						//跳出当前的循环
                        throw new RuntimeException();
                    }
                    map.put(field.getName(), field.getAnnotation(OrderField.class).value());
                });
            } catch (Exception e) {}
        }
        return map;
    }
}
