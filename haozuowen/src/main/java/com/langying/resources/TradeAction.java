package com.langying.resources;

import com.langying.common.contant.CommonConstant;
import com.langying.common.models.RollPage;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.service.ITradeService;
import com.langying.handler.ResponseHandler;
import com.langying.models.UUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/4/19.
 */
@RestController
@Api("查询交易记录（金币使用、人民币充值）")
@RequestMapping("/trade")
public class TradeAction {

    private static final Logger logger = LoggerFactory.getLogger(TradeAction.class);

    @Autowired
    private ITradeService tradeService;


    /**
     * 分页查询用户金币使用记录
     * 目前金币消耗的情况仅当在用户使用金币购买书籍的时候
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "金币使用记录", notes = "buy_time 购买时间；gold_num 使用金币数；info 购买哪本书",response = ResponseHandler.class)
    @RequestMapping(value = "/gold", method = RequestMethod.GET)
    public ResponseHandler gold(@ApiParam("页号")@RequestParam(name = "pageNum",required = true)Integer pageNum,
                             @ApiParam("页面记录数")@RequestParam(name = "pageSize",required = true)Integer pageSize) {
        logger.debug("第几页 pageNum => " + pageNum);
        logger.debug("每页显示几条数据 pageSize => " + pageSize);
        UUser user = UserDetailHelper.getUserDetail();
        Map<String,Object> params = new HashMap<>();
        params.put("user_id",user.getUserId());
        RollPage<Map<String,Object>> goldDetailRollPage  = tradeService.findGoldRecordByParams(params,pageNum,pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("goldDetailRollPage", goldDetailRollPage);
        return ResponseHandler.getResponse(result);
    }

    // 查询人民币充值记录（recharge）

    /**
     * 分页查询用户金币使用记录
     * 目前金币消耗的情况仅当在用户使用金币购买书籍的时候
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "人民币充值记录",notes = "total_fee 金币数；order_type 订单类型： 1 支付宝支付，2 微信支付；pay_time 下单时间" , response = ResponseHandler.class)
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public ResponseHandler recharge(@ApiParam("页号")@RequestParam(name = "pageNum",required = true)Integer pageNum,
                                @ApiParam("页面记录数")@RequestParam(name = "pageSize",required = true)Integer pageSize) {
        logger.debug("第几页 pageNum => " + pageNum);
        logger.debug("每页显示几条数据 pageSize => " + pageSize);
        UUser user = UserDetailHelper.getUserDetail();
        Map<String,Object> params = new HashMap<>();
        params.put("user_id",user.getUserId());
        RollPage<Map<String,Object>> rechargeDetailRollPage  = tradeService.findRechargeRecordByParams(params,pageNum,pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("rechargeDetailRollPage", rechargeDetailRollPage);
        return ResponseHandler.getResponse(result);
    }


}
