package com.cw.bookappointment.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 一个通用的泛型化的DAO接口， <br/>
 * 该接口里包含了比较通用的CRUD操作的方法声明。 <br/>
 * 通过继承该接口，<br/>
 * 可以 使你的DAO接口免去声明这些比较通用的CRUD方法的工作。<br/>
 * </p>
 * 
 * @company: sohu
 * @author: jingzhiliu
 * @date: 2011-7-19 下午06:33:04
 * @version: v_0.1
 * @version: 0.2 2012-08-08 huanghaixin
 */
public interface IGenericDao<T, ID extends Serializable> {

    /**
     * 添加新实体
     * 
     * @param t
     * @return
     * @throws Exception
     */
    void save(T t);

    /**
     * 批量添加新实体
     * 
     * @param list
     * @return
     * @throws Exception
     */
    void batchSave(List<T> list);

    /**
     * 删除实体（软册除status=2）
     * 
     * @param id
     * @throws Exception
     */
    void delete(ID id);

    /**
     * 批量删除实体（软删除status=2）
     * 
     * @param list
     * @throws Exception
     */
    void batchDelete(List<ID> list);

    /**
     * 修改实体
     * 
     * @param t
     * @return
     * @throws Exception
     */
    void update(T t);

    /**
     * 通过ID获取实体
     * 
     * @param id
     * @return
     * @throws Exception
     */
    T get(ID id);

    /**
     * 通过ID获取实体列表
     * @param id
     * @return
     * @throws Exception
     */
    List<T> getList(ID id) throws Exception;
    
    /**
     * <p>
     * 不带分页的列表查询。
     * </p>
     * 
     * @param t
     * @return
     */
    List<T> list(T t);

    /**
     * 通过id列表获取实体列表
     * 
     * @param ids
     * @return
     * @throws Exception
     */
    List<T> getbyIdList(@Param("ids") List<ID> ids);

    /**
     * 根据条件查记录数
     * 
     * @param t
     * @return
     * @throws Exception
     */
    int count(T t);

    /**
     * 检查是否重复
     * 
     * @param t
     * @return
     */
    boolean isDuplicated(T t);
    
    /**
     * 批量添加实体
     * @param list
     * @throws Exception
     */
    void batchAdd(List<T> list) throws Exception;
}
