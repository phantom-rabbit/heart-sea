import * as types from './mutation-types'
import api from '@/api'
import {Toast} from 'vant'

export const actions = {
    async loadLtcList({commit}, o){
        api.getLtcConfig(o).then(res=>{
            if(res.data.code === 0){
                commit(types.INIT_LTC_LIST, res.data.page)
            }else{
                commit(types.LOAD_LTC_FINISHED, true)
                Toast.fail(res.data.msg)
            }
        })
    }
}
