<template>
    <div class="mod-active-code">
        <van-nav-bar class="nav-bar"
                     title="申请我的战队"
                     left-text="返回"
                     left-arrow
                     @click-left="$router.go(-1)"
        />
        <div>
            <div>
                <van-form class="form" @submit="onSubmit">
                    <van-field
                               v-model="name"
                               name="战队名称"
                               label="战队名称"
                               placeholder="战队名称"
                               :rules="[{ required: true, message: '请填写战队名称' }]"
                    />
                    <van-field
                            v-model="wechat"
                            name="战队微信"
                            label="战队微信"
                            placeholder="战队微信"
                            :rules="[{ required: true, message: '请填写战队微信' }]"
                    />
                    <van-field
                            v-model="slogan"
                            name="战队口号"
                            label="战队口号"
                            placeholder="战队口号"
                            :rules="[{ required: true, message: '请填写战队口号' }]"
                    />
                    <div>
                        <van-field v-if="status === 0" name="状态" label="状态" value="审核中"/>
                        <van-field v-if="status === 1" name="状态" label="状态" value="通过"/>
                        <van-field v-if="status === -1" name="状态" label="状态" value="审核未通过"/>
                    </div>
                    <div style="margin: 16px;" v-if="!id">
                        <van-button round block type="primary" native-type="submit">
                            提 交
                        </van-button>
                    </div>
                </van-form>
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
                id:0,
                name:'',
                wechat:'',
                slogan:'',
                status:''
            };
        },
        created(){
            this.getUserInfo()
            this.getApplyTeamInfo()
        },
        watch:{
            myTeam(n){
                if(n){
                    this.name =  n.team
                    this.wechat = n.teamWechat
                    this.slogan = n.slogan
                    this.id = n.id
                    this.status = n.status
                }
            }
        },
        computed: {
            ...mapState('user', ['userInfo','account','team','myTeam'])
        },
        methods: {
            ...mapActions('user',['getUserInfo','getTeamInfo','getApplyTeamInfo']),
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
