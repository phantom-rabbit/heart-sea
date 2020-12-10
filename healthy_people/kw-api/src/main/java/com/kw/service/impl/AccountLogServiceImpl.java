package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.exception.RRException;
import com.kw.common.utils.PageUtils;
import com.kw.common.utils.Query;
import com.kw.dao.AccountLogDao;
import com.kw.entity.AccountEntity;
import com.kw.entity.AccountLogEntity;
import com.kw.entity.AccountMoneyEntity;
import com.kw.service.AccountLogService;
import com.kw.service.AccountMoneyService;
import com.kw.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("accountLogService")
public class AccountLogServiceImpl extends ServiceImpl<AccountLogDao, AccountLogEntity> implements AccountLogService {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountLogDao accountLogDao;
    @Autowired
    AccountMoneyService accountMoneyService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccountLogEntity> page = this.page(
                new Query<AccountLogEntity>().getPage(params),
                new QueryWrapper<AccountLogEntity>().eq("user_id", params.get("userId")).orderByDesc("id")
        );
        return new PageUtils(page);
    }

    @Override
    public void logAccount( Long userId, BigDecimal parentPrice, String mark,String type,Integer kind) {
        AccountMoneyEntity accountMoneyEntity = accountMoneyService.getOne(new QueryWrapper<AccountMoneyEntity>().eq("user_id",userId));

       // AccountEntity accountEntity = accountService.getOne(new QueryWrapper<AccountEntity>().eq("user_id",userId));
        AccountLogEntity accountLogEntity = null;
        if(kind==1){
             accountLogEntity  = new AccountLogEntity(userId,accountMoneyEntity.getParentMoney(),parentPrice,mark,type,kind);
        }
        else if(kind==2){
             accountLogEntity  = new AccountLogEntity(userId,accountMoneyEntity.getSonMoney(),parentPrice,mark,type,kind);
        }
        else if(kind==3){
             accountLogEntity  = new AccountLogEntity(userId,accountMoneyEntity.getPower(),parentPrice,mark,type,kind);
        }
        else if(kind==4){
            accountLogEntity  = new AccountLogEntity(userId,accountMoneyEntity.getPowerSource(),parentPrice,mark,type,kind);
        }

        else{
            throw new RRException("资金类型错误");
        }
        accountLogEntity.setCreateTime(new Date());
        save(accountLogEntity);
    }

    @Override
    public List<AccountLogEntity> queryListByUserId(Long userId) {
        return accountLogDao.queryListByUserId(userId);
    }

    @Override
    public List<AccountLogEntity> queryListByPage(Map<String, Object> params) {
        return accountLogDao.queryListByPage(params);
    }
}