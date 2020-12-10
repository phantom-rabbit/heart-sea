

package com.kw.common.oss.cloud;



import com.kw.common.exception.RRException;
import com.kw.common.sysconfig.ConfigConstant;
import com.kw.common.sysconfig.service.SysConfigService;
import com.kw.common.utils.Constant;
import com.kw.common.utils.SpringContextUtils;


/**
 * 文件上传Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.LOCAL.getValue()){
            return new DiskCloudStorageService(config);
        }
        throw new RRException("系统存储配置错误");
    }

}
