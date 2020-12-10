import Vue from 'vue'
import Vuex from 'vuex'


import user from './modules/user'
import common from './modules/common'
import transfer from './modules/transfer'

Vue.use(Vuex)



export default new Vuex.Store({
  mutations: {},
  actions: {},
  modules: {
    user,
    common,
    transfer
  }
})
