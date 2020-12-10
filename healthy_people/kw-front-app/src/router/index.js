import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/index.vue'
import {clearLoginInfo, isLogin} from '../utils/index'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/transfer',
        name: 'TransFer',
        component: () => import('../views/transfer.vue')
    },
    {
        path: '/awardCode',
        name: 'AwardCode',
        component: () => import('../views/user/awardCode.vue')
    },
    {
        path: '/payment',
        name: 'Payment',
        component: () => import('../views/user/payment.vue')
    },
    {
        path: '/createword',
        name: 'CreateWord',
        component: () => import('../views/user/createword.vue')
    },

    {
        path: '/sd',
        name: 'Sd',
        component: () => import('../views/user/sdApply.vue')
    },

    {
        path: '/usertransaction',
        name: 'Usertransaction',
        component: () => import('../views/user/usertransaction.vue')
    },
    {
        path: '/transbuyer',
        name: 'transbuyer',
        component: () => import('../views/user/transbuyer.vue')
    },
    {
        path: '/transeller',
        name: 'transeller',
        component: () => import('../views/user/transeller.vue')
    },
    {
        path: '/payments',
        name: 'Payments',
        component: () => import('../views/user/payments.vue')
    },
    {
        path: '/transaction',
        name: 'Transaction',
        component: () => import('../views/transaction.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('../views/common/register.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/common/login.vue')
    },
    {
        path: '/noticeList',
        name: 'noticeList',
        component: () => import('../views/notice/list.vue')
    },
    {
        path: '/noticeDetail',
        name: 'noticeDetail',
        component: () => import('../views/notice/detail.vue')
    },
    {
        path: '/user',
        name: 'user',
        component: () => import('../views/mine.vue')
    },
    {
        path: '/accountLoglist',
        name: 'accountLoglist',
        component: () => import('../views/user/accountLoglist.vue')
    },
    {
        path: '/bindAuthCode',
        name: 'bindAuthCode',
        component: () => import('../views/user/bindAuthCode.vue')
    },

    {
        path: '/ucenter',
        name: 'ucenter',
        component: () => import('../views/user/ucenter.vue')
    },

    {
        path: '/userAuth',
        name: 'userAuth',
        component: () => import('../views/user/userAuth.vue')
    },
    {
        path: '/powerList',
        name: 'powerList',
        component: () => import('../views/user/powerList.vue')
    },

    {
        path: '/setting',
        name: 'setting',
        component: () => import('../views/user/setting.vue')
    },
    {
        path: '/forget',
        name: 'forget',
        component: () => import('../views/user/forget.vue')
    },
    {
        path: '/forgetReset',
        name: 'forgetReset',
        component: () => import('../views/user/forgetReset.vue')
    },
    {
        path: '/payPassword',
        name: 'payPassword',
        component: () => import('../views/user/payPassword.vue')
    },
    {
        path: '/password',
        name: 'password',
        component: () => import('../views/user/password.vue')
    },
    {
        path: '/activeCode',
        name: 'activeCode',
        component: () => import('../views/user/activeCode.vue')
    },
    {
        path: '/applyTeamCaptain',
        name: 'applyTeamCaptain',
        component: () => import('../views/user/apply-team-captain.vue')
    },
    {
        path: '/myTeam',
        name: 'myTeam',
        component: () => import('../views/user/my-team.vue')
    },
    {
        path: '/xq',
        name: 'xq',
        component: () => import('../views/xq.vue')
    },
    {
        path: '/inviter',
        name: 'inviter',
        component: () => import('../views/user/inviter.vue')
    }
]

const router = new VueRouter({
    mode: 'hash',
    routes
})


const whiteList = ['login', 'register', 'forget', 'forgetReset']

router.beforeEach((to, from, next) => { //全局钩子函数
    if (!whiteList.includes(to.name) && !isLogin()) {
        clearLoginInfo()
        next({name: 'login'})
    }
    next()
});

export default router
