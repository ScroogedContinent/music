package cn.org.continent.base.service;

import cn.org.continent.base.entity.DataTable;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/24 12:01
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 分页
     * @param dataTable
     * @return
     */
    DataTable<T> pageSearch(DataTable<T> dataTable);

    DataTable<T> pageSearch(DataTable<T> dataTable, Condition condition);

    /**
     * 不带分页
     * @param dataTable
     * @return
     */
    List<T> searchList(DataTable<T> dataTable);

    List<T> searchList(DataTable<T> dataTable, Condition condition);
}
