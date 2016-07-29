package com.langying.controller.mapper;

import com.langying.common.models.LangYingSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Liwei on 2016/4/20.
 */
public interface RTradeMapper extends LangYingSource {


    Integer findGoldRecordByParamsCount(@Param(value = "params") Map<String, Object> params);

    List<Map<String,Object>> findGoldRecordByParams(@Param(value = "params") Map<String, Object> params,
                                                    @Param(value = "pageOffset") Integer pageOffset,
                                                    @Param(value = "pageSize") Integer pageSize);


    Integer findRechargeRecordByParamsCount(@Param(value = "params") Map<String, Object> params);
    List<Map<String,Object>> findRechargeRecordByParams(@Param(value = "params") Map<String, Object> params,
                                                        @Param(value = "pageOffset") Integer pageOffset,
                                                        @Param(value = "pageSize") Integer pageSize);

}
