<template>
    <div class="mod-notice">
        <van-nav-bar
                :title="$config.coin+'记录'"
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
                <div v-for="item in list"  :key="item.id" class="item" >
                    <div>
                        <div slot="title">
                            <span :class="getColor(type[item.logType])">{{getType(type[item.logType])}} {{item.money}}</span>
                            <span class="coin" :class="getColor(type[item.logType])" v-if="item.moneyKind === 1">{{$config.coin}}</span>
                            <span class="coin" :class="getColor(type[item.logType])" v-if="item.moneyKind === 2">{{$config.sonCoin}}</span>
                            <span class="coin" :class="getColor(type[item.logType])" v-if="item.moneyKind === 3">动力值</span>
                            <span class="coin" :class="getColor(type[item.logType])" v-if="item.moneyKind === 4">动力源</span>
                        </div>
                    </div>
                    <div class="item-sub">
                        <div class="a" >
                            <span :class="getColor(type[item.logType])">{{item.mark}}</span>
                        </div>
                        <div class="txtColor a b">
                            {{$moment(item.createTime).format('YYYY-MM-DD HH:mm:ss')}}
                        </div>
                    </div>
                </div>

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
                type:{},
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
            getColor(type){
                switch (type) {
                    case 'in':
                        return 'green';
                    case 'out':
                        return 'red';
                    case 'frezen':
                        return 'blue';
                    case 'frezen_out':
                        return 'red';
                    case 'frezen_in':
                        return 'green';
                }
            },
            getType(type){
                switch (type) {
                    case 'in':
                        return '+';
                    case 'out':
                        return '-';
                    case 'frezen':
                        return '冻';
                    case 'frezen_out':
                        return '-';
                    case 'frezen_in':
                        return '+';
                }
            },
            onLoad() {
                if (this.refreshing) {
                    this.list = [];
                    this.pageIndex = 1;
                    this.refreshing = false;
                }

                api.getAccountLogList({
                    'page': this.pageIndex,
                    'limit': this.pageSize,
                }).then(({data})=>{
                    this.type = data.type;

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
    .mod-notice {
        .item{
            height: 120px;
            border-bottom:1px solid #f2f2f2;
            padding:0 10px;
            display: flex;justify-content: space-between;align-items: center;

            .item-sub{
                height: 120px;
                display: flex;
                flex-flow: column;
                justify-content: center;
                .a{
                    height: 60px;

                    display: flex;
                    justify-content: flex-end;
                    align-items: center;

                    &.b{
                        font-size:20px;
                    }
                }
            }
        }
        .green{
            color: #1a9325;
        }

        .red{
            color:#da6869;
        }

        .blue{
            color:#03A9F4;
        }

        .txtColor{
            color:#888;
        }

        .coin{
            margin-left:20px;
        }
    }
</style>
