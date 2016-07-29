package com.langying.controller.service;

import com.langying.controller.mapper.RReadingLogMapper;
import com.langying.models.RReadingLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yucy on 2016/3/16.
 */
@Service
public class ReadingLogService extends BaseService<RReadingLog> implements IReadingLogService {
    @Autowired
    RReadingLogMapper readingLogMapper;

    @Autowired
    void setrReadingLogMapper(RReadingLogMapper readingLogMapper) {
        this.readingLogMapper = readingLogMapper;
        super.baseMapper=readingLogMapper;
    }

    @Override
    public Integer addBasic(RReadingLog obj) throws Exception {
        readingLogMapper.insertSelective(obj);
        return obj.getReadingLogId();
    }

    @Override
    public void modifyBasic(RReadingLog obj) throws Exception {

    }

    @Override
    public void delBasic(RReadingLog obj) throws Exception {

    }
}
