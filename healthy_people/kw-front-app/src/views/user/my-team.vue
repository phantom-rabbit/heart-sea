<template>
    <div class="mod-my-team">
        <van-nav-bar class="nav-bar"
                     title="我的战队"
                     left-text="返回"
                     left-arrow
                     @click-left="$router.go(-1)"
        />
        <div>
            <div class="team-info">
                <van-cell-group>
                    <van-cell title="战队名称" :value="team.team" />
                    <van-cell title="战队口号" :value="team.slogan"  />
                    <van-cell title="战队微信" :value="team.teamWechat"  />
                    <van-cell title="战队领导" :value="team.leaderLevelName"/>
                    <van-cell title="战队人数" :value="team.teamCount"/>
                    <van-cell title="总动力值" :value="team.zhyd"/>
                </van-cell-group>
                <div class="team-action" v-if="(!myTeam || !myTeam.id || team.status !== 1) && account.levelId >= 2">
                    <van-button size="large" plain round type="warning" @click="$router.push('/applyTeamCaptain')">我要申请战队</van-button>
                </div>
            </div>

            <div class="team-list">
                <div class="item" v-for="item in teamList" :key="item.id" @click="call('tel://'+item.mobile)">
                    <div class="item-l">
                        <img class="logo" src="../../assets/img/common/logo.png" />
                    </div>
                    <div class="item-r">
                        <div class="a">
                            <div class="m">{{item.levelName}}</div>
                            <div class="n">{{item.workingLtc}}台转换器工作中</div>
                        </div>
                        <div class="b">
                            <div class="x">动力值：{{item.hyd}}</div>
                            <div class="y">战友数：{{item.directUserCount}}</div>
                        </div>
                    </div>
                </div>
                <div style="background-color: #fff;width: 100%" v-if="!teamList || teamList.length === 0">
                    <!-- 通用错误 -->
                    <van-empty image="error" description="暂无战队" />
                </div>
            </div>
        </div>


    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import api from '@/api'
    export default {
        name: 'User',
        data (){
            return{
                name:'',
                wechat:'',
                slogan:''
            };
        },
        created(){
            this.getUserInfo()
            this.getApplyTeamInfo()
            this.getTeamInfo()
        },
        computed: {
            ...mapState('user', ['userInfo','account','myTeam','team','teamList'])
        },
        methods: {
            ...mapActions('user',['getUserInfo','getTeamInfo','getApplyTeamInfo']),
            call(url) {
                if (url) location.href = url
            },
            onSubmit(){
                api.applyTeamCaptain({
                    name: this.name,
                    wechat: this.wechat,
                    slogan: this.slogan
                }).then(res=>{
                    if(res.data.code === 0){
                        this.$toast.success(res.data.msg)
                        this.getUserInfo()
                    }else{
                        this.$toast.fail(res.data.msg)
                    }
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .mod-my-team {
        background-color: #f9f9f9;
        .team-info{
            overflow: hidden;
            border-radius: 20px;
            width: 730px;

            margin:5px auto;
        }

        .team-action{
            padding:20px;
            background-color: #fff;
        }

        .team-list{
            display: flex;
            flex-flow: column;
            align-items: center;
            border-radius: 10px;


            .item:first-child{
                border-top-right-radius: 10px;
                border-top-left-radius: 10px;
            }

            .item:last-child{
                border-bottom-right-radius: 10px;
                border-bottom-left-radius: 10px;
            }

            .item{
                height: 180px;
                background-color: #fff;
                width: 730px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #f5f5f5;
                .logo{
                    width: 80px;
                    height: 80px;
                }

                .item-l{
                    width: 120px;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }
                .item-r{
                    flex: 1;
                    display: flex;
                    justify-content: flex-start;
                    align-items: center;
                    line-height: 50px;
                    .a{
                        color: #999;
                        .m{
                            font-size:35px;
                            font-weight: 900;
                            color:#555;
                        }
                        .n{

                        }

                    }
                    .b{
                        margin-left:20px;
                        color: #999;
                        .x{}
                        .y{}
                    }
                }


            }
        }

    }
</style>
