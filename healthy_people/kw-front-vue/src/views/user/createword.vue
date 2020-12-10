<template>
    <div class="mod-notice">
        <van-nav-bar
                title="创世合伙人"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <van-tabs  v-model="active">
            <van-tab title="我的下游">
                <van-pull-refresh v-model="refreshingUser" @refresh="onRefreshUser">
                    <van-list
                            v-model="loadingUser"
                            :finished="finishedUser"
                            finished-text="没有更多了"
                            @load="onLoadUser"
                    >
                <div class="item"  v-for="item in listUser"   :key="item.userId">
                    <div class="item-a">
                        <div>用户：{{item.mobile}}</div>
                        <div>动力值：{{item.power}}</div>
                    </div>
                    <div class="item-a">
                        <div>动力源：{{item.powerSource}}</div>
                        <div>OPC：{{item.parentMoney}}</div>
                    </div>
                </div>
                    </van-list>
                </van-pull-refresh>
            </van-tab>
            <van-tab title="下游日交易汇总">

                <van-pull-refresh v-model="refreshingTrans" @refresh="onRefreshTrans">
                    <van-list
                            v-model="loadingTrans"
                            :finished="finishedTrans"
                            finished-text="没有更多了"
                            @load="onLoadTrans"
                    >

                <div class="item"  v-for="item in listTrans"   :key="item.userId">
                    <div class="item-a">
                        <div>用户：{{item.mobile}}</div>
                        <div>买入：{{item.buyAmount}}</div>
                    </div>
                    <div class="item-a">
                        <div>手续费：{{item.fee}}</div>
                        <div>卖出：{{item.sellAmount}}</div>
                    </div>

                    <div class="item-a">

                        <div>日期：{{item.dateTime|formatDate}}</div>
                    </div>
                </div>
                    </van-list>
                </van-pull-refresh>
            </van-tab>
            <van-tab title="下游日业绩汇总">
                <van-pull-refresh v-model="refreshingDaybills" @refresh="onRefreshDaybills">
                    <van-list
                            v-model="loadingDaybills"
                            :finished="finishedDaybills"
                            finished-text="没有更多了"
                            @load="onLoadDaybills">
                <div class="item"   v-for="item in listDaybills"   :key="item.userId">
                    <div class="item-a">
                        <div>用户：{{item.mobile}}</div>
                        <div>动力值：{{item.power}}</div>
                    </div>
                    <div class="item-a">
                        <div>动力源：{{item.powerSource}}</div>
                        <div>日期：{{item.dateTime|formatDate}}</div>
                    </div>
                </div>
                    </van-list>
                </van-pull-refresh>
            </van-tab>
        </van-tabs>


    </div>
</template>

<script>
    // import tabs from '@/components/tabs.vue'
    import api from '@/api'
    // import { bignumber, format} from 'mathjs'
    // import {precision} from '@/utils/config'
    import moment from 'moment'
    export default {
        name: 'CreateWord',
        data (){
            return{
                pageIndexUser: 1,
                pageSizeUser: 10,
                totalPageUser: 0,
                listUser:[],
                loadingUser: false,
                finishedUser: false,
                refreshingUser: false,
                pageIndexTrans: 1,
                pageSizeTrans: 10,
                totalPageTrans: 0,
                listTrans:[],
                loadingTrans: false,
                finishedTrans: false,
                refreshingTrans: false,
                pageIndexDaybills: 1,
                pageSizeDaybills: 10,
                totalPageDaybills: 0,
                listDaybills:[],
                loadingDaybills: false,
                finishedDaybills: false,
                refreshingDaybills: false,
                active: 0
            };
        },
        computed: {
        },
        filters:{
            formatDate: function (value) {
                return moment(value).format('YYYY-MM-DD')
            },
        },
        methods: {

            onClickLeft(){

            },
            onLoadUser() {
                if (this.refreshingUser) {
                    this.listUser = [];
                    this.pageIndexUser = 1;
                    this.refreshingUser = false;
                }

                api.downUserList({
                    'page': this.pageIndexUser,
                    'limit': this.pageSizeUser,
                }).then(({data})=>{
                    if (data && data.code === 0) {
                    this.listUser = data.page.records
                    this.totalPageUser = data.page.pages
                }

                if(this.listUser.length >= this.totalPageUser){
                    this.finishedUser = true;
                }else{
                    this.pageIndexUser++;
                }
                this.loading = false;
            })
            },
            onLoadTrans() {
                if (this.refreshingTrans) {
                    this.listTrans = [];
                    this.pageIndexTrans = 1;
                    this.refreshingTrans = false;
                }

                api.downUserDaytrans({
                    'page': this.pageIndexTrans,
                    'limit': this.pageSizeTrans,
                }).then(({data})=>{

                if (data && data.code === 0) {
                    this.listTrans = data.page.records
                    this.totalPageTrans = data.page.pages
                }

                if(this.listTrans.length >= this.totalPageTrans){
                    this.finishedTrans = true;
                }else{
                    this.pageIndexTrans++;
                }
                this.loading = false;
            })
            },

            onLoadDaybills() {
                if (this.refreshingDaybills) {
                    this.listUser = [];
                    this.pageIndexDaybills = 1;
                    this.refreshingDaybills = false;
                }

                api.downUserDaybills({
                    'page': this.pageIndexDaybills,
                    'limit': this.pageSizeDaybills,
                }).then(({data})=>{

                if (data && data.code === 0) {
                    this.listDaybills = data.page.records
                    this.totalPageDaybills = data.page.pages
                }

                if(this.listDaybills.length >= this.totalPageDaybills){
                    this.finishedDaybills = true;
                }else{
                    this.pageIndexDaybills++;
                }
                this.loading = false;
            })
            },

            onRefreshUser() {
                this.finishedUser = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loadingUser = true;
                this.onLoadUser();
            },
            onRefreshTrans() {
                this.finishedTrans = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loadingTrans = true;
                this.onLoadTrans();
            },
            onRefreshDaybills() {
                this.finishedDaybills = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loadingDaybills = true;
                this.onLoadDaybills();
            }
        }
    }
</script>

<style lang="scss" scoped>

    @keyframes wordsLoop {
        0% {
            transform: translateX(200px);
            -webkit-transform: translateX(200px);
        }
        100% {
            transform: translateX(-100%);
            -webkit-transform: translateX(-100%);
        }
    }

    @-webkit-keyframes wordsLoop {
        0% {
            transform: translateX(200px);
            -webkit-transform: translateX(200px);
        }
        100% {
            transform: translateX(-100%);
            -webkit-transform: translateX(-100%);
        }
    }

    .mod-notice {
        .item{
            border-top:1px solid #f2f2f2;
            padding:20px;
            .item-a{
                line-height: 60px;
                color:#999;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
        }
    }
</style>
