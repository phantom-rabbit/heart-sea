<template>
    <div class="mod-active-code">
        <van-nav-bar class="nav-bar"
                title="我的邀请码"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <div>
            <div class="header">
                <div class="header-l">
                    <span class="num">{{activeCodeCt}}</span>
                    <div class="desc">可领取邀请码数量</div>
                </div>
                <div class="header-r" v-if="activeCodeCt!==0">
                    <van-button color="#ff5a60" plain size="small" @click="getActiveCode()" class="btn-submit" round  >领取</van-button>
                </div>
                <div class="header-r" >
                    <van-button color="#ff5a60" plain size="small" @click="$router.push('/awardCode')" class="btn-submit" round  >发放</van-button>
                </div>
            </div>

            <van-list
                    v-model="loading"
                    :finished="finished"
                    finished-text="没有更多了"
                    @load="onLoad"
            >
                <div class="item" v-for="item in activeCodeList" :key="item.id" >
                    <div class="item-a">
                        <div>邀请码：{{item.activationCode}}</div>
                        <div>状态：
                            <span v-if="item.status === -1">已失效</span>
                            <span v-if="item.status === 1">已使用</span>
                            <span v-else>可用</span>
                        </div>
                    </div>
                    <div class="item-a">
                        <div>获取时间：{{item.createTime | moment("MM-DD HH:mm")}}</div>
                        <div><van-button round @click="doCopy(item.activationCode)" v-if="item.status === 0" style="width:80px;height:25px;font-size:12px;" color="linear-gradient(to right, #4bb0ff, #6149f6)">分享好友</van-button></div>
                    </div>
                </div>
            </van-list>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    // import api from '@/api'
    // import {clearLoginInfo} from '@/utils'
    export default {
        name: 'User',
        data (){
            return{
                loading: false,
                finished: false
            };
        },
        created(){
            this.getActiveCodeList()
        },
        computed: {
            ...mapState('user', ['activeCodeList','activeCodeCt'])
        },
        methods: {
            ...mapActions('user',['getActiveCodeList','getActiveCode']),
            onLoad() {
                this.loading = true
                this.getActiveCodeList()
                this.finished = true
                this.loading = false
            },
            doCopy: function (code) {
                this.$router.push( {name:'inviter',  query:{code: code}})

                // let url = window.location.href;
                // url=url.replace(this.$route.path,"");
                // console.log(url)
                // let that = this
                // this.$copyText(url+'/register?code='+code).then(function () {
                //     that.$toast('分享链接，复制成功')
                // }, function () {
                //     that.$toast('复制失败')
                // })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .mod-active-code {

        .header{
            display: flex;
            justify-content: space-between;
            align-items: center;
            height: 300px;
            background: linear-gradient(#ff5a60, #ff827b);
            .header-l{
                color: #373737;
                height: 200px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                flex: 1;
                .num{
                    font-size: 80px;
                    color: #fff;
                }

                .desc{
                    font-size:30px;
                    color:#fff;
                }
            }
            .header-r{
                width: 200px;
                .btn-submit{
                    width: 120px;
                }
            }
        }

        .item{
            border-top:1px solid #f2f2f2;
            padding:20px;
            .item-a{
                line-height: 60px;
                color: #646464;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
        }
    }
</style>
