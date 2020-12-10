<template>
    <div class="home" :class="'_'+bg">
        <div style="text-align: center;font-size:30px;position:absolute;top:60px;right: 10px"></div>
        <div class="note-box">
            <div class="notice">
                <div class="icon"></div>
                <div class="content " @click="detail()">
                    <div class="animate" >{{noticeHome.title}}</div>
                </div>
            </div>
            <div class="more-box">
                <div class="more" @click="$router.push(`/noticeList?title=通知&t=0`)">公告</div>
            </div>

        </div>
        <div class="account-box" ref="rubbish" @click="setBg">
            <div class="value">{{account.parentMoney}} {{$config.coin}}</div>
        </div>
        <rubbish  />
        <tabs class="tabs"/>
    </div>
</template>

<script>
    import tabs from '@/components/tabs.vue'
    import rubbish from '@/components/rubbish.vue'

    import {mapActions, mapState} from 'vuex'
    // import { bignumber, format} from 'm公共athjs'
    // import {precision} from '@/utils/conrfig'
    export default {
        name: 'Home',
        data (){
            return{
                show: true,
                target:{},
                amount: 0,
            };
        },
        computed: {
            ...mapState('common', ['account','noticeHome','bg']),
        },
        components: {
            tabs,
            rubbish
        },
        methods: {
            ...mapActions('common',['getHomeInfo','setNoticeDetail','selectTab','setBg']),
            ...mapActions('user',['getUserInfo']),
            detail(){
                if(this.noticeHome && this.noticeHome.id){
                    this.setNoticeDetail(this.noticeHome)
                    this.$router.push('/noticeDetail')
                }
            },
        },
        mounted(){
            console.log(this.account)
        },
        created() {
            this.selectTab(1)
            this.getHomeInfo()
            this.getUserInfo()
        },

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
    .home {
        position: fixed;
        overflow: hidden;
        background-position: top center;
        background-size: 100% 92%;
        background-repeat: no-repeat;
        background-attachment: fixed;
        width: 750px;
        height: 100%;
        padding-top:20px;

        &._0{
            background-image: url("~@/assets/img/common/bg_0.jpg");
        }

        &._1{
            background-image: url("~@/assets/img/common/bg_1.jpg");
        }

        &._2{
            background-image: url("~@/assets/img/common/bg_2.jpg");
        }

        .note-box{
            display: flex;
            align-items: center;
            margin-left:23px;
            .notice {
                background-color: #fff;
                width: 644px;
                height: 70px;
                overflow: hidden;
                border-radius: 70px;
                display: flex;
                justify-content: center;
                align-items: center;
                padding-left:10px;
                .icon{
                    display: inline-block;
                    width: 57px;
                    height: 46px;
                    background-image: url("~@/assets/img/home/icon-notice.png");
                    background-size: 100% 100%;
                    background-position: center center;
                    background-repeat: no-repeat;
                }
                .content{
                    .animate {
                        display: inline-block;
                        white-space: nowrap;
                        animation: 10s wordsLoop linear infinite normal;
                    }
                    flex: 1;
                    /*background-color: #7fd4ff;*/
                    text-align: left;
                    padding: 0 20px;
                    overflow: hidden;
                    white-space: nowrap;
                    text-overflow: ellipsis;
                    color: #888;
                    font-size: 28px;
                }
            }
            .more-box{
                width: 100px;
                display: flex;
                justify-content: center;
                align-items: center;
                .more{
                    display: inline-block;
                    /*width: 11px;*/
                    /*height: 52px;*/
                    /*background-image: url("~@/assets/img/home/more.png");*/
                    /*background-size: 100% 100%;*/
                    /*background-position: center center;*/
                    /*background-repeat: no-repeat;*/
                }
            }


        }

        .account-box{
            background-image: url("~@/assets/img/home/account_bg.png");
            overflow: hidden;
            background-position: center center;
            background-size: 100% 100%;
            background-repeat: no-repeat;
            width: 213px;
            height: 97px;
            margin-left:23px;
            margin-top: 24px;

            display: flex;
            justify-content: center;
            align-items: center;
            .value{
                margin-left:100px;
                font-size: 18px;
                font-weight: 800;
                color: #7fd4ff;
            }
        }
    }
</style>
