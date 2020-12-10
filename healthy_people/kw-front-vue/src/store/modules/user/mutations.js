import * as types from './mutation-types'
import {initState} from './state'

export const mutations = {
  [types.INIT_USER_INFO] (state, o) {
    state.gAuth=o.gAuth
    state.userInfo = o.user
    state.account = o.account
    state.sk = o.sk
    state.aList = o.aList
    state.cw = o.cw
    state.leader = o.leader
  },
  [types.INIT_ACTIVE_CODE_LIST] (state, o) {
    state.activeCodeList = o.list
    state.activeCodeCt = o.activeCodeCt
  },
  [types.INIT_TEAM_INFO] (state, o) {
    state.team = o.team
    state.teamList = o.teamUserList
    console.log(state.teamList)
  },
  [types.INIT_MY_TEAM_INFO] (state, o) {
    state.myTeam = o.info
  },
  [types.INIT_RESET] (state) {
    state.userInfo = initState.userInfo
    state.account = initState.account
    state.sk = initState.sk
    state.aList = initState.aList
    state.activeCodeCt = initState.activeCodeCt
    state.team = initState.team
    state.myTeam = initState.myTeam
    state.teamList = initState.teamList
    state.cw = initState.cw
    state.leader = initState.leader
  }
}
