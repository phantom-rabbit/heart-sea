<template>
    <div class="mod-auth-code">
        <van-nav-bar
                title="绑定谷歌验证码"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <div v-if="userInfo.bindAuthCode === 1">
           <div style="text-align: center">您已经绑定过了</div>
        </div>
        <div v-else>
            <div style="text-align: center">
                <img :src="url"/>
                <div>{{sk}}</div>
            </div>
            <div>
                <van-form class="form" @submit="onSubmit">

                    <van-field
                            v-model="captcha"
                            center
                            clearable
                            label="短信验证码"
                            placeholder="请输入短信验证码"
                    >
                        <template #button>
                            <van-button native-type="button" style="width: 120px;height:40px;"  type="primary" @click="sendSms">{{getCodeButtonText}}</van-button>
                        </template>
                    </van-field>
                    <van-field
                            v-model="code"
                            name="谷歌验证码"
                            label="谷歌验证码"
                            placeholder="谷歌验证码"
                            :rules="[{ required: true, message: '请填写谷歌验证码' }]"
                    />
                    <div style="margin: 16px;">
                        <van-button round block type="primary" native-type="submit">
                            立即绑定
                        </van-button>
                    </div>
                </van-form>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    const ls = require('local-storage');
    import api from '@/api'
    export default {
        name: 'User',
        data (){
            return{
                captcha:'',
                code: '',
                url: this.$http.adornUrl('/api/qr') +"?token="+ls.get("token"),
                disableGetMobileCode: false,
                disableSubmitMobileCode: false,
                getCodeButtonText: '获取验证码',
            };
        },
        computed: {
            ...mapState('user', ['sk','userInfo'])
        },
        methods: {
            ...mapActions('user',['getUserInfo']),
            onSubmit(){
                api.bindAuthCode({
                    code: this.code,
                    captcha: this.captcha,
                }).then(res=>{
                    if(res.data.code === 0){
                        this.$toast.success(res.data.msg)
                    }else{
                        this.$toast.fail(res.data.msg)
                    }
                })
            },
            sendSms(){
                api.bindAuthCodeSms().then(res=>{
                    if(res.data.code === 0){
                        let that = this;
                        let i = 60;
                        that.intervalId = setInterval(function () {
                            i--;
                            if (i <= 0) {

                                that.disableGetMobileCode = false;
                                that.disableSubmitMobileCode = false;
                                that.getCodeButtonText = '获取验证码';

                                clearInterval(that.intervalId);
                            } else {

                                that.getCodeButtonText = i;
                                that.disableGetMobileCode = true;
                                that.disableSubmitMobileCode = false;

                            }
                        }, 1000);
                        this.$toast.success(res.data.msg)
                    }else{
                        this.$toast.fail(res.data.msg)
                    }
                })
            }
        },
        created() {
            this.getUserInfo()
        }
    }
</script>

<style lang="scss" scoped>
    .mod-user {
        position:fixed;
        background: url(http://epstatic21.zhiqiu666.com/static/v1/img/bg1.jpg);
        overflow: hidden;
        background-position: center center;
        background-size: 100% 100%;
        background-repeat: no-repeat;
        background-attachment: fixed;
        width: 750px;
        height: 1920px;

        .user-info-box{

            .level-info{
                display: flex;
                justify-content: flex-start;
                align-items: center;
                .l{
                    .logo{
                        background-image: url("~@/assets/img/common/logo.png");
                        overflow: hidden;
                        background-position: center center;
                        background-size: 100% 100%;
                        background-repeat: no-repeat;
                        width: 100px;
                        height: 100px;
                        display: block;
                    }
                }

                .r{
                    flex: 1;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }

            }
        }
    }
</style>
