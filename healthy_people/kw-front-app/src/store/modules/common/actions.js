import * as types from './mutation-types'
import api from '@/api'
const ls = require('local-storage');
export const actions = {
    selectTab({commit}, o) {
        commit(types.INIT_TAB_INDEX, o)
    },
    async getHomeInfo({commit}) {
        let res = await api.getHome();
        if (res.data && res.data.code === 0) {

            commit(types.INIT_HOME_INFO, res.data)
        }
    },
    setTotalAmounted({commit}, o) {
        commit(types.INIT_AMOUNT_ED, o)
    },
    addAccountAmount({commit}, o) {
        commit(types.ADD_ACCOUNT_AMOUNT, o)
    },
    setNoticeDetail({commit}, o) {
        commit(types.NOTICE_DETAIL, o)
    },
    async getSdInfo({commit}) {
        let res = await api.getSdinfo();
        commit(types.SD_INFO, res.data.sdInfo)
    },
    async getBanks({commit}) {
        let res = await api.banks();
        commit(types.INIT_BANK_LIST, res.data.page)
    },
    async getTransDetail({commit}, o) {

        const param = {
            id: o
        }
        let res = await api.getTransDetail(param);
        if (res.data && res.data.code === 0) {

            if (res.data.item.photo != null) {
                res.data.item.fileList = [];

                const url = {
                    url: res.data.item.photo
                }

                res.data.item.fileList.push(url);

            }
            commit(types.TRANS_DETAIL, res.data.item)
        }
    },
    setBg({commit}) {
        let bg =  ls.get('bg');


        if(bg === undefined||bg==null){
            ls.set('bg', 0)
            commit(types.SET_BG, 0)
        }else{
            bg =  (parseInt(bg)+1)%3;
            ls.set('bg',bg)
            commit(types.SET_BG, bg)
        }
    },
    startHomeAutoCollectAction({commit},o) {
        commit(types.SET_HOME_AUTH_COLLECT_START, o)
    },

}
