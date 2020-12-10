<template>
    <div class="mod-notice">
        <van-nav-bar
                title="动力源记录"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />

        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <van-list
                    v-model="loading"
                    :finished="finished"
                    finished-text="没有更多了"
                    @load="onLoad"
            >
                <van-cell v-for="item in list"  :key="item.id" :title="item.money" :value="item.mark"/>

            </van-list>
        </van-pull-refresh>
    </div>
</template>

<script>
    // import tabs from '@/components/tabs.vue'

    import {mapActions, mapState} from 'vuex'
    // import { bignumber, format} from 'mathjs'
    // import {precision} from '@/utils/config'
    import api from '@/api'
    export default {
        name: 'powerList',
        data (){
            return{
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                list:[],
                loading: false,
                finished: false,
                refreshing: false
            };
        },
        computed: {
            ...mapState('common', ['account','noticeHome'])
        },
        methods: {
            ...mapActions('common',['setNoticeDetail']),
            onClickLeft(){},

            onLoad() {
                if (this.refreshing) {
                    this.list = [];
                    this.pageIndex = 1;
                    this.refreshing = false;
                }

                api.getPowerList({
                    'page': this.pageIndex,
                    'limit': this.pageSize,
                }).then(({data})=>{
                    if (data && data.code === 0) {
                        this.list = this.list.concat(data.page.list)
                        this.totalPage = data.page.totalCount
                    }
                    if(this.list.length >= this.totalPage){
                        this.finished = true;
                    }else{
                        this.pageIndex++;
                    }
                    this.loading = false;
                })
            },
            onRefresh() {
                // 清空列表数据
                this.finished = false;

                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.loading = true;
                this.onLoad();
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

    .home {
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
            .more{
                display: inline-block;
                width: 11px;
                height: 52px;
                background-image: url("~@/assets/img/home/more.png");
                background-size: 100% 100%;
                background-position: center center;
                background-repeat: no-repeat;
                margin-left: 32px;
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
