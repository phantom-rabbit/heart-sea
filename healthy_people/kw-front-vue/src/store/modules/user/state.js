// 放置你要重置的
export const initState = {
  userInfo: {},
  account: {},
  leader: false,
  sk: {},
  aList:{},
  /**
   * 待领取邀请码数量
   */
  activeCodeCt: 0,
  gAuth:0,
  /**
   * 邀请码列表
   */
  activeCodeList: [],
  /**
   * 战队信息
   */
  team:{},
  /**
   * 我的战队信息（申请的）
   */
  myTeam:{},
  /**
   * team 列表
   */
  teamList: [],
  cw:{}

}


export default {
  ...initState
}
