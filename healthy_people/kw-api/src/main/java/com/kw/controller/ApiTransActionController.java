package com.kw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kw.common.annotation.Login;
import com.kw.common.annotation.LoginUser;
import com.kw.common.constant.CacheConstants;
import com.kw.common.enums.BuyTransStatus;
import com.kw.common.exception.RRException;
import com.kw.common.oss.cloud.OSSFactory;
import com.kw.common.oss.entity.SysOssEntity;
import com.kw.common.oss.service.SysOssService;
import com.kw.common.sms.config.SmsConstant;
import com.kw.common.sms.config.SmsResult;
import com.kw.common.utils.*;
import com.kw.common.validator.ValidatorUtils;
import com.kw.entity.AccountEntity;
import com.kw.entity.BuyTransEntity;
import com.kw.entity.PurposeBuyEntity;
import com.kw.entity.UserEntity;
import com.kw.form.*;
import com.kw.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiTransActionController {

    @Autowired
    PurposeBuyService purposeBuyService;


    @Autowired
    BuyTransService buyTransService;


    @Autowired
    AccountService accountService;

    @Autowired
    SysOssService sysOssService;


    @Autowired
    UserService userService;

    @Autowired
    private SmsService smsService;
    @Autowired
    RedisUtils  redisUtils;
    @RequestMapping("/transaction/index")
    public R index(){
        HashMap map = new HashMap();
        BigDecimal sumPrebuy = purposeBuyService.sumPrebuy();
        Date now =DateUtils.addDateDays(new Date(),1);
        Date yesterday = DateUtils.addDateDays(now,-1);
        HashMap minMaxMap = purposeBuyService.selectMinMax();
        if(minMaxMap==null){
            minMaxMap = new HashMap();
            minMaxMap.put("maxPrice",0);
            minMaxMap.put("minPrice",0);
        }
        if(null!=minMaxMap.get("maxPrice")&&null!=minMaxMap.get("minPrice")){
            Double gain = (Double.valueOf(minMaxMap.get("maxPrice").toString())- Double.valueOf(minMaxMap.get("minPrice").toString()))/Double.valueOf(minMaxMap.get("minPrice").toString());
            minMaxMap.put("gain",gain*100);

        }else{
            minMaxMap.put("gain",0);
        }

        BigDecimal transMoney = buyTransService.selectTransMoney(DateUtils.format(yesterday,DateUtils.DATE_PATTERN),DateUtils.format(now,DateUtils.DATE_PATTERN));
        minMaxMap.put("sumPrebuy",sumPrebuy);
        minMaxMap.put("transMoney",transMoney==null?0:transMoney);
        minMaxMap.put("proposedPrice",redisUtils.get((Constant.PLAT_AVG_PRICE)));
        minMaxMap.put("minPrice",redisUtils.get((Constant.PLAT_AVG_PRICE)));
        minMaxMap.put("googleAuth",redisUtils.get(Constant.GOOGLE_AUTH,Integer.class));
     //   minMaxMap.put("googleAuth",redisUtils.get(Constant.BUSINESS_OPEN,Integer.class));

        return R.ok(minMaxMap);
    }

    @Login
    @RequestMapping("/transaction/bublish")
    @ApiOperation(value="发布交易")
    public R bublish(@ApiIgnore @LoginUser UserEntity user,@RequestBody PurposeForm purposeForm){



        ValidatorUtils.validateEntity(purposeForm);






        if( purposeForm.getTotal().contains(".")) {
            if(Integer.parseInt(purposeForm.getTotal().split("\\.")[1])!=0){
                return R.error("必须为整数");
            }
            int indexOf =  purposeForm.getTotal().indexOf(".");
            purposeForm.setTotal(purposeForm.getTotal().substring(0, indexOf));
        }


        if(purposeForm.getPayType().equals("2")){
            if (new BigDecimal(redisUtils.get((Constant.PLAT_AVG_PRICE))).compareTo(new BigDecimal(purposeForm.getTotal()).multiply(new BigDecimal(7))) == 1) {
                return R.error("必须大于平台参考价");
            }
        }else {

            if (new BigDecimal(redisUtils.get((Constant.PLAT_AVG_PRICE))).compareTo(new BigDecimal(purposeForm.getTotal())) == 1) {
                return R.error("必须大于平台参考价");
            }

        }


        if(new BigDecimal(purposeForm.getTotal()).compareTo(BigDecimal.ONE)==-1){
            return R.error("收购金额不能小于1");
        }

        if(!user.getPayPassword().equals(DigestUtils.sha256Hex(purposeForm.getPaypassword()))){
            return R.error("支付密码错误");
        }


        if(redisUtils.get(Constant.BUSINESS_OPEN,Integer.class)==0){
            return R.error("已休市,无法交易");
        }

        AccountEntity account = accountService.queryByUserId(user.getId());
        if(account.getRealStatus()!=2){
            throw new RRException("请先进行实名认证");
        }
        if(purposeForm.getPayType().equals("1")){
            if(account.getAlipayStatus()==0){
                throw new RRException("请先进行支付宝设置");
            }
        }
       else  if(purposeForm.getPayType().equals("2")){
            if(account.getUsdtStatus()==0){
                throw new RRException("请先进行USDT设置");
            }
        }
        else if(purposeForm.getPayType().equals("3")){
            if(account.getBankStatus()==0){
                throw new RRException("请先进行银行卡设置");
            }
        }else{
            throw new RRException("请选择支付方式");
        }



        return purposeBuyService.publish(user.getId(),purposeForm);

    }

    @Login
    @RequestMapping("/purpose/cancle")
    @ApiOperation(value="取消求购")
    @CacheEvict(value = CacheConstants.SUMPREBUY,allEntries = true)
    public R canclepurpose(@ApiIgnore @LoginUser UserEntity user,@RequestBody  CancleForm cancleForm){

      PurposeBuyEntity purposeBuyEntity = purposeBuyService.getById(cancleForm.getPurposeId());

      if(purposeBuyEntity.getStatus()==1||purposeBuyEntity.getStatus()==-1){
          return R.error("本次求购已经是最终状态");
      }
      if(!purposeBuyEntity.getBuyerId().equals(user.getId())){
          return R.error("必须本人完成求购");
      }

      List<BuyTransEntity> list =   buyTransService.list(new QueryWrapper<BuyTransEntity>().eq("purpose_id",cancleForm.getPurposeId()));

      for(BuyTransEntity buyTransEntity:list){
          if(buyTransEntity.getStatus()==0||buyTransEntity.getStatus()==1){
              return R.error("有交易正在进行中");
          }
      }

      purposeBuyEntity.setStatus(1);
      purposeBuyService.cancle(purposeBuyEntity);
      return  R.ok("完成成功");

    }

    @Login
    @RequestMapping("/purpose/detail")
    @ApiOperation(value="欲买入详情")
    public R detail(Long id){
       return  R.ok().put("item",purposeBuyService.getById(id));
    }

    @Login
    @RequestMapping("/transaction/detail")
    @ApiOperation(value="交易详情")
    public R transactiondetail(Long id){


        BuyTransEntity buyTransEntity = buyTransService.getById(id);
        UserEntity buyer = userService.getById(buyTransEntity.getBuyerId());
        UserEntity seller = userService.getById(buyTransEntity.getSellerId());

        buyTransEntity.setBuyerPhone( buyer.getMobile());
        buyTransEntity.setSellerPhone(seller.getMobile());

        AccountEntity accountSeller =  accountService.queryByUserId(buyTransEntity.getSellerId());


        if(buyTransEntity.getPayType()==1){
            buyTransEntity.setPayNumber(accountSeller.getAlipayNumber());
        }
        if(buyTransEntity.getPayType()==2){
            buyTransEntity.setPayNumber(accountSeller.getUsdtToken());
        }
        if(buyTransEntity.getPayType()==3){
            buyTransEntity.setPayNumber(accountSeller.getBank()+accountSeller.getBranch()+"卡号:"+accountSeller.getBankCardno());
        }



        Long time = 0L;
        if(buyTransEntity.getStatus()==0) {
            time = (DateUtils.addDateHours(buyTransEntity.getCreateTime(), redisUtils.get(Constant.BUSINESS_TIME,Integer.class)).getTime() - System.currentTimeMillis()) / 1000;
        }
        if(buyTransEntity.getStatus()==1){
            time = (DateUtils.addDateHours(buyTransEntity.getBuyerSubmitTime(), redisUtils.get(Constant.BUSINESS_TIME,Integer.class)).getTime() - System.currentTimeMillis()) / 1000;
        }
        buyTransEntity.setLastTime(time);
        return  R.ok().put("item",buyTransEntity);

    }




    @RequestMapping("/transaction/list")
    @ApiOperation(value="交易列表")
    @Login
    public R list(@ApiIgnore @LoginUser UserEntity user,@RequestParam Map<String, Object> params){
        params.put("userId",user.getId());

    //    params.put("buyerId",params.get("buyerId"));
        PageUtils page = purposeBuyService.queryPage(params);
        String start = DateUtils.format(DateUtils.addDateDays(new Date(),-30),DateUtils.DATE_PATTERN);
        String end = DateUtils.format(DateUtils.addDateDays(new Date(),1),DateUtils.DATE_PATTERN);
        for(Object entitys:page.getList()){
            PurposeBuyEntity entity = (PurposeBuyEntity)entitys;
            entity.setCounts(purposeBuyService.countByUserId(entity.getBuyerId()
                    ,start
                    ,end));
        }

        return R.ok().put("page", page);
    }

    @Login
    @RequestMapping("/transaction/sell")
    @ApiOperation(value="售卖人出售")

    public R sell(@ApiIgnore @LoginUser UserEntity user, @RequestBody SellForm sellForm){
        ValidatorUtils.validateEntity(sellForm);



        if(redisUtils.get(Constant.BUSINESS_OPEN,Integer.class)==0){
            return R.error("已休市,无法交易");
        }
        if(!user.getPayPassword().equals(DigestUtils.sha256Hex(sellForm.getPaypassword()))){
            return R.error("支付密码错误");
        }
        Integer auth = redisUtils.get(Constant.GOOGLE_AUTH,Integer.class);
        if(auth==1||user.getBindAuthCode() == 1){
                if(user.getBindAuthCode() != 1){
                    return R.error("为了您的安全，请在(我的->设置)先绑定谷歌验证码");
                }
                if(sellForm.getCode() == null){
                    return R.error("谷验歌证码不能为空");
                }

                if(!GoogleAuthenticatorUtils.verify(user.getSecretKey(), sellForm.getCode())){
                    return R.error("验证码错误");
             }
        }


        //判断purpose最低金额
        PurposeBuyEntity  purposeBuyEntity  = purposeBuyService.getById(sellForm.getPurposeId());

        if(purposeBuyEntity.getBuyerId().equals(user.getId())){
            return  R.error("不能购买你自己的");
        }
        if(purposeBuyEntity.getStatus()==1||purposeBuyEntity.getStatus()==-1){
            return  R.error("交易已完成");
        }



        if( sellForm.getAmount().contains(".")) {
            if(Integer.parseInt(sellForm.getAmount().split("\\.")[1])!=0){
                return R.error("必须为整数");
            }
            int indexOf =  sellForm.getAmount().indexOf(".");
            sellForm.setAmount(sellForm.getAmount().substring(0, indexOf));
        }





        BigDecimal lastMoney = purposeBuyEntity.getTotal().subtract(purposeBuyEntity.getHasBuy());
        //余额比最低投资大
        if(lastMoney.doubleValue()>purposeBuyEntity.getBuyStart().doubleValue()){
            if(new BigDecimal(sellForm.getAmount()).compareTo(purposeBuyEntity.getBuyStart())<0){
                return  R.error("不能小于最低购买数量");
            }

            if(new BigDecimal(sellForm.getAmount()).compareTo(BigDecimal.ONE)==-1){
                return R.error("收购金额不能小于1");
            }
        }else {
            if(new BigDecimal(sellForm.getAmount()).compareTo(purposeBuyEntity.getBuyStart())!=0){
                return  R.error("数量超限或小于小于最低购买数量");
            }
        }



        return buyTransService.sell(user.getId(),purposeBuyEntity,sellForm.getAmount()
                ,DateUtils.format(DateUtils.addDateDays(new Date(),-30),DateUtils.DATE_PATTERN)
                ,DateUtils.format(DateUtils.addDateDays(new Date(),1),DateUtils.DATE_PATTERN)
        );
    }


    @Login
    @RequestMapping("/buytrans/list")
    @ApiOperation(value="交易列表")
    public R buytrans(@ApiIgnore @LoginUser UserEntity user, @RequestParam Map<String, Object> params){
                params.put("userId",user.getId());
        PageUtils page = buyTransService.queryPage(params);
        return R.ok().put("page", page).put("userId",user.getId());



    }

    /**
     * 上传文件
     */
    @PutMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);

        return R.ok().put("url", url);
    }


    @Login
    @RequestMapping("/transaction/photo")
    @ApiOperation(value="购买者保存上传照片")
    public R photo(@ApiIgnore @LoginUser UserEntity user, @RequestBody UploadPhotoForm uploadPhotoForm){

        ValidatorUtils.validateEntity(uploadPhotoForm);

        BuyTransEntity buyTransEntity = buyTransService.getById(uploadPhotoForm.getTransId());
        if(!buyTransEntity.getBuyerId().equals(user.getId())){
            return R.error("不能操作不是你的交易");
        }
        if(!buyTransEntity.getStatus().equals(BuyTransStatus.STATUS0.getValue())){
            return R.error("不能操作状态不是待上传凭证的交易");
        }
        if(StringUtils.isBlank(uploadPhotoForm.getPhoto())){
            return R.error("凭证为空");
        }
        buyTransEntity.setBuyerSubmitTime(new Date());
        buyTransEntity.setPhoto(uploadPhotoForm.getPhoto());
        buyTransEntity.setStatus(BuyTransStatus.STATUS1.getValue());
        buyTransService.updateById(buyTransEntity);
       // log.info("手机号码"+mobile);
        SmsResult result = smsService.sendWithTemplate(userService.getById(buyTransEntity.getSellerId()).getMobile(), SmsConstant.SMS_TRADE_ALI_TEMPLATE_ID, new String[]{"卖出",redisUtils.get(Constant.BUSINESS_TIME,Integer.class)+""});

        return R.ok("保存成功");
    }

    @Login
    @RequestMapping("/transaction/confirm")
    @ApiOperation(value="售卖人确认交易")
    public R confirm(@ApiIgnore @LoginUser UserEntity user,@RequestBody TransConfirmForm transConfirmForm){

        ValidatorUtils.validateEntity(transConfirmForm);
        if(!user.getPayPassword().equals(DigestUtils.sha256Hex(transConfirmForm.getPaypassword()))){
            return R.error("支付密码错误");
        }

        return buyTransService.confirm(transConfirmForm.getTransId(),user.getId());
    }

}
