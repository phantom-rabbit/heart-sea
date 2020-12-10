<template>
    <div class="mod-forget">
        <van-nav-bar
                title="修改交易密码"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <div>
            <van-form class="form" @submit="onSubmit">
                <!--<van-field v-if="userInfo.bindAuthCode == 1"-->
                        <!--v-model="code"-->
                        <!--name="谷歌验证码"-->
                        <!--label="谷歌验证码"-->
                        <!--placeholder="谷歌验证码"-->
                        <!--:rules="[{ required: true, message: '请填写谷歌验证码' }]"-->
                <!--/>-->

                <div v-if="type === 1">
                    <van-cell title="手机号码" :value="userInfo.mobile"/>
                    <van-field
                            v-model="captcha"
                            center
                            clearable
                            label="短信验证码"
                            placeholder="请输入短信验证码"
                    >
                        <template #button >
                            <van-button v-if="type === 1" native-type="button" style="width: 120px;height:40px;" type="primary"
                                        @click="sendSms">{{getCodeButtonText}}
                            </van-button>
                        </template>
                    </van-field>
                </div>

                <div v-if="type === 0">
                    <van-field
                            v-model="oldPassword"
                            type="password"
                            name="原密码"
                            label="原密码"
                            placeholder="原密码"
                            :rules="[{ pattern, message: '请填写正确的原密码' }]"
                    />
                </div>

                <van-field
                        v-model="password"
                        type="password"
                        name="新密码"
                        label="新密码"
                        placeholder="新密码"
                        :rules="[{ pattern, message: '请输入6位数字新密码' }]"
                />
                <div style="margin: 16px;">
                    <van-button round block type="primary" native-type="submit">
                        重置密码
                    </van-button>
                    <div style="display: flex;justify-content: flex-end;align-items: center;margin-top: 5px;">
                        <div v-if="type === 0" @click="type = 1">忘记密码？</div>
                        <div v-if="type === 1" @click="type = 0">修改密码？</div>
                    </div>
                </div>
            </van-form>
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
                pattern: /\d{6}/,
                type:0,
                code:'',
                oldPassword:'',
                password:'',
                auth:false,

                captcha: '',
                disableGetMobileCode: false,
                disableSubmitMobileCode: false,
                getCodeButtonText: '获取验证码',
            };
        },
        created(){
            this.getUserInfo()
        },
        computed: {
            ...mapState('user', ['userInfo','account','aList'])
        },
        methods: {
            ...mapActions('user',['getUserInfo']),
            sendSms() {
                api.sendTradeSms().then(res => {
                    if (res.data.code === 0) {
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
                    } else {
                        this.$toast.fail(res.data.msg)
                    }
                })
            },
            onSubmit(){
                api.alertPayPassword({
                    captcha: this.captcha,
                    password: this.password,
                    oldPassword: this.oldPassword,
                    type: this.type
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
    .mod-forget {
    }
</style>
