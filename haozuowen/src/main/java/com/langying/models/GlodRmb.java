package com.langying.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/31.
 */
@ApiModel("人民币与金币关系")
public class GlodRmb {

    private int id;
    @ApiModelProperty("人民币金额")
    private int rmb;
    @ApiModelProperty("金币数量")
    private int goldAmount;

    private static Map rmbgoldMap;

    public GlodRmb(int id, int rmb, int goldAmount) {
        this.id = id;
        this.rmb = rmb;
        this.goldAmount = goldAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }
    public static List<GlodRmb> getAmountList(){
        List<GlodRmb> glodRmbs=new ArrayList<>();
        glodRmbs.add(new GlodRmb(1,1,10));
        glodRmbs.add(new GlodRmb(2,5,50));
        glodRmbs.add(new GlodRmb(3,10,100));
        glodRmbs.add(new GlodRmb(4,20,200));
        glodRmbs.add(new GlodRmb(5,100,1000));
        return glodRmbs;
    }

    public static Map<Integer,GlodRmb> getRmbgoldMap() {
        if(rmbgoldMap==null||rmbgoldMap.size()==0){
            rmbgoldMap=new HashMap();
            for (GlodRmb glodRmb:getAmountList()){
                rmbgoldMap.put(glodRmb.getId(),glodRmb);
            }
        }
        return rmbgoldMap;
    }
}
