package com.langying.controller.service;


import com.langying.models.UUser;

import java.util.Map;

/**
 * Created by chenxu on 2016/1/12.
 */
public interface ISendMsgService {
   public Map sendMsg(String token,String mobile, String type);
   Map getMsg(String token,String mobile, String type);
   void clearMsg(String token,String mobile, String type);

    /**
     * 在redis中设置用户的uuid（手机验证修改密码）
     * @param uuid
     * @param mobile
     * @return
     * @throws Exception
     */
   public UUser userInfoUuidByMobile(String uuid, String mobile) throws Exception;

    /**
     * 清除redis中设置用户的uuid
     * @param uuid
     */
   public void clearUserInfoUuid(String uuid);

    public Map putCodeBySecurityCode(String code,Map data);
    public Map securityCodeMap(String code);
    public void clearSecurityCode(String code);
}
