package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.common.models.RollPage;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 */
@CompileStatic
@TypeChecked
public interface IBaseService<M>{

    /**
     * 增加记录
     * @param obj
     * @throws Exception
     */
    public <K> K addBasic(M obj) throws Exception;

    /**
     * 修改记录
     * @param obj
     * @throws Exception
     */
    public void modifyBasic(M obj) throws Exception;

    /**
     * 删除记录
     * @param obj
     * @throws Exception
     */
    public  void delBasic(M obj) throws Exception;

    /**
     * 根据主键查询记录
     * @param seq
     * @return
     * @throws Exception
     */
    public <K> M  findObjByKey(K seq) throws Exception;

    /**
     * 根据条件查询记录
     * @param params
     * @return
     * @throws Exception
     */
    public Object findObj(Map<String, Object> params) throws Exception;


    /**
     * 根据条件查询 ，返回记录总数
     * @param params
     * @return
     */
    public Integer findCountByParams(Map<String, Object> params);

    /**
     * 根据条件查询列表
     * @param params
     * @param order
     * @return
     * @throws Exception
     */
    public List<M> findListByParams(Map<String, Object> params, Order order) throws Exception;

    /**
     * 根据条件查询列表
     * @param params
     * @param order
     * @param maxRecord
     * @return
     * @throws Exception
     */
    public List<M> findListByParams(Map<String, Object> params, Order order, Integer maxRecord) throws Exception;

    /**
     * 根据条件查询 列表（分页查询）
     * @param params
     * @param order
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public RollPage<M> findListPageByParams(Map<String, Object> params, Order order, Integer pageNum, Integer pageSize)throws Exception;

}