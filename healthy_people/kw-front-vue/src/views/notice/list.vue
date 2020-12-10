<template>
    <div class="mod-notice">
        <van-nav-bar
                :title="$route.query.title?$route.query.title:'通知公告'"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />

        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
            <div v-if="!list || list.length === 0">
                <!-- 通用错误 -->
                <van-empty image="error" description="" />
            </div>
            <van-list
                    v-model="loading"
                    :finished="finished"
                    finished-text="没有更多了"
                    @load="onLoad">
                <van-cell v-for="item in list" @click="detail(item)" :key="item.id" :title="item.title" is-link :value="item.updateDate | moment('MM-DD HH:mm')"/>

            </van-list>
        </van-pull-refresh>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import api from '@/api'
    export default {
        name: 'Home',
        data (){
            return{
                type:0,
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
            detail(item){
                this.setNoticeDetail(item)
                this.$router.push('/noticeDetail')
            },
            onLoad() {
                if (this.refreshing) {
                    this.list = [];
                    this.pageIndex = 1;
                    this.refreshing = false;
                }
                api.getNoticeList({
                    'page': this.pageIndex,
                    'type': this.$route.query.t || 0,
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

</style>
