package com.langying.controller.service;

import com.langying.common.models.Order;
import com.langying.controller.mapper.RBookLexileMapper;
import com.langying.controller.mapper.RUserBookMapper;
import com.langying.controller.resourcemapper.TArticleInfoMapper;
import com.langying.controller.resourcemapper.TArticletopicRelationMapper;
import com.langying.models.RBookLexile;
import com.langying.models.RUserBook;
import com.langying.models.TArticleInfo;
import com.langying.models.VArticleInfo;
import com.langying.toolbox.utils.ClearNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thtanghao on 2016/3/16.
 */
@Service
public class ResourceService extends  BaseService<RUserBook> implements IResourceService{

    private RUserBookMapper userBookMapper;
    @Autowired
    void setMapper(RUserBookMapper mapper) {
        this.userBookMapper = mapper;
        super.baseMapper=userBookMapper;
    }

    @Autowired
    private RBookLexileMapper bookLexileMapper;
    @Autowired
    private Environment env;
    @Override
    public <K> K addBasic(RUserBook obj) throws Exception {
        return null;
    }

    @Override
    public void modifyBasic(RUserBook obj) throws Exception {

    }

    @Override
    public void delBasic(RUserBook obj) throws Exception {

    }

    @Override
    public List<VArticleInfo> setUserBookSign(Map<String, Object> params,List<TArticleInfo> articleInfoL) throws Exception {
        List<VArticleInfo> varticleInfoL=new ArrayList<VArticleInfo>();
        VArticleInfo varticleInfo=null;
        List<RUserBook> userBookL=userBookMapper.selectListByParams(params,null,null,"update_time desc");
        Map<String, Object> param = new HashMap<String, Object>();
        List<RBookLexile> rBookLexileL=bookLexileMapper.selectListByParams(param,null,null,null);
        Integer bookgoldcoin=env.getProperty("goldcoin.bookgoldcoin",Integer.class);
        for(TArticleInfo articleInfoObj:articleInfoL){
            varticleInfo=new VArticleInfo();
            varticleInfo.setAuthor(articleInfoObj.getAuthor());
            varticleInfo.setBooktitle(articleInfoObj.getBooktitle());
            varticleInfo.setId(articleInfoObj.getId());
            varticleInfo.setImgurl(articleInfoObj.getImgurl());
            varticleInfo.setNumber(articleInfoObj.getNumber());
            varticleInfo.setShowlevel(articleInfoObj.getShowlevel());
            varticleInfo.setGoldcoin(bookgoldcoin);
            varticleInfo.setPurchaseStatus(0);
            varticleInfo.setLexile(articleInfoObj.getLexile());
            if(rBookLexileL.size()>0) {
                for (RBookLexile bookLexileObj : rBookLexileL) {//设置tip
                    if (bookLexileObj.getMinlexile() <= articleInfoObj.getLexile() &&
                            articleInfoObj.getLexile() <= bookLexileObj.getMaxlexile()) {
                        varticleInfo.setTip(bookLexileObj.getLexilelevel());
                    }
                }
            }
            for(RUserBook userBookObj:userBookL){//设置我的读物状态标签
                if(userBookObj.getBookId().equals(articleInfoObj.getId())){
                    varticleInfo.setReadStatus(userBookObj.getReadStatus());
                    varticleInfo.setPurchaseStatus(1);
                    varticleInfo.setUpdateTime(userBookObj.getUpdateTime());
                }
            }
            varticleInfoL.add(varticleInfo);
        }
        return varticleInfoL;
    }

    @Override
    public List<RUserBook> findUserBookList(Map<String, Object> params) throws Exception {
        List<RUserBook> userBookL=userBookMapper.selectListByParams(params,null,null,null);
        return userBookL;
    }

}
