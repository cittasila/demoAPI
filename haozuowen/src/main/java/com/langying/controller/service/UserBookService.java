package com.langying.controller.service;

import com.langying.common.contant.CommonConstant;
import com.langying.controller.mapper.RUserBookMapper;
import com.langying.exception.ApiException;
import com.langying.models.RUserBook;
import com.langying.models.RUserGold;
import com.langying.models.RUserGoldDetail;
import com.langying.models.TArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yucy on 2016/3/16.
 */
@Service
public class UserBookService extends BaseService<RUserBook> implements IUserBookService {
    @Autowired
    RUserBookMapper userBookMapper;

    @Autowired
    IArticleInfoService articleInfoService;

    @Autowired
    private IGoldService goldService;

    @Autowired
    private IGoldDetailService goldDetailService;

    @Autowired
    private Environment env;
    @Autowired
    void setrUserBookMapper(RUserBookMapper readingLogMapper) {
        this.userBookMapper = userBookMapper;
        super.baseMapper=userBookMapper;
    }

    @Override
    public Integer addBasic(RUserBook obj) throws Exception {
        userBookMapper.insertSelective(obj);
        return obj.getUserBookId();
    }

    @Override
    public void modifyBasic(RUserBook obj) throws Exception {
        userBookMapper.updateByPrimaryKey(obj);
    }

    @Override
    public void delBasic(RUserBook obj) throws Exception {

    }

    /***
     * 购买书籍
     * @param bookId
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Integer purchase(String bookId, Integer userId) throws Exception {
        TArticleInfo articleInfo = (TArticleInfo) articleInfoService.findObjByKey(bookId);
        if (articleInfo == null) {
            throw new ApiException("2001");
        }
        Integer bookgoldcoin = env.getProperty("goldcoin.bookgoldcoin", Integer.class);
        RUserGold userGold = goldService.selectByParams(CommonConstant.getResMap("userId", userId));
        // 用户购书之前的金币数量
        Integer userGoldNumBefore = userGold.getGoldNum();

        if (userGold==null||userGold.getGoldNum()==null||userGold.getGoldNum() < bookgoldcoin)
            throw new ApiException("9015");
        Map<String,Object> param=new HashMap<>();
        param.put("isActive","1");
        param.put("bookId",bookId);
        param.put("userId",userId);
        List<RUserBook> userBookList=this.findListByParams(param,null);
        if(userBookList!=null&&userBookList.size()!=0){
            throw new ApiException("9016");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);
        RUserBook userBook=new RUserBook();
        userBook.setUserId(userId);
        userBook.setBookId(bookId);
        userBook.setCreateTime(time);
        userBook.setUpdateTime(date);
        userBook.setReadStatus("2");
        userBook.setIsActive("1");
        userBook.setPrice(bookgoldcoin);

        this.addBasic(userBook);

        userGold.setGoldNum(userGold.getGoldNum()-bookgoldcoin);
        userGold.setUpdateTime(new Date());
        goldService.modifyBasic(userGold);

        // 记录金币明细表（购买书本）
        RUserGoldDetail rUserGoldDetailAdd = new RUserGoldDetail();
        rUserGoldDetailAdd.setUserId(userId);
        rUserGoldDetailAdd.setIsActive("1");
        rUserGoldDetailAdd.setGoldChange(bookgoldcoin);
        rUserGoldDetailAdd.setDetailType(CommonConstant.UserGoldDetailType.USERBYBOOK.getValue());
        rUserGoldDetailAdd.setBookId(bookId);
        rUserGoldDetailAdd.setBasicGold(userGoldNumBefore);
        goldDetailService.addUserGoldDetail(rUserGoldDetailAdd);

       return userGold.getGoldNum();
    }
}
