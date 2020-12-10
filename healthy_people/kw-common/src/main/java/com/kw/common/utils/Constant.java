

package com.kw.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";
	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * 服务器本地存储
         */
        LOCAL(4);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


//    /**
//     * 用户注册默认等级KEY   redis
//     */
//    public final static String USER_REGISTER_LEVEL = RedisKeys.getSysConfigKey("USER_REGISTER_LEVEL");
//    /**
//     * 注册默认VIP等级 KEY redis
//     */
//    public final static String USER_REGISTER_VIP =  RedisKeys.getSysConfigKey("USER_REGISTER_VIP");
    /**
     * 市代奖励
     */
    public final static String SD_AWARD_AT_USER_AUTH =  RedisKeys.getSysConfigKey("SD_AWARD_AT_USER_AUTH");
    /***
     * 实名认证聚合APPKEY  redis
     */
    public final static String JUHE_REAL_NAME_KEY = RedisKeys.getSysConfigKey("JUHE_REAL_NAME_KEY");
    /**
     * 关闭网站登录
     */
    public final static String LOGIN_CLOSED = RedisKeys.getSysConfigKey("LOGIN_CLOSED");
    /**
     * 手机号码归属地聚合APPKEY
     */
    public final static String JUHE_MOBILE_INFO = RedisKeys.getSysConfigKey("JUHE_MOBILE_INFO");


    public final static String BUSINESS_OPEN = RedisKeys.getSysConfigKey("BUSINESS_OPEN");


    /***
     * 平台参考价格  redis
     */
    public final static String PLAT_AVG_PRICE = RedisKeys.getSysConfigKey("PLAT_AVG_PRICE");


    /***
     * USDTTOKEN
     */
    public final static String USDT_TOKEN = RedisKeys.getSysConfigKey("SD_USDT_TOKEN");

    public final static String SD_OPEN = RedisKeys.getSysConfigKey("SD_OPEN");
    public final static String BUSINESS_TIME = RedisKeys.getSysConfigKey("BUSINESS_TIME");

    /**
     * 激活码有效天数
     */
    public final static String ACTIVATION_CODE_VALID_TIME = RedisKeys.getSysConfigKey("ACTIVATION_CODE_VALID_TIME");


    public final static String INVITER_KJ_AWARD = RedisKeys.getSysConfigKey("INVITER_KJ_AWARD");

    /**
     * 谷歌验证码开关
     */
    public final static String GOOGLE_AUTH = RedisKeys.getSysConfigKey("GOOGLE_AUTH");

    public final static String BUSINESS_MAC_LIMIT = RedisKeys.getSysConfigKey("BUSINESS_MAC_LIMIT");



    public final static String SD_STEP_3 = RedisKeys.getSysConfigKey("SD_STEP_3");
    public final static String SD_STEP_2 = RedisKeys.getSysConfigKey("SD_STEP_2");

    public final static String SS_LOWER = RedisKeys.getSysConfigKey("SS_LOWER");
    public final static String SS_HIGH = RedisKeys.getSysConfigKey("SS_HIGH");

    public final static String IS_DL = RedisKeys.getSysConfigKey("IS_DL");

    /**
     * 购买矿机可领取激活码的比例（可领取数=设置的百分比*购买矿机总消费）
     */
    public final static String BUY_LTC_GET_ACTIVE_CODE_CT = RedisKeys.getSysConfigKey("BUY_LTC_GET_ACTIVE_CODE_CT");

}
