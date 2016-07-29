package com.langying.resources;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.service.*;
import com.langying.exception.ApiException;
import com.langying.handler.ResponseHandler;
import com.langying.models.*;

import com.langying.payment.common.*;
import com.langying.payment.common.alipay.AlipayNotify;
import com.langying.toolbox.utils.IpUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenxu on 2016/3/8.
 */
@RestController
@Api("支付信息管理")
@RequestMapping("/pay")
class PayAction {


    private static final Logger logger = LoggerFactory.getLogger(PayAction.class);

    @Autowired
    IArticleInfoService articleInfoService;

    @Autowired
    private IGoldService goldService;

    @Autowired
    private IUserBookService userBookService;

    @Autowired
    IOrderService orderService;

    @Autowired
    private Environment environment;




    @ApiOperation(value = "人民币与金币关系列表",response = GlodRmb.class,responseContainer = "List")
    @RequestMapping(value = "/amountList",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler amountList(){
        return ResponseHandler.getResponse(CommonConstant.getResMap("list", GlodRmb.getAmountList()));
    }

    @ApiOperation(value = "用户财产",notes = "查询用户金币数量" ,response = UUser.AmountUserView.class)
    @RequestMapping(value = "/userPatrimony",method = RequestMethod.GET)
    @ResponseBody
    ResponseHandler userPatrimony(){
        UUser user=UserDetailHelper.getUserDetail();
        RUserGold userGold= goldService.selectByParams(CommonConstant.getResMap("userId",user.getUserId()));
        int amount=0;
        if(userGold!=null){
            amount=userGold.getGoldNum();
        }
        return ResponseHandler.getResponse(CommonConstant.getResMap("amount",amount));
    }

    /***
     * 购买读物接口：
     * 先查询读物是否存在
     * 然后查询总金币数量
     * 插入购买记录,用户与文章的关系
     * @param bookId
     * @return
     */
    @ApiOperation(value="购买" ,notes = "用金币购买书籍" ,response = UUser.AmountUserView.class)
    @RequestMapping(value = "/purchase",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler purchase(@ApiParam("书籍ID")@RequestParam(name = "bookId",required = true)String bookId) throws Exception {
        UUser user=UserDetailHelper.getUserDetail();
        Integer userId=user.getUserId();
        synchronized (userId) {
            Integer amount=userBookService.purchase(bookId,userId);
            return ResponseHandler.getResponse(CommonConstant.getResMap("amount",amount));
        }
    }

    @ApiOperation(value="下单" ,notes = "支付宝/微信下单,1：支付宝，2：微信" )
    @RequestMapping(value = "/order",method = RequestMethod.POST)
    @ResponseBody
    ResponseHandler order(@ApiParam(value = "类型",allowableValues = "1,2")@RequestParam(name = "type",required = true) String type,@ApiParam("关系编号")@RequestParam(name = "amountId",required = true) Integer amountId, HttpServletRequest request) throws ApiException {
        if(CommonConstant.OrderType.getOrderType(type)==null){
            throw new ApiException("9019");
        }
        String orderNo=RandomStringGenerator.getNo(type);
        while (orderService.tradeNo(orderNo)!=null){
            orderNo=RandomStringGenerator.getNo(type);
        }
        String ip=IpUtil.getIpAddr(request);
        if(type.equals("2")){
            /**
             * 异步通知地址
             */
            String url=request.getRequestURL().toString().replaceAll("order","notify/"+orderNo);
            orderService.order(orderNo,amountId,ip,url);
            return ResponseHandler.getResponse(CommonConstant.getResMap("orderNo",orderNo,"url",request.getRequestURL().toString().replaceAll("order","qrcode/"+orderNo)));
        }else if (type.equals("1")){
            logger.info("------ 支付宝下单 ------");
            Map<String,String> alipayResult = orderService.orderOfAlipay(orderNo,amountId,ip);
            String result_url = alipayResult.get("result_url");
            logger.info("result_url => " + result_url);
            return ResponseHandler.getResponse(CommonConstant.getResMap("orderNo",orderNo,"url",result_url));
        }else {
            return null;
        }
    }

    @ApiOperation(value="二维码" ,notes = "微信二维码")
    @RequestMapping(value = "/qrcode/{orderNo}",method = RequestMethod.GET)
    void qrcode(@PathVariable String orderNo, HttpServletResponse response) throws ApiException {

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        Map map=orderService.tradeNo(orderNo);
        if(map!=null) {
            try {
                QRCodeUtil.qrcode(map.get("code_url").toString(), response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else
            throw new ApiException("9021");

    }

    /**
     * 支付宝、微信异步通知统一地址（支付宝处理完成后主动发起回调通知，把处理的结果信息通知给朗鹰）
     * @param request
     * @param orderNo
     * @param out
     * @throws Exception
     */
    @ApiOperation(value="异步通知" ,notes = "异步通知")
    @RequestMapping(value = "/notify/{orderNo}",method = RequestMethod.POST)
    void notify(HttpServletRequest request,@PathVariable String orderNo,PrintWriter out) throws Exception {
        // 异步通知来自何处（0：支付宝，1：微信）
        String type=orderNo.substring(0,1);
        logger.debug("来自何处的异步通知：" + type);
        CommonConstant.OrderType orderType=CommonConstant.OrderType.getOrderType(type);
        if(orderType==null){
            throw new ApiException("9019");
        }
        if(orderType.equals(CommonConstant.OrderType.WeiXin)) {
            String xml = IOUtils.toString(request.getInputStream(), "UTF-8");
            logger.info("微信返回xml:"+xml);
            orderService.notify(orderNo, out, xml);
        }
        // 处理支付宝即时到帐逻辑
        if(orderType.equals(CommonConstant.OrderType.Alipay)){
            logger.info("开始接收支付宝异步通知的信息。");
            orderService.notifyOfAlipay(orderNo,request,out);

            logger.info("支付宝异步通知处理完毕。");
        }

    }



    @ApiOperation(value="根据订单号查询订单交易状态",notes = "orderStatus 0：交易创建，等待卖家付款，1：交易关闭，2：交易成功，3：等待卖家收款（如果卖家账号被冻结），4：交易成功且结束")
    @RequestMapping(value = "getOrderStatusByOrderId",method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandler getOrderStatusByOrderId(@ApiParam("订单id")@RequestParam(name = "orderNo",required = true)String orderNo) throws Exception{
        String orderStatus=null;
        TUserOrder userOrder=(TUserOrder)orderService.findObj(CommonConstant.getResMap("tradeNo",orderNo));
        if(userOrder!=null){
            orderStatus=userOrder.getStatus();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("orderStatus",orderStatus);
        return ResponseHandler.getResponse(result);
    }


    @RequestMapping(value = "/return",method = RequestMethod.GET)
    public void payReturn(HttpServletResponse response) throws IOException {
        String closeWindowUrl = environment.getProperty("alipay.close.window");
        response.sendRedirect(closeWindowUrl);
    }
}
