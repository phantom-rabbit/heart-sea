<template>
    <div class="mine">
        <div class="bg">
            <img src="~@/assets/img/common/bg_0.jpg" width="100%" height="100%"/>
        </div>

        <div class="data-groups-top">
            <div class="inner">
                <div class="face">
                    <a href="#"><img class="logo" src="../assets/img/common/logo.png"></a>
                </div>
                <div style="flex:1">
                    <div class="name">{{account.levelName}}

                        <span class="zzz" v-if="leader">战</span>
                    </div>
                    <div class="text">UID:{{userInfo.id}}</div>
                    <div class="text2">保护环境人人有责</div>
                </div>
                <div class="status">
                    <div class="txt" @click="$router.push('/userAuth')">{{account.realStatus===2?'已认证':'未认证'}}</div>
                </div>
            </div>
            <div class="data-group">
                <div class="data-col">
                    <div class="col-top">{{account.vipName || '暂无'}}</div>
                    <div class="col-down">会员等级</div>
                </div>
                <div class="data-col" @click="$router.push('/accountLoglist')">
                    <div class="col-top">{{account.parentMoney}}</div>
                    <div class="col-down">{{$config.coin}}</div>
                </div>
                <div class="data-col">
                    <div class="col-top">{{account.hyd}}</div>
                    <div class="col-down">动力值</div>
                </div>
                <div class="data-col" @click="$router.push('/powerList')">
                    <div class="col-top">{{account.powerSource}}</div>
                    <div class="col-down">动力源</div>
                </div>
            </div>
        </div>
        <div class="data-groups">
            <div class="data-group-menus">
                <div class="data-col-menus" @click="$router.push('/userAuth')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/realname.png"></div>
                    <div class="col-down-menus">实名认证</div>
                </div>
                <div class="data-col-menus" @click="$router.push('/ucenter')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/person.png"></div>
                    <div class="col-down-menus">个人信息</div>
                </div>
                <div class="data-col-menus" @click="$router.push('/userTransaction')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/transaction.png"></div>
                    <div class="col-down-menus">我的交易</div>
                </div>
                <div class="data-col-menus" @click="$router.push('/accountLoglist')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/accountlog.png"></div>
                    <div class="col-down-menus">资金明细</div>
                </div>
            </div>
            <div class="data-group-menus">
                <div class="data-col-menus">
                    <div class="col-top-menus" @click="mailtous()"><img src="~@/assets/img/mine/customer.png"></div>
                    <div class="col-down-menus">客服中心</div>
                </div>
                <div class="data-col-menus" @click="$router.push('/activeCode')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/team.png"></div>
                    <div class="col-down-menus">战队招募</div>
                </div>
                <div class="data-col-menus" @click="$router.push('/myTeam')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/myteam.png"></div>
                    <div class="col-down-menus">我的战队</div>
                </div>
                <div class="data-col-menus" @click="$router.push(`/noticeList?title=教程指南&t=1`)">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/guide.png"></div>
                    <div class="col-down-menus">教程指南</div>
                </div>
            </div>
            <div class="data-group-menus">
                <div class="data-col-menus" @click="hbxy">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/school.png"></div>
                    <div class="col-down-menus">环保学院</div>
                </div>
                <div class="data-col-menus">
                    <div class="col-top-menus" @click="$toast.fail('暂未开放')"><img src="~@/assets/img/mine/shop.png"></div>
                    <div class="col-down-menus">闲置商城</div>
                </div>
                <div class="data-col-menus">
                    <div class="col-top-menus" @click="$toast.fail('暂未开放')"><img src="~@/assets/img/mine/game.png"></div>
                    <div class="col-down-menus">游戏</div>
                </div>
                <div class="data-col-menus" @click="$router.push('/setting')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/setting.png"></div>
                    <div class="col-down-menus">设置</div>
                </div>

            </div>

            <div class="data-group-menus" style="justify-content: flex-start">

                <div class="data-col-menus" v-if="sdInfo.sdOpen==1||account.sdStatus>0" @click="$router.push('/sd')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/sd.png"></div>
                    <div class="col-down-menus">市代</div>
                </div>
                <div class="data-col-menus" v-if="cw!=null" @click="$router.push('/createWord')">
                    <div class="col-top-menus"><img src="~@/assets/img/mine/point.png"></div>
                    <div class="col-down-menus">{{cw.name}}</div>
                </div>
                <div class="data-col-menus">
                    <div class="col-top-menus"></div>
                    <div class="col-down-menus"></div>
                </div>
                <div class="data-col-menus">
                    <div class="col-top-menus"></div>
                    <div class="col-down-menus"></div>
                </div>

            </div>


        </div>

        <tabs class="tabs"/>
    </div>
</template>

<script>
    import tabs from '@/components/tabs.vue'
    import api from '@/api'
    import {mapActions, mapState} from 'vuex'

    export default {
        name: 'User',
        data() {
            return {
                copy:''
            };
        },
        computed: {
            ...mapState('user', ['userInfo', 'account', 'aList','cw', 'leader']),
            ...mapState('common', ['sdInfo'])
        },
        components: {
            tabs
        },
        methods: {
            ...mapActions('user', ['getUserInfo','getApplyTeamInfo']),
            ...mapActions('common', ['getHomeInfo','selectTab','getSdInfo','setNoticeDetail']),

            mailtous(){
                this.copy = "举报处理客服微信ACTION8999,市场信息客服微信lw7799131431";
                this.$copyText(this.copy).then(function () {
                    alert("举报处理客服微信ACTION8999，\n" +
                        "市场信息客服微信lw7799131431,已经拷贝到您的剪贴板");
                }, function () {

                })

                //window.location.href = "mailto:opcocean@outlook.com";
            },
            hbxy(){
                api.getNoticeList({
                    'page': 1,
                    'type': 3,
                    'id':1,
                    'limit': 1,
                }).then(({data})=>{
                    if (data && data.code === 0) {
                        let list = data.page.list
                        if(list instanceof Array && list.length === 1){
                            this.setNoticeDetail(list[0])
                            this.$router.push('/noticeDetail')
                        }
                    }
                    if(this.list.length >= this.totalPage){
                        this.finished = true;
                    }else{
                        this.pageIndex++;
                    }
                    this.loading = false;
                })
            }
        },
        created() {
            this.selectTab(4)
            this.getUserInfo()
            this.getHomeInfo()
            this.getSdInfo()
        }
    }

</script>


<style lang="scss" scoped>
    .bg {
        position: fixed;
        top:0;
        left:0;
        z-index: -1;
        width: 100%;
        height: 100%;
    }
    .mine {
        /*background-image: url("~@/assets/img/common/bg_0.jpg");*/
        overflow: hidden;
        /*background-position: top center;*/
        /*background-size: 100% 92%;*/
        /*background-repeat: no-repeat;*/
        /*background-attachment: fixed;*/
        width: 750px;
        height: 1500px;
        display: flex;
        position: relative;
        flex-flow: column;
        color: #999;
        align-items: center;
        .data-groups {
            padding: 20px;
            width: 90%;
            margin: auto 0;
            margin-top: 20px;
            background: url(~@/assets/img/mine/bg_groups.png), no-repeat;
            background-position: center center;
            background-size: 100% 100%;

            .data-group-menus {
                margin-top: 30px;
                justify-content: center;
                width: 100%;
                display: flex;
                border-radius: 10px;

                .data-col-menus {
                    padding: 10px;
                    text-align: center;
                    align-items: center;
                    width: 25%;

                    .col-top-menus img {
                        width: 80px;
                        height: 80px;
                    }

                    .col-down-menu {
                        font-size: 32px;
                        margin-top: 10px;

                    }

                }
            }
        }

        .data-groups-top {
            width: 750px;
            /*border-radius: 30px;*/
            display: flex;
            flex-direction: column;
            color: #fff;
            /*margin-top: 106px;*/
            /*background: url(~@/assets/img/mine/bg_grous_top.png),no-repeat;*/
            /*background-position: center center;*/
            /*background-size: 100% 100%;*/
            background: linear-gradient(#3db1ff, #4cabe7);
            box-shadow: 0 20px 20px #4cabe7;

            .inner {
                display: flex;
                justify-content: space-between;
                align-items: center;
                height: 200px;
                padding-left: 20px;


                .zzz{
                    background-color: #f9655e;
                    color: #fff;
                    padding:5px 10px;
                    display: inline-block;
                    border-radius: 50px;
                    font-size: 20px;
                }
                .face {
                    margin-right: 50px;

                    img {
                        width: 101px;
                        height: 101px;
                    }
                }

                .name {
                    font-size: 35px;
                    color: #ededed;
                    font-weight: 900;
                }

                .status{
                    width: 200px;
                    text-align: center;
                    .txt{
                        font-size: 20px;
                        border: 1px solid #ffffff;
                        color: #ffffff;
                        border-radius: 50px;
                        height: 50px;
                        display: flex;
                        width: 150px;
                        justify-content: center;
                        align-items: center;
                    }
                }
            }

            .data-group {
                margin-top: 5px;
                justify-content: center;
                width: 100%;
                display: flex;
                border-radius: 10px;
                margin-bottom: 10px;
                color:#f2f2f2;
                .data-col {
                    padding: 5px;
                    text-align: center;
                    align-items: center;

                    width: 25%;

                    .col-top {
                        font-size: 32px;
                        color: #ffffff;
                    }

                    .col-down {
                        font-size: 24px;
                        margin-top: 10px;

                    }

                }


            }
        }

    }
</style>
