import * as types from './mutation-types'
import {chain, bignumber, format} from 'mathjs'
import {precision} from '@/utils/config'



function exists(arr, n) {
    for(let i = 0; i < arr.length; i++){
        if(arr[i].icon === n){
            return true;
        }
    }

    return false;
}




export const mutations = {
    [types.INIT_BANK_LIST](state, o) {
        state.bankList = o
    },
    [types.INIT_TAB_INDEX](state, o) {
        state.tabIndex = o
    },
    [types.INIT_AMOUNT_ED](state, o) {
        state.totalAmounted = o
    },
    [types.NOTICE_DETAIL](state, o) {
        state.noticeDetail = o
    },
    [types.TRANS_DETAIL](state, o) {
        state.transDetail = o
    },
    [types.SD_INFO](state, o) {
        state.sdInfo = o
    },
    [types.SET_BG](state, o) {
        state.bg = o
    },
    [types.SET_HOME_AUTH_COLLECT_START](state, o) {
        state.startHomeAutoCollect = o
    },
    [types.ADD_ACCOUNT_AMOUNT](state, o) {
        if (state.account) {
            if (state.account.parentMoney) {
                state.account.parentMoney = format(chain(bignumber(state.account.parentMoney)).add(bignumber(o)).done(), {
                    notation: 'fixed',
                    precision: precision
                })
            } else {
                state.account.parentMoney = format(bignumber(o), {notation: 'fixed', precision: precision})
            }
        } else {
            state.account = {}
            state.account.parentMoney = format(bignumber(o), {notation: 'fixed', precision: precision})
        }
    },
    [types.INIT_HOME_INFO](state, o) {

        state.account = o.account || {}
        state.totalAmount = o.income || 0
        state.noticeList = o.noticeList || []

        state.totalAmounted = 0
        state.startHomeAutoCollect = false

        if (state.noticeList.length > 0) {
            state.noticeHome = state.noticeList[0]
        }
        if (state.amountIcon.length > 0) {
            let sum = 0;
            state.amountIcon.forEach(item => {
                sum = format(chain(bignumber(sum)).add(bignumber(item.amount)).done(), {
                    notation: 'fixed',
                    precision: precision
                })
            })
            if (sum === format(bignumber(state.totalAmount), {notation: 'fixed', precision: precision})) {
                return
            }
        }

        state.amountIcon = []
        if (state.totalAmount > 0) {
            //固定显示
            let n = state.rbNum;
            //总图标数量
            let iconNum = state.iconNum;
            let icons = [];
            while (icons.length < n){
                let num = Math.floor(Math.random()*10000000)%iconNum;
                if(!exists(icons, num)){//数组中不存在
                    icons.push({icon:num, name: state.iconNames[num]});
                }
            }
            let amount = state.totalAmount;
            let avgAm = chain(bignumber(amount)).divide(bignumber(n)).done();
            let am = 0
            state.amountIcon = icons.map((item,index) => {
                if(index === icons.length - 1){
                    item.amount = format(chain(bignumber(state.totalAmount)).subtract(bignumber(am)).done(), {
                        notation: 'fixed',
                        precision: precision
                    })
                    return item;
                }
                item.amount = format(avgAm, {notation: 'fixed',precision: precision})
                am = chain(bignumber(am)).add(bignumber(avgAm)).done()
                am = format(am, {notation: 'fixed',precision: precision})
                return item;
            })
        }

    }
}
