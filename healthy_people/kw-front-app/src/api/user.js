import request from "@/utils/httpRequest";
export default {
  getUserInfo (data) {
    return request({
      url: '/api/userInfo',
      method: 'get',
      params: request.adornParams(data)
    })
  },
    getDirects (data) {
        return request({
            url: '/api/directs',
            method: 'get',
            params: request.adornParams(data)
        })
    },

  /**
   * 领取
   */
  income (data) {
    return request({
      url: '/api/user/income',
      method: 'post',
      data: request.adornData(data)
    })
  },
  /**
   * 绑定谷歌验证码
   * @param data
   */
  bindAuthCode (data) {
    return request({
      url: '/api/user/bindAuthCode',
      method: 'post',
      data: request.adornData(data)
    })
  },
  /**
   * 绑定谷歌验证码短信吗
   * @param data
   */
  bindAuthCodeSms (data) {
    return request({
      url: '/api/user/authCode/sms',
      method: 'post',
      data: request.adornData(data)
    })
  },

  /**
   * 实名认证
   * @param data
   */
  userAuth (data) {
    return request({
      url: '/api/user/auth',
      method: 'post',
      data: request.adornData(data)
    })
  },
  /**
   * 忘记密码发送短信验证码
   * @param data
   */
  sendForgetSms (data) {
    return request({
      url: '/api/user/forget/sms',
      method: 'post',
      data: request.adornData(data)
    })
  },
  /**
   * 第一步 忘记密码验证短信验证码
   * @param data
   */
  forgetS1 (data) {
    return request({
      url: '/api/user/forget/s1',
      method: 'post',
      data: request.adornData(data)
    })
  },
  /**
   * 第二步 忘记密码重置密码
   * @param data
   */
  forgetS2 (data) {
    return request({
      url: '/api/user/forget/s2',
      method: 'post',
      data: request.adornData(data)
    })
  },
    /**
     * 支付方式设定
     * @param data
     */
    paymentSave (data) {
        return request({
            url: '/api/user/payment/save',
            method: 'post',
            data: request.adornData(data)
        })
    },
    banks (data) {
        return request({
            url: '/api/banks',
            method: 'post',
            data: request.adornData(data)
        })
    },
    /**
     * 修改支付密码
     * @param data
     */
    alertPayPassword (data) {
      return request({
        url: '/api/user/pay/password',
        method: 'post',
        data: request.adornData(data)
      })
    },
   /**
     * 修改登录付密码
     * @param data
     */
   alterPassword (data) {
      return request({
        url: '/api/user/password',
        method: 'post',
        data: request.adornData(data)
      })
    },
  getPowerList (data) {
    return request({
      url: '/api/user/getPowerList',
      method: 'get',
      params: request.adornParams(data)
    })
  },
  getAccountLogList (data) {
    return request({
      url: '/api/user/getAccountLogList',
      method: 'get',
      params: request.adornParams(data)
    })
  },
    downUserDaytrans (data) {
        return request({
            url: '/api/user/downUserDaytrans',
            method: 'get',
            params: request.adornParams(data)
        })
    },
    downUserDaybills (data) {
        return request({
            url: '/api/user/downUserDaybills',
            method: 'get',
            params: request.adornParams(data)
        })
    },
    downUserList (data) {
        return request({
            url: '/api/user/downUserList',
            method: 'get',
            params: request.adornParams(data)
        })
    },

    sdpublish (data) {
        return request({
            url: '/api/user/sd/publish',
            method: 'post',
            data: request.adornData(data)
        })
    },
    sdphoto (data) {
        return request({
            url: '/api/user/sd/photo',
            method: 'post',
            data: request.adornData(data)
        })
    },
    /**
     * 获取待领取邀请码
     * @param data
     */
    getActiveCodeList () {
        return request({
            url: '/api/user/activeCode/list',
            method: 'get'
        })
    },
    /**
     * 领取邀请码
     * @param data
     */
    getActiveCode () {
        return request({
            url: '/api/user/activeCode/get',
            method: 'get'
        })
    },
    /**
     *我的战队信息
     */
    getTeamInfo(){
        // TODO 后端接口实现
        return request({
            url: '/api/user/teamList',
            method: 'get'
        })
    },
    /**
     *我的战队信息
     */
    getTeamApplyInfo(){
        return request({
            url: '/api/user/my/team/captain',
            method: 'get'
        })
    },
    /**
     * 申请战队
     */
    applyTeamCaptain(data){
        return request({
            url: '/api/user/apply/team/captain',
            method: 'post',
            data: request.adornData(data)
        })
    },
    /**
     * 发放
     */
    awardInvitecode(data){
        return request({
            url: '/api/user/invitecode/award',
            method: 'post',
            data: request.adornData(data)
        })
    },
    /**
     * 忘记交易密码短信
     * @param data
     */
    sendTradeSms(data){
        return request({
            url: '/api/user/trade/sms',
            method: 'post',
            data: request.adornData(data)
        })
    }
}
