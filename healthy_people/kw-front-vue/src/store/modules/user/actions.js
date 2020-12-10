import * as types from './mutation-types'
import api from '@/api'
import {setToken} from '@/utils'
import {Toast} from 'vant'
import router from '@/router'
// const ls = require('local-storage');

export const actions = {
    async login({dispatch}, o){
        api.login(o).then(res=>{
            if(res.data.code === 0){
                setToken(res.data.token)
                Toast.success(res.data.msg)
                router.push("/")
                dispatch('getUserInfo')
            }else{
                this.$toast.fail(res.data.msg)
            }
        })
    },
    async getUserInfo({commit}) {
        let res = await api.getUserInfo();
        if (res.data.code === 0) {
            // ls.set("userInfo", res.data.info)
            commit(types.INIT_USER_INFO, res.data)
        }
    },
    /**
     * 领取收益
     * @param o
     * @returns {Promise<void>}
     */
    async saveIncome( o) {
        let res = await api.income(o);
        if (res.data.code === 0) {
            // dispatch('getHomeInfo')
        }
    },
    /**
     * 邀请码数据
     * @param commit
     * @returns {Promise<void>}
     */
    async getActiveCodeList({commit}) {
        let res = await api.getActiveCodeList();
        if (res.data.code === 0) {
            commit(types.INIT_ACTIVE_CODE_LIST, res.data)
        }
    },
    /**
     * 领取邀请码
     * @param commit
     * @returns {Promise<void>}
     */
    async getActiveCode({dispatch}) {
        let res = await api.getActiveCode();
        if (res.data.code === 0) {
            dispatch('getActiveCodeList')
        }
    },
    /**
     * 获取我的战队信息
     * @param commit
     * @returns {Promise<void>}
     */
    async getTeamInfo({commit}) {
        let res = await api.getTeamInfo();
        if (res.data.code === 0) {
            // ls.set("userInfo", res.data.info)
            commit(types.INIT_TEAM_INFO, res.data)
        }
    },
    /**
     * 获取我的战队信息
     * @param commit
     * @returns {Promise<void>}
     */
    async getApplyTeamInfo({commit}) {
        let res = await api.getTeamApplyInfo();
        if (res.data.code === 0) {
            // ls.set("userInfo", res.data.info)
            commit(types.INIT_MY_TEAM_INFO, res.data)
        }
    },
    async resetUserInfo({commit}){
        commit(types.INIT_RESET)
    }
}
