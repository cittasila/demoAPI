package com.langying.common.models;

import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
@CompileStatic
@TypeChecked
public class RollPage<T> {

    private List<T> resultList; //记录列表

    private Integer iTotalRecords; //记录总数

    private Integer iTotalDisplayRecords; //显示记录总数

    private Integer pageSize; //分页参数

    private Integer pageNum; //当前页数

    private Integer pageSum; // 总页数

    public RollPage(Integer iTotalRecords, Integer iTotalDisplayRecords, Integer pageSize, Integer pageNum) {
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public Integer getPageSum() {
        if(this.pageSize!=0)
           return   (int) Math.ceil(this.iTotalRecords / this.pageSize); //+ ((this.iTotalRecords % this.pageSize==0)?0:1)
        else
           return  0;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public Integer getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Integer iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public Integer getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
