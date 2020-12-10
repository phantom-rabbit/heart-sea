package com.kw.modules.app.service.impl;

import com.kw.common.enums.LogType;
import com.kw.common.exception.RRException;
import com.kw.modules.app.dao.LevelShareDao;
import com.kw.modules.app.entity.LevelShareDetailEntity;
import com.kw.modules.app.entity.LevelShareEntity;
import com.kw.modules.app.service.AccountLogService;
import com.kw.modules.app.service.AccountService;
import com.kw.modules.app.service.LevelShareDetailService;
import com.kw.modules.app.service.LevelShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import org.springframework.transaction.annotation.Transactional;


@Service("levelShareService")
public class LevelShareServiceImpl extends ServiceImpl<LevelShareDao, LevelShareEntity> implements LevelShareService {

    @Autowired
    LevelShareDetailService levelShareDetailService;


    @Autowired
    AccountService accountService;

    @Autowired
    AccountLogService accountLogService;
    @Autowired
    LevelShareDao levelShareDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<LevelShareEntity>().getPage(params), params));
    }
    @Override
    @Transactional
    public void gather() {
        List<LevelShareDetailEntity> levelShareDetailEntitys = levelShareDetailService.gatherList();
        for(LevelShareDetailEntity levelShareDetailEntity :levelShareDetailEntitys) {
            LevelShareEntity levelShareEntity = new LevelShareEntity();
            levelShareEntity.setUserId(levelShareDetailEntity.getUserId());
            levelShareEntity.setCreateDate(new Date());
            levelShareEntity.setShareSdMoney(levelShareDetailEntity.getShareSdMoney());
            levelShareEntity.setShareMoney(levelShareDetailEntity.getShareMoney());
            levelShareEntity.setShareStatus(0);
            levelShareDetailService.updateByStatusAndUserId(0,levelShareEntity.getUserId());
            save(levelShareEntity);
            //	levelShareDetailService.update(new QueryWrapper<LevelShareDetailEntity>().eq("user_id",levelShareDetailEntity.getUserId()).eq(l))
        }
    }

    @Override
    @Transactional
    public void share(Long[] ids) {



        List<LevelShareEntity> levelShareEntityList = (List<LevelShareEntity>) listByIds(Arrays.asList(ids));


        for(LevelShareEntity levelShareEntity:levelShareEntityList){


            if(levelShareEntity.getShareStatus()==1){
                continue;
            }

            if(levelShareEntity.getShareMoney().compareTo(BigDecimal.ZERO)==1) {
                if(accountService.share(levelShareEntity.getUserId(), levelShareEntity.getShareMoney())<0){
                        throw new RRException("系统出错");
                }
                accountLogService.logAccount(levelShareEntity.getUserId(),levelShareEntity.getShareMoney(),"级别分红",LogType.SHARE.getValue(),1);
            }
            if(levelShareEntity.getShareSdMoney().compareTo(BigDecimal.ZERO)==1) {
                if(accountService.share(levelShareEntity.getUserId(), levelShareEntity.getShareSdMoney())<0){
                    throw new RRException("系统出错");
                }
                accountLogService.logAccount(levelShareEntity.getUserId(),levelShareEntity.getShareSdMoney(),"市代分红",LogType.SHARE_SD.getValue(),1);
            }
            if(levelShareEntity.getShareSonMoney().compareTo(BigDecimal.ZERO)==1) {
                if(accountService.shareSon(levelShareEntity.getUserId(), levelShareEntity.getShareSonMoney())<0){
                    throw new RRException("系统出错");
                }
                accountLogService.logAccount(levelShareEntity.getUserId(),levelShareEntity.getShareSonMoney(),"市代MAC分红",LogType.SHARE_SD_SON.getValue(),2);
            }
            levelShareEntity.setShareStatus(1);
           updateById(levelShareEntity);
        }


    }


}