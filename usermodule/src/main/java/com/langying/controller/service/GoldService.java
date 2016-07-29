package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.RUserGoldDetailMapper;
import com.langying.controller.mapper.RUserGoldMapper;
import com.langying.controller.mapper.UUserMapper;
import com.langying.models.RUserGold;
import com.langying.models.RUserGoldDetail;
import com.langying.models.UClasses;
import com.langying.models.UUser;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Liwei on 2016/3/31.
 */
@Service
public class GoldService extends BaseService<RUserGold> implements IGoldService{


    private static Logger logger = LoggerFactory.getLogger(GoldService.class);

    @Autowired
    private GoldDetailService goldDetailService;


    private RUserGoldMapper rUserGoldMapper;
    @Autowired
    public void setrUserGoldMapper(RUserGoldMapper rUserGoldMapper) {
        this.rUserGoldMapper = rUserGoldMapper;
        super.baseMapper=rUserGoldMapper;
    }

    @Autowired
    private RUserGoldDetailMapper rUserGoldDetailMapper;


    @Override
    public RUserGold selectByParams(@Param(value = "params") Map params) {
        return rUserGoldMapper.selectByParams(params);
    }

    /**
     * 用户首次登陆的时候初始化用户金币表，并为用户金币明细表增加一条记录
     * @param userId
     * @param initGoldNum
     * @return
     */

    @Override
    public Boolean initGoldAndDetail(Integer userId, Integer initGoldNum) {
        Boolean flag = false;
        // 为用户插入一条金币账号数据，并且插入一条用户金币明细记录
        Integer insertNum1 =  initUserGold(userId,initGoldNum);
        if(insertNum1 == 1){
            logger.debug("成功创建了 userId => " + userId + " 的用户金币表数据，初始化金币数量 initGoldNum => " + initGoldNum);
        }
        RUserGoldDetail rUserGoldDetailAdd = new RUserGoldDetail();
        rUserGoldDetailAdd.setUserId(userId);
        rUserGoldDetailAdd.setIsActive("1");
        rUserGoldDetailAdd.setBasicGold(0);
        rUserGoldDetailAdd.setGoldChange(Integer.valueOf(initGoldNum));
        rUserGoldDetailAdd.setDetailType(CommonConstant.UserGoldDetailType.INIT.getValue());
        Integer insertNum2 =
                goldDetailService.addUserGoldDetail(rUserGoldDetailAdd);
        if(insertNum2 == 1){
            logger.debug("为用户金币明细表增加了一条数据， userId => " + userId + " 增加了金币数量 initGoldNum => " + initGoldNum);
        }
        if(insertNum1 == 1 && insertNum2 == 1){
            flag = true;
        }
        return flag;
    }


    /**
     * 在用户登录的时候检查是否创建了金币账号（即 r_user_gold 中是否有对应的 user_id 数据），
     * 初始化逻辑，一个山东好作文用户只会执行一次
     * @param userId
     * @param initGoldNum
     * @return
     */
    private Integer initUserGold(Integer userId,Integer initGoldNum){
        RUserGold rUserGoldAdd  = new RUserGold();
        rUserGoldAdd.setUserId(userId);
        rUserGoldAdd.setIsActive("1");
        rUserGoldAdd.setGoldNum(Integer.valueOf(initGoldNum));
        Integer insertNum = 0;
        try {
            // 返回插入数据的条数
            insertNum = rUserGoldMapper.insertSelective(rUserGoldAdd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertNum;
    }


    @Override
    public Integer addBasic(RUserGold obj) throws Exception {
         rUserGoldMapper.insert(obj);
        return obj.getUserGoldId();
    }

    @Override
    public void modifyBasic(RUserGold obj) throws Exception {
        rUserGoldMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public void delBasic(RUserGold obj) throws Exception {

    }
}
