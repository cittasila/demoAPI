package com.langying.controller.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.langying.common.contant.CommonConstant;
import com.langying.common.models.RollPage;
import com.langying.controller.common.UserDetailHelper;
import com.langying.controller.mapper.TUserOrderMapper;
import com.langying.exception.ApiException;
import com.langying.models.*;
import com.langying.payment.common.*;
import com.langying.payment.common.alipay.AlipayNotify;
import com.langying.payment.common.alipay.AlipaySubmit;
import com.langying.payment.common.alipay.model.Alipay;
import com.langying.payment.models.UnifiedOrderData;
import com.langying.payment.models.WeiXinOrderQueryData;
import com.langying.payment.service.IPayService;
import com.langying.toolbox.utils.DateUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.sf.json.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by chenxu on 2016/4/7.
 */
@Service
public class OrderService  extends  BaseService<TUserOrder> implements IOrderService{


    private TUserOrderMapper userOrderMapper;
    @Autowired
    void setMapper(TUserOrderMapper mapper) {
        this.userOrderMapper = mapper;
        super.baseMapper=userOrderMapper;
    }
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    IPayService payService;

    @Autowired
    private IUserOrderService userOrderService;

    @Autowired
    private IGoldService goldService;

    @Autowired
    private IGoldDetailService goldDetailService;

    @Cacheable(cacheNames ="payCode",key="'payCode'+#orderNo")
    @Override
    public Map tradeNo(String orderNo) {
        return null;
    }

    /**
     * 下单
     * @param orderNo 订单号
     * @param id      金额与金币关系id
     * @param ip       访问ip地址
     * @param url      异步通知
     * @return
     * @throws ApiException
     */
    @CachePut(cacheNames ="payCode",key="'payCode'+#orderNo")
    @Override
    public Map order(String orderNo, Integer id,String ip,String url) throws ApiException {
        UUser user=UserDetailHelper.getUserDetail();
        Map map=null;

        GlodRmb glodRmb= GlodRmb.getRmbgoldMap().get(id);
        if(glodRmb==null)
            throw new ApiException("9018");
        String subject="有氧英语充值"+glodRmb.getGoldAmount()+"金币";
        try {
            TUserOrder userOrder=new TUserOrder();
            userOrder.setCreateTime(new Date());
            userOrder.setUserId(user.getUserId());
            userOrder.setStatus("0");
            userOrder.setTotalFee((float)glodRmb.getRmb());
            userOrder.setOrderType("2");
            userOrder.setGoldNum(glodRmb.getGoldAmount());
            userOrder.setTradeNo(orderNo);
            userOrder.setSubject(subject);
            userOrderService.addBasic(userOrder);
            map=payService.unifiedOrder(orderNo,subject,glodRmb.getRmb()*100, Util.time(),ip,user.getUserId().toString(),url);//微信
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(map!=null&&(map.containsKey("result_code")&&"SUCCESS".equals(map.get("result_code")))) {
            return map;
        }else if(map!=null&&(map.containsKey("result_code")&&"FAIL".equals(map.get("result_code")))) {
                throw new ApiException("9020",map.get("return_msg").toString());
        }else {
            throw new ApiException("9999");
        }
    }

    @Cacheable(cacheNames ="payCode",key="'payCode'+#orderNo")
    @Override
    public String qrCode(String orderNo, Map data) {
        return null;
    }

    /**
     * 异步微信通知
     * @param orderNo
     * @param out
     * @param xml
     * @throws Exception
     */
    @Override
    public void notify(String orderNo, PrintWriter out, String xml) throws Exception {
        XStream xStream = new XStream(new DomDriver());
        Map map =new HashMap();
        xStream.registerConverter(new PojoMapConverter());
        String returnXml="";
        map= (Map<String,Object>)xStream.fromXML(xml.replaceAll("<xml>","<map>").replaceAll("</xml>","</map>"));
        if(map.containsKey("sign")){
            String sign=map.get("sign").toString();
            map.remove("sign");
            if(sign.equals(Signature.getSign(map))){
                map.put("sign",sign);
                map.put("xml",xml);
               TUserOrder userOrder= (TUserOrder)userOrderService.findObj(CommonConstant.getResMap("tradeNo",orderNo));
                if(orderNo!=null){
                    if(!"4".equals(userOrder.getStatus())) {
                        if (map.containsKey("result_code") && map.containsKey("return_code") && "SUCCESS".equals(map.get("result_code")) && "SUCCESS".equals(map.get("result_code"))) {
                            TUserOrder nUserOrder = new TUserOrder();
                            nUserOrder.setOrderId(userOrder.getOrderId());
                            nUserOrder.setOrderInfo(xml);
                            nUserOrder.setStatus("4");
                            nUserOrder.setUpdateTime(new Date());
                            userOrderService.modifyBasic(nUserOrder);
                            Integer integer = goldDetailService.findCountByParams(CommonConstant.getResMap("goldOrderId", userOrder.getOrderId()));
                            if (integer == 0) {
                                RUserGold userGold = (RUserGold) goldService.findObj(CommonConstant.getResMap("userId", userOrder.getUserId()));
                                RUserGoldDetail userGoldDetail = new RUserGoldDetail();
                                userGoldDetail.setUserGoldDetailId(userOrder.getOrderId());
                                userGoldDetail.setUserId(userOrder.getUserId());
                                userGoldDetail.setGoldChange(userOrder.getGoldNum());
                                userGoldDetail.setBasicGold(userGold==null?0:userGold.getGoldNum());
                                userGoldDetail.setDetailType(CommonConstant.UserGoldDetailType.USERRECHARGE.getValue());
                                userGoldDetail.setGoldOrderId(userOrder.getOrderId());
                                goldDetailService.addBasic(userGoldDetail);
                                if(userGold!=null) {
                                    userGold.setUpdateTime(new Date());
                                    userGold.setGoldNum(userGold.getGoldNum() + userOrder.getGoldNum());
                                    goldService.modifyBasic(userGold);
                                }else {
                                    userGold=new RUserGold();
                                    userGold.setGoldNum(userOrder.getGoldNum());
                                    userGold.setUpdateTime(new Date());
                                    userGold.setCreateTime(new Date());
                                    userGold.setIsActive("1");
                                    userGold.setUserId(userOrder.getUserId());
                                    goldService.addBasic(userGold);
                                }
                            }
                        }
                    }
                    returnXml=Util.toWeiXinXml(true,null);
                }else {
                    returnXml=Util.toWeiXinXml(false,"订单不存在");
                }
            }else {
                returnXml=Util.toWeiXinXml(false,"签名错误");
            }
        }else
            returnXml=Util.toWeiXinXml(false,"格式错误");
        logger.info("订单通知"+orderNo+":");
        logger.info(returnXml);
        out.append(returnXml);
        out.close();
    }

    /**
     *
     * 处理支付宝的异步通知
     *
     * 处理来自支付宝的异步通知
     * 可参考技术文档中页面跳转同步通知参数列表
     * @param orderNo
     * @param request
     * @param out
     * @throws Exception
     */
    @Override
    public void notifyOfAlipay(String orderNo, HttpServletRequest request, PrintWriter out) throws Exception {
        // 获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        // 对应商户网站的订单系统中的唯一订单号（我们的订单号）
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //
        // 该交易在支付宝系统中的交易流水号。最长64位。（支付宝交易号）
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        logger.info("重要参数 trade_status => " + trade_status);

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            // 【普通即时到账的交易成功状态】
            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序



                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    // 【开通了高级即时到账或机票分销产品后的交易成功状态】
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知
                // 根据订单号查询订单信息
                TUserOrder userOrder = (TUserOrder) userOrderService.findObj(CommonConstant.getResMap("tradeNo", out_trade_no));
                // 如果订单号非空
                if (orderNo != null) {
                    // 如果订单状态不是 4 （交易成功且结束）
                    if (!"4".equals(userOrder.getStatus())) {

                        TUserOrder nUserOrder = new TUserOrder();
                        nUserOrder.setOrderId(userOrder.getOrderId());

                        ObjectMapper mapper = new ObjectMapper();
                        String requestInfoJson = mapper.writeValueAsString(params);
                        nUserOrder.setOrderInfo(requestInfoJson);

                        nUserOrder.setStatus("4");
                        nUserOrder.setUpdateTime(new Date());
                        // 更新 t_user_info 信息
                        userOrderService.modifyBasic(nUserOrder);
                        // 先根据订单 id 查询用户金币明细表
                        Integer integer = goldDetailService.findCountByParams(CommonConstant.getResMap("goldOrderId", userOrder.getOrderId()));
                        if (integer == 0) { // 在支付成功的前提下，如果没有查询到该订单的明细，须要添加一条记录
                            RUserGold userGold = (RUserGold) goldService.findObj(CommonConstant.getResMap("userId", userOrder.getUserId()));
                            RUserGoldDetail userGoldDetail = new RUserGoldDetail();
                            // todo
                            userGoldDetail.setUserGoldDetailId(userOrder.getOrderId());
                            userGoldDetail.setUserId(userOrder.getUserId());
                            userGoldDetail.setGoldChange(userOrder.getGoldNum());
                            userGoldDetail.setBasicGold(userGold == null ? 0 : userGold.getGoldNum());
                            userGoldDetail.setDetailType(CommonConstant.UserGoldDetailType.USERRECHARGE.getValue());
                            userGoldDetail.setGoldOrderId(userOrder.getOrderId());
                            goldDetailService.addBasic(userGoldDetail);
                            if (userGold != null) {
                                userGold.setUpdateTime(new Date());
                                userGold.setGoldNum(userGold.getGoldNum() + userOrder.getGoldNum());
                                goldService.modifyBasic(userGold);
                            } else {
                                userGold = new RUserGold();
                                userGold.setGoldNum(userOrder.getGoldNum());
                                userGold.setUpdateTime(new Date());
                                userGold.setCreateTime(new Date());
                                userGold.setIsActive("1");
                                userGold.setUserId(userOrder.getUserId());
                                goldService.addBasic(userGold);
                            }
                        }

                    }
                }

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                // todo 我的疑问：返回 success 和 返回 fail 有什么区别呢，对于支付宝来说
                // 如果返回了 “success” 支付宝就不会再发异步通知给我方服务器了，
                out.println("success");    //请不要修改或删除

                //////////////////////////////////////////////////////////////////////////////////////////
            } else {//验证失败
                // 返回 fail ，支付宝就会在规定的时间之内再发异步通知，让我们再处理这条消息。
                out.println("fail");
            }
        }
    }


    /**
     * 检查还未完成的订单
     * @return
     * @throws Exception
     */
    @Override
    public Map checkNotFinishOrder() throws Exception {
        Map<String,Object> map=new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("notStatus","'4'");
        // param.put("createTimeIsToday",DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        param.put("createTimeIsToday","2016-06-08");
        try {
            WeiXinOrderQueryData weiXinOrderQueryData;
            HttpsRequest request;
            String xml;
            XStream xStream;
            // 1、查询出今天之前的所有状态不是 4 的订单
            List<TUserOrder> userOrdersL=userOrderService.findListByParams(param,null);

            for(TUserOrder userOrderObj:userOrdersL){
                // 2（1） 在从这些订单中查找出是支付宝的订单，做相应的逻辑
                if("1".equals(userOrderObj.getOrderType())){//支付宝支付


                    // 请见支付宝《单笔交易查询接口》
                    // 请求参数
                    String service = "single_trade_query";
                    String partner = "2088511881019952";
                    String _input_charset = "utf-8";
                    String sign_type = "MD5";
                    String out_trade_no =userOrderObj.getTradeNo();

                    // 把请求参数放在一个临时 map 中
                    Map<String,String> sParaTemp = new HashMap<>();
                    sParaTemp.put("service",service);
                    sParaTemp.put("partner",partner);
                    sParaTemp.put("_input_charset",_input_charset);
                    sParaTemp.put("sign_type",sign_type);
                    sParaTemp.put("out_trade_no","12016060813462342");

                    // 使用支付宝的工具类 AlipaySubmit 的 buildRequestPara 方法
                    // 获得 MD5 签名校验参数 sign
                    Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp);


                    // 向支付宝网关发起请求

                    HttpClient client = HttpClients.createDefault();
                    HttpPost post = new HttpPost("https://mapi.alipay.com/gateway.do");
                    List<BasicNameValuePair> parameters = new ArrayList<>();
                    for(Map.Entry<String,String> entry:sPara.entrySet()){
                        logger.debug(entry.getKey() + "\t" + entry.getValue());
                        parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }
                    post.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
                    HttpResponse response = client.execute(post);
                    HttpEntity entity = response.getEntity();
                    String resultXML = EntityUtils.toString(entity);
                    logger.debug("支付宝单笔交易查询接口返回数据：" + resultXML);

                    XStream xStreamAlipay = new XStream();
                    xStreamAlipay.alias("alipay", Alipay.class);
                    xStreamAlipay.autodetectAnnotations(true);
                    Object object = xStreamAlipay.fromXML(resultXML);
                    Alipay alipay = (Alipay) object;
                    // “T”表示单笔数据查询“成功”返回，但不代表交易成功，须继续做判断
                    if("T".equals(alipay.getIsSuccess())){
                        String signType = alipay.getSign_type();
                        String sign = alipay.getSign();
                        Alipay.Response response1 = alipay.getResponse();
                        // todo 目前校验的代码还未调试成功
                        Map<String,String> tradeMap = convertBean(response1.getRrade());
                        /*tradeMap.put("is_success",alipay.getIsSuccess());
                        tradeMap.put("_input_charset","utf-8");
                        tradeMap.put("service","single_trade_query");
                        tradeMap.put("partner","2088511881019952");
                        tradeMap.put("out_trade_no","12016060813462342");
                        tradeMap.put("sign","68b1cbadb1de50997e4858db836e837e");
                        tradeMap.put("sign_type","MD5");

                        Map<String,String> tradeMap = new HashMap<>();
                        tradeMap.put("out_trade_no","12016060813462342");
                        tradeMap.put("partner","2088511881019952");
                        tradeMap.put("service","single_trade_query");
                        tradeMap.put("_input_charset","utf-8");
                        tradeMap.put("sign","68b1cbadb1de50997e4858db836e837e");
                        tradeMap.put("sign_type","MD5");*/

                        Boolean responseSign = AlipayNotify.verify(tradeMap);
                        logger.debug("responseSign => " + responseSign);
                        logger.debug("sign => " + sign);


                        String tradeStatus = response1.getRrade().getTrade_status();
                        // 如果交易状态为
                        if("TRADE_SUCCESS".equals(tradeStatus)){
                            // “1” 表示支付宝
                            changeUserGold("1",userOrderObj.getOrderId(),tradeStatus);
                        }
                    }

                }else if("2".equals(userOrderObj.getOrderType())){//微信支付
                    // 2（2） 在从这些订单中查找出是微信的订单，做相应的逻辑
                        weiXinOrderQueryData=new WeiXinOrderQueryData(userOrderObj.getTradeNo());
                        request=new HttpsRequest();
                        xml=request.sendXmlPost(Configure.weiXinOrderQuery,weiXinOrderQueryData);
                        if(xml!=null){
                            xStream = new XStream(new DomDriver());
                            xStream.registerConverter(new PojoMapConverter());
                            //map= (Map<String,Object>)xStream.fromXML(xml);
                            //System.out.println(">>>>"+xml);
                            map= (Map<String,Object>)xStream.fromXML(xml.replaceAll("<xml>","<map>").replaceAll("</xml>","</map>"));
                            if("SUCCESS".equals(map.get("return_code").toString())&&"SUCCESS".equals(map.get("result_code").toString())){
                                changeUserGold("2",userOrderObj.getOrderId(),map.get("trade_state").toString());
                            }
                        }
                }else{

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if(map)
        return map;
    }
    @Override
    public boolean changeUserGold(String orderType,Integer orderId,String status) {
        boolean re=false;
        boolean tradeStatus=false;
        Map<String, Object> param =null;
        try {
            if ("2".equals(orderType)) {//微信
                if("SUCCESS".equals(status)) {
                    tradeStatus=true;
                }
            }else if("1".equals(orderType)){//支付宝
                if("TRADE_FINISHED".equals(status) || "TRADE_SUCCESS".equals(status)) {
                    tradeStatus=true;
                }
            }else{

            }
            if(tradeStatus){//交易成功后
                TUserOrder userOrderObj = userOrderService.findObjByKey(orderId);
                userOrderObj.setStatus("4");
                userOrderService.modifyBasic(userOrderObj);
                param = new HashMap<String, Object>();
                param.put("userId",userOrderObj.getUserId());

                RUserGold userGoldObj =goldService.selectByParams(param);
                RUserGoldDetail userGoldDetailObj=new RUserGoldDetail();
                userGoldDetailObj.setUserId(userOrderObj.getUserId());
                userGoldDetailObj.setBasicGold(userGoldObj.getGoldNum());
                userGoldDetailObj.setGoldChange(userOrderObj.getGoldNum());
                userGoldDetailObj.setDetailType("1");//充值
                userGoldDetailObj.setGoldOrderId(userOrderObj.getOrderId());
                userGoldDetailObj.setIsActive("1");
                userGoldDetailObj.setCreateTime(new Date());
                goldDetailService.addBasic(userGoldDetailObj);

                userGoldObj.setGoldNum(userGoldObj.getGoldNum()+userOrderObj.getGoldNum());
                goldService.modifyBasic(userGoldObj);
            }
            re=true;
        } catch (Exception e) {
            re=false;
            e.printStackTrace();
        }finally{

        }
        return re;
    }


    /**
     * 参考：JavaBean对象与Map对象互相转化 - - ITeye技术网站  http://fs20041242.iteye.com/blog/814449
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    private static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }


    /**
     * 支付宝下单
     *
     * 该方法主要完成了两件事情
     * 1、在 langying 数据库中生成支付宝订单数据
     * 2、向支付宝网关发起支付请求，返回 url 链接，返回给前端，由前端请求 url
     *
     * @param orderNo 订单编号
     * @param id
     * @param ip
     * @return
     * @throws ApiException
     */
    @CachePut(cacheNames ="payCode",key="'payCode'+#orderNo")
    @Override
    public Map orderOfAlipay(String orderNo, Integer id, String ip) throws ApiException {
        logger.info("产生支付宝支付的订单数据");
        UUser user = UserDetailHelper.getUserDetail();
        Map map = null;

        GlodRmb glodRmb = GlodRmb.getRmbgoldMap().get(id);
        logger.debug("glodRmb id => " + glodRmb.getId());
        logger.debug("（重要信息）glodRmb 人民币 => " + glodRmb.getRmb());
        logger.debug("glodRmb tGoldAmount => " + glodRmb.getGoldAmount());
        if(glodRmb==null){
            // 传入金额不正确,请重新选择
            throw new ApiException("9018");
        }

        String subject = "有氧英语充值" + glodRmb.getGoldAmount()+"金币";
        try {
            TUserOrder userOrder=new TUserOrder();
            userOrder.setCreateTime(new Date());
            userOrder.setUserId(user.getUserId());
            // 0 表示 交易创建，等待卖家付款，
            userOrder.setStatus("0");
            // 下单金额，以元为单位
            userOrder.setTotalFee(glodRmb.getRmb()*1f);
            // 1：支付宝，2：微信
            userOrder.setOrderType("1");
            userOrder.setGoldNum(glodRmb.getGoldAmount());
            userOrder.setTradeNo(orderNo);
            userOrder.setSubject(subject);
            userOrderService.addBasic(userOrder);
            /**
             * 向支付宝网关发起支付请求
             */
            map=payService.unifiedOrderOfAlipay(orderNo,subject,glodRmb.getRmb()+"");
        } catch (Exception e) {
            // todo
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public <K> K addBasic(TUserOrder obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(TUserOrder obj) throws Exception {

    }

    @Override
    public void delBasic(TUserOrder obj) throws Exception {

    }
}
