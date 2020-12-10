/*
状态管理
 */
export default {
  /**
   * 支付方式
   * @param val
   * @returns {*}
   */
  zhifuStatus (val) {
    return {
      1: '支付宝',
      2: 'usdt',
      3: '银行卡'
    }[val]
  },
  /**
   * 普通状态
   * @param val
   * @returns {*}
   */
  generalStatus (val) {
    return {
      0: '进行中',
      1: '成功'
    }[val]
  },
  ltcStatus (val) {
    return {
      0: '待启动',
      1: '启动中'
    }[val]
  },
  /**
   * 采集状态
   * @param val
   * @returns {*}
   */
  settleStatus (val) {
    return {
      0: '未采集',
      1: '已采集'
    }[val]
  },
  // 0 未提交 1 提交待审核。2审核通过。-1 审核失败
  /**
   * 实名认证状态
   * @param val
   * @returns {*}
   */
  realStatus (val) {
    return {
      '0': '未提交',
      '1': '提交待审核',
      '2': '审核通过',
      '-1': '审核失败'
    }[val]
  },
}
