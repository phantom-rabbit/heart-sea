package com.kw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kw.common.constant.CacheConstants;
import com.kw.common.utils.*;
import com.kw.dao.PurposeBuyDao;
import com.kw.entity.BuyTransEntity;
import com.kw.entity.PurposeBuyEntity;
import com.kw.form.PurposeForm;
import com.kw.service.BuyTransService;
import com.kw.service.PurposeBuyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("purposeBuyService")
public class PurposeBuyServiceImpl extends ServiceImpl<PurposeBuyDao, PurposeBuyEntity> implements PurposeBuyService {

    @Autowired
    BuyTransService buyTransService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurposeBuyEntity> queryWrapper  = new QueryWrapper<PurposeBuyEntity>();
        if("1".equals(params.get("self"))){
            queryWrapper.eq("buyer_id",params.get("userId"));
        }else {
            if (params.get("buyerId") != null && StringUtils.isNotBlank(params.get("buyerId").toString())) {
                queryWrapper.eq("buyer_id", params.get("buyerId"));
            }

            queryWrapper.apply("total>has_buy");
        }

        queryWrapper.eq("status", 0);

        IPage<PurposeBuyEntity> page = this.page(
                new Query<PurposeBuyEntity>().getPage(params),
                queryWrapper
        );



        for(PurposeBuyEntity entity:page.getRecords()){
            BigDecimal last = entity.getTotal().subtract(entity.getHasBuy());
            if(last.compareTo(entity.getBuyStart())==-1){
                entity.setNumberSection(entity.getBuyStart()+"-"+last);
            }else{
                entity.setNumberSection(entity.getBuyStart()+"-"+last);
            }
        }

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value=CacheConstants.BUY_TRANS_COUNT_30DAY,key = "#buyerId+'-'+#start+'-'+#end")
    public Integer countByUserId(Long buyerId, String start, String end) {
        return this.baseMapper.countByUserId(buyerId,start,end);
    }

    @Override
    @CacheEvict(value = CacheConstants.SUMPREBUY,allEntries = true)
    public void cancle(PurposeBuyEntity purposeBuyEntity) {
        updateById(purposeBuyEntity);
    }

    @Override
    public int reduceHasBuy(Long purposeId, BigDecimal amount) {
        return  this.baseMapper.reduceHasBuy(purposeId,amount);
    }


    @Override
    public int preSell(Long purposeId, String amount) {
        return baseMapper.preSell(purposeId,amount);
    }

    @Override
    @Cacheable(value=CacheConstants.SUMPREBUY,unless = "#result == null")
    public BigDecimal sumPrebuy()
    {
        BigDecimal sumpre = this.baseMapper.sumPrebuy();
        return sumpre==null?BigDecimal.ZERO:sumpre;
    }
    @Override
    @Cacheable(value=CacheConstants.MINMAX,unless = "#result == null")
    public HashMap selectMinMax() {
        HashMap usdt = this.baseMapper.selectMinMaxUSDT();
        HashMap cny =  baseMapper.selectMinMax();
        HashMap map = new HashMap();
        double minPrice=0;
        double maxPrice=0;
        if(usdt!=null){
            minPrice = Double.valueOf(usdt.get("minPrice").toString())*7;
            maxPrice = Double.valueOf(usdt.get("maxPrice").toString())*7;
        }
        if(cny!=null){
            minPrice = Double.valueOf(cny.get("minPrice").toString())<minPrice?Double.valueOf(cny.get("minPrice").toString()):minPrice;
            maxPrice = Double.valueOf(cny.get("maxPrice").toString())>maxPrice?Double.valueOf(cny.get("maxPrice").toString()):maxPrice;
        }
        map.put("minPrice",minPrice);
        map.put("maxPrice",maxPrice);
        return map;
    }

    @Override
    @Caching(evict={@CacheEvict(value = CacheConstants.MINMAX,allEntries = true),
            @CacheEvict(value = CacheConstants.SUMPREBUY,allEntries = true)})
    public R publish(Long id, PurposeForm purposeForm) {
        List<PurposeBuyEntity> purposeBuyEntityList = this.list(new QueryWrapper<PurposeBuyEntity>().eq("buyer_id",id).eq("status",0));
        if(purposeBuyEntityList!=null&&purposeBuyEntityList.size()>0){
            return  R.error("已有您的正在进行的交易,不能发布交易");
        }
        if(new BigDecimal(purposeForm.getBuyStart()).compareTo(new BigDecimal(purposeForm.getTotal()))==1){
            return  R.error("起量不能大于总量");
        }
        if(new BigDecimal(purposeForm.getTotal()).compareTo(BigDecimal.ZERO)==0){
            return  R.error("总额不能为0");
        }
        if(new BigDecimal(purposeForm.getPrice()).compareTo(BigDecimal.ZERO)==0){
            return  R.error("价格不能为0");
        }

        PurposeBuyEntity purposeBuyEntity = new PurposeBuyEntity();
        purposeBuyEntity.setBuyerId(id);
        purposeBuyEntity.setPrice(new BigDecimal(purposeForm.getPrice()));
        purposeBuyEntity.setPayType(Integer.parseInt(purposeForm.getPayType()));
        purposeBuyEntity.setTotal(new BigDecimal(purposeForm.getTotal()));
        purposeBuyEntity.setStatus(0);
        purposeBuyEntity.setUnitPrice(new BigDecimal(purposeForm.getPrice()).divide(new BigDecimal(purposeForm.getTotal()),2,BigDecimal.ROUND_HALF_UP));
        purposeBuyEntity.setBuyStart(new BigDecimal(purposeForm.getBuyStart()));
        purposeBuyEntity.setCreateTime(new Date());
        if(!save(purposeBuyEntity)){
            return R.error("系统异常");
        }
        return R.ok("发布成功");
    }


}