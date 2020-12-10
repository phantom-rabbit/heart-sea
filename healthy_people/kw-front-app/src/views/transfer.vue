<template>
    <div class="transfer">
        <div class="bg">
            <img src="~@/assets/img/common/bg_0.jpg" width="100%" height="100%"/>
        </div>
        <div class="transfertab">
            <div class="tab"><i @click="search(1)"><img src="~@/assets/img/transfer/transshop.png" alt=""></i></div>
            <div class="tab"><i @click="search(2)"><img src="~@/assets/img/transfer/mytrans.png" alt=""></i></div>
            <div class="tab"><i @click="search(3)"><img src="~@/assets/img/transfer/overtrans.png" alt=""></i></div>
        </div>

        <div class="ep-groups" v-if="type===1">
            <div class="eg-item" v-for="item in ltcList" :key="item.id">
                <div class="eg-media">
                    <img :src="item.ltcImg" alt="" class="round">
                </div>
                <div class="eg-list-inner">
                    <div class="row">
                        <div class="row-text">{{item.name}}</div>
                    </div>
                    <div class="blank"></div>
                    <div class="subtitle">动力值：{{item.power}}</div>
                    <div class="subtitle">周期：{{item.days}}天</div>
                    <div class="subtitle">总产值：{{Math.round(item.days*item.incomeParentPerday)}}{{$config.coin}}</div>
                    <div class="subtitle">&nbsp;</div>
                </div>
                <div class="row-remark">
                    <p class="price">消耗：{{item.parentPrice}}{{$config.coin}}</p>
                    <p>
                        <button class="buyBtn" @click="duihuan(item.id)">兑换</button>
                    </p>
                </div>
            </div>
        </div>

        <van-pull-refresh v-model="refreshingDaybills" @refresh="refresh" v-if="type!==1">
            <van-list
                    v-model="loading"
                    :finished="finished"
                    finished-text="没有更多了"
                    @load="onLoad">
                <div class="ep-groups">
                    <div class="eg-item item1" v-for="item in list" :key="item.id">

                        <div class="eg-media">
                            <img :src="item.ltcImg" alt="" class="round">
                        </div>
                        <div class="eg-list-inner">
                            <div class="row">
                                <div class="row-text">{{item.name}}</div>
                            </div>
                            <div class="blank"></div>
                            <div class="subtitle">动力值: {{item.power}}</div>
                            <div class="subtitle">周期: {{item.days}}天</div>
                            <div class="subtitle">总产值: {{parseInt(item.days*item.incomeParentPerday)}}{{$config.coin}}</div>
                            <div class="subtitle">剩余时间: {{item.days-item.runDays}}天</div>
                        </div>
                        <div class="row-remark">
                            <p class="price">已产：{{(item.runDays*item.incomeParentPerday).toFixed($config.precision)}}{{$config.coin}}</p>
                            <p>
                                <button class="buyBtn" @click="startLtc(item.id,item.ltcStatus)">
                                    {{item.ltcStatus|formatLtcStatus}}
                                </button>
                            </p>
                        </div>
                        <div class="jb" v-if="item.isAward==1"></div>
                    </div>
                </div>
            </van-list>
        </van-pull-refresh>
        <tabs class="tabs"/>
    </div>
</template>

<script>
    // @ is an alias to /src
    import tabs from '@/components/tabs.vue'
    import api from '@/api'
    import {mapActions, mapState} from 'vuex'

    export default {
        name: 'Transfer',
        components: {
            tabs
        },
        data() {
            return {
                list: [],
                loading: false,
                finished: false,
                page: 1,
                type: 1,
                refreshingDaybills: false,

            }
        },
        created() {
            this.selectTab(2)
            this.getHomeInfo()
            this.loadLtcList({page: this.page, limit: 1000});
        },
        filters: {
            formatLtcStatus: function (value) {
                if (value == "0") {
                    return "启  动";
                }
                if (value == "-1") {
                    return "已过期";
                }
                if (value == "1") {
                    return "运行中";
                }
            }
        },
        computed: {
            ...mapState('transfer', ['ltcList'])
        },
        methods: {
            ...mapActions('common', ['selectTab','getHomeInfo']),
            ...mapActions('transfer', ['loadLtcList']),
            startLtc(id, status) {
                if (status == 0) {
                    api.startLtc({
                        id: id,
                    }).then(res => {
                        if (res.data.code === 0) {
                            this.$toast.success(res.data.msg)
                            this.getHomeInfo();
                            this.search(2)
                        } else {
                            this.$toast.fail(res.data.msg)
                        }
                    });
                }
            },
            search(id) {
                this.type = id;
               this.refresh()
            },
            duihuan(id) {
                api.buyLtc({
                    configId: id,
                }).then(res => {
                    if (res.data.code === 0) {
                        this.$toast.success(res.data.msg)
                    } else {
                        this.$toast.fail(res.data.msg)
                    }
                });
            },
            refresh() {
                this.list = [];
                this.loading = false;
                this.finished = false;
                this.page = 1;
            },
            onLoad() {
                if (this.refreshingDaybills) {
                    this.list = [];
                    this.page = 1;
                    this.refreshingDaybills = false;
                }
                let self = this;
                console.log("onLoad function strike!");
                this.loading = false;
                this.finished=true;
                api.getUserLtcList({
                    page: this.page,
                    limit: 10000000,
                    type: this.type
                }).then(res => {
                    if (res.data.code === 0) {
                        // let totalPages = res.data.page.totalPage;
                        self.finished = true;
                        if (res.data.page.list instanceof Array) {
                            res.data.page.list.forEach(item => {
                                self.list.push(item);
                            })
                        }
                    } else {
                        self.finished = true;
                        this.$toast.fail(res.data.msg)
                    }
                })

            }
        }

    }


</script>


<style lang="scss" scoped>
   /* .transfer:before {
        content: "";
        background: url("~@/assets/img/common/bg_0.jpg") no-repeat;
        background-size: 100% 100%;
        background-repeat: no-repeat;
        position: fixed;
        top: 1rem;
        left: 0;
        bottom: 0;
        right: 0;
        z-index: -1;
    }*/
   .bg {
       position: fixed;
       top:0;
       left:0;
       z-index: -1;
       width: 100%;
       height: 100%;
   }
    .transfer {
        color: #999;
        font-family: "Helvetica Neue", Helvetica, sans-serif;
        overflow: hidden;
        width: 750px;
        min-height: 1920px;
        /*height: 2260px;*/
        .transfertab {
            height: 200px;
            position: fixed;
            display: flex;
            justify-content: center;
            align-items: center;
            width: 750px;
            z-index: 1000;
            .tab {
                box-sizing: border-box;
                width: 220px;
                margin: 0 8px;
            }

            img {
                width: 100%
            }
        }

        .ep-groups {
            display: flex;
            flex-wrap: wrap;
            margin: 16px;
            border-radius: 16px;
            margin-top:200px;
            -webkit-overflow-scrolling: touch;
            padding-bottom:150px;
            overflow-y: scroll;
            margin-bottom:100px;
            .item1 {
                height: 244px !important;
            }
            .item2 {
                height: 244px !important;
            }


            .eg-item {
                height: 224px;
                width: 700px;
                display: flex;
                padding: 16px;
                position: relative;
                background: url(~@/assets/img/transfer/bg_item.png) no-repeat;
                background-size: 100% 100%;
                margin-bottom: 16px;
                .jb{
                    position: absolute;
                    top: 5px;
                    right: 5px;
                    width: 80px;
                    height: 80px;
                    background: url(~@/assets/img/transfer/award_icon.png) no-repeat;
                    background-size: 100% 100%;
                }
                .eg-media {
                    display: flex;
                    align-items: center;
                    margin-right: 8px;
                    color: #aaa;
                    /*position: relative;*/

                    img {
                        width: 160px;
                        border-radius: 4.8px;
                    }
                }

                .eg-list-inner {
                    margin-left: 20px;
                    width: 260px;
                    padding: 2px;
                    display: block;
                    /*position: relative;*/

                    .row {
                        /*position: relative;*/
                        margin-top: 5px;
                        height: 32px;
                        line-height: 32px;
                        -webkit-box-flex: 1;
                        margin-bottom: 12px;

                        .row-text {
                            text-overflow: ellipsis;
                            overflow: hidden;
                            font-weight: bolder;
                            color: #666;
                            font-size: 30px;
                        }
                    }

                    .subtitle {
                        margin-top: 6px;
                        font-size: 28px;
                        color: #8f8f94
                    }
                }

                .row-remark {
                    margin-right:20px;
                    p {
                        margin-block-start: 0px;
                        margin-block-end: 0px;
                        margin-inline-start: 0px;
                        margin-inline-end: 0px;
                    }

                    .price {
                        margin-top: 60px;
                        color: #ff9326;
                        font-weight: bolder;
                        font-size: 20px;
                    }

                    .buyBtn {
                        background: url(~@/assets/img/transfer/buyBtn.png) no-repeat;
                        background-size: 100% 100%;
                        border-style: none;
                        font-size: 32px;
                        line-height: 32px;
                        /*position: relative;*/
                        display: inline-block;
                        margin-bottom: 0;
                        padding: 12px 24px;
                        color: #fff;
                        margin-top:20px;
                    }

                    text-align: right;
                    /*position: relative;*/
                    flex-shrink: 0;
                    margin-left: auto;
                    padding-left: 32px;
                }
            }
            .data_0{
                background: url("~@/assets/img/transfer/bg_item.png") no-repeat;
                background-size: 100% 100%;
            }

            .data_1{
                background: url("~@/assets/img/transfer/bg_item_1.png") no-repeat;
                background-size: 100% 100%;

            }


        }
    }
</style>
