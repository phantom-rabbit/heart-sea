package com.kw.modules.app.service.impl;

import com.kw.common.enums.SdStatus;
import com.kw.modules.app.entity.AccountEntity;
import com.kw.modules.app.entity.BuyTransEntity;
import com.kw.modules.app.entity.LevelConfiigEntity;
import com.kw.modules.app.entity.LevelShareDetailEntity;
import com.kw.modules.app.service.AccountService;
import com.kw.modules.app.service.BuyTransService;
import com.kw.modules.app.service.LevelConfiigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;

import com.kw.modules.app.dao.LevelShareDetailDao;

import com.kw.modules.app.service.LevelShareDetailService;
import org.springframework.transaction.annotation.Transactional;


@Service("levelShareDetailService")
public class LevelShareDetailServiceImpl extends ServiceImpl<LevelShareDetailDao, LevelShareDetailEntity> implements LevelShareDetailService {
    @Autowired
    BuyTransService buyTransService;

    @Autowired
    LevelConfiigService levelConfiigService;

    @Autowired
    AccountService accountService;
    @Autowired
    LevelShareDetailDao levelShareDetailDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(baseMapper.queryList(new Query<LevelShareDetailEntity>().getPage(params), params));
    }

    @Override
    public List<LevelShareDetailEntity> gatherList() {
        return this.baseMapper.gatherList();
    }

    @Override
    public boolean updateByStatusAndUserId(int status, Long userId) {
        return this.baseMapper.updateByStatusAndUserId(status,userId);
    }

    @Override
    @Transactional
    public void cacue() {
        BigDecimal feeSD = new BigDecimal(0.3);


        List<BuyTransEntity> buyTransList = buyTransService.list(new QueryWrapper<BuyTransEntity>().eq("status",2).eq("settle_status",0));
        List<LevelConfiigEntity> levelConfiigEntitys = levelConfiigService.list();
        List<AccountEntity> accountEntitiesAll = accountService.list(new QueryWrapper<AccountEntity>());
        List<LevelShareDetailEntity> levelShareDetailEntities = new ArrayList<LevelShareDetailEntity>();
        for(BuyTransEntity buyTransEntity:buyTransList){

            for(LevelConfiigEntity levelConfiigEntity:levelConfiigEntitys){


                if(levelConfiigEntity.getTransShare().compareTo(BigDecimal.ZERO)==0){
                    continue;
                }
                BigDecimal fee = (buyTransEntity.getFee().multiply( levelConfiigEntity.getTransShare())).setScale(8,BigDecimal.ROUND_DOWN);



                //如果是健康之星 那么世代参与平分
                if(levelConfiigEntity.getId().equals(2L)){
                    //统计世代人数 + 健康大使人数
                    int count = 0 ;
                    int countsd = 0;
                    List<AccountEntity> accountEntitiesNormal = new ArrayList<AccountEntity>();
                    List<AccountEntity> accountEntitiesSd = new ArrayList<AccountEntity>();
                    for(AccountEntity accountEntity:accountEntitiesAll){
                        if(accountEntity.getLevelId().equals(levelConfiigEntity.getId())) {
                            accountEntitiesNormal.add(accountEntity);
                            count++;
                        }
                        if (accountEntity.getSdStatus() == SdStatus.SUCCESS.getValue()) {
                            countsd++;
                            accountEntitiesSd.add(accountEntity);
                        }
                    }
                    if(count+countsd>0){
                        //这里牵扯到加权平分 但是因为考虑到规则不会变化 暂时这样子
                        //单人费用
                        BigDecimal feePerson = fee.divide(new BigDecimal(count+countsd)).setScale(8,BigDecimal.ROUND_DOWN);
                        //普通健康之星

                      //  BigDecimal feeNormal = levelConfiigEntity.getTransShare().multiply(fee.divide(new BigDecimal(levelConfiigEntity.getTransShare().doubleValue()*count+ feeSD.doubleValue()*countsd)).setScale(8,BigDecimal.ROUND_DOWN));
                        //30/(2)
                        //0.3*(30/(0.3*1+0.3*1))
                      //  BigDecimal feeSd = feeSD.multiply(fee.divide(new BigDecimal(levelConfiigEntity.getTransShare().doubleValue()*count+ feeSD.doubleValue()*countsd)).setScale(8,BigDecimal.ROUND_DOWN));



                        for(AccountEntity accountEntity:accountEntitiesNormal){
                            LevelShareDetailEntity levelShareDetailEntity = new LevelShareDetailEntity();
                            levelShareDetailEntity.setUserId(accountEntity.getUserId());
                            levelShareDetailEntity.setTransId(buyTransEntity.getId());
                            levelShareDetailEntity.setCreateDate(new Date());
                            levelShareDetailEntity.setGatherStatus(0);
                            levelShareDetailEntity.setShareMoney(feePerson);
                            levelShareDetailEntity.setShareSdMoney(new BigDecimal(0));
                            //加入豪华午餐
                            levelShareDetailEntities.add(levelShareDetailEntity);
                        }
                        //世代额外加成
                        for(AccountEntity accountEntity:accountEntitiesSd){
                            LevelShareDetailEntity levelShareDetailEntity = new LevelShareDetailEntity();
                            levelShareDetailEntity.setUserId(accountEntity.getUserId());
                            levelShareDetailEntity.setTransId(buyTransEntity.getId());
                            levelShareDetailEntity.setCreateDate(new Date());
                            levelShareDetailEntity.setGatherStatus(0);
                            levelShareDetailEntity.setShareMoney(new BigDecimal(0));
                            levelShareDetailEntity.setShareSdMoney(feePerson);
                            //加入豪华午餐
                            levelShareDetailEntities.add(levelShareDetailEntity);
                        }
                    }
                }else{
                    int count = 0 ;
                    List<AccountEntity> accountEntitiesNormal = new ArrayList<AccountEntity>();
                    for(AccountEntity accountEntity:accountEntitiesAll){
                        if(accountEntity.getLevelId().equals(levelConfiigEntity.getId())) {
                            accountEntitiesNormal.add(accountEntity);
                            count++;
                        }
                    }
                    if(count>0){
                        BigDecimal feePerson = fee.divide(new BigDecimal(count)).setScale(8,BigDecimal.ROUND_DOWN);
                        for(AccountEntity accountEntity:accountEntitiesNormal){
                            LevelShareDetailEntity levelShareDetailEntity = new LevelShareDetailEntity();
                            levelShareDetailEntity.setUserId(accountEntity.getUserId());
                            levelShareDetailEntity.setTransId(buyTransEntity.getId());
                            levelShareDetailEntity.setCreateDate(new Date());
                            levelShareDetailEntity.setGatherStatus(0);
                            levelShareDetailEntity.setShareMoney(feePerson);
                            levelShareDetailEntity.setShareSdMoney(new BigDecimal(0));
                            //加入豪华午餐
                            levelShareDetailEntities.add(levelShareDetailEntity);

                        }

                    }
                }

            }
            buyTransEntity.setSettleStatus(1);
            buyTransService.updateById(buyTransEntity);

        }
        saveBatch(levelShareDetailEntities);



    }



}