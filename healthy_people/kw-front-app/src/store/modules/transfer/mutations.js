import * as types from './mutation-types'


export const mutations = {
  [types.INIT_LTC_LIST] (state, o) {
    if (o.list instanceof Array) {
      state.ltcList = o.list
    }
  },
  [types.INIT_RESET_LTC_LOAD] (state) {
    state.ltcList = []
  },
}
