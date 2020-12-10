<template>
    <div class="mod-register">
        <div class="logo-box">
            <div class="logo"/>
        </div>
        <van-form class="form" @submit="onSubmit">
            <van-field
                    v-model="mobile"
                    type="number"
                    name="手机号码"
                    label="手机号码 +86"
                    placeholder="手机号码"
                    :rules="[{ required: true, message: '请填写手机号码' }]"
            />
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
                    v-model="password"
                    type="password"
                    name="密码"
                    label="密码"
                    placeholder="密码"
                    :rules="[{ required: true, message: '请填写密码' }]"
            />

            <van-field
                    v-model="payPassword"
                    type="password"
                    name="支付密码"
                    label="支付密码"
                    placeholder="支付密码"
                    :rules="[{ required: true, message: '请填写支付密码' }]"
            />

            <van-field
                    v-model="code"
                    name="推荐码"
                    label="推荐码"
                    placeholder="推荐码"
                    :rules="[{ required: true, message: '请填写推荐码' }]"
            />
            <div style="margin: 16px;">
                <van-button round block type="primary" native-type="submit">
                    立即注册
                </van-button>
                <div class="actions-box">
                    <div class="forget" @click="$router.push('/forget')">忘记密码?</div>
                    <div class="login" @click="$router.push('login')">立即登录</div>
                </div>

            </div>
        </van-form>
    </div>
</template>

<script>
    import { isMobile, setToken} from '@/utils'
    import api from '@/api'
    export default {
        name: 'Register',
        data() {
            return {
                captchaPath:'',
                mobile:'',
                password:'',
                payPassword:'',
                // uuid:'',
                captcha:'',
                code: '',

                disableGetMobileCode: false,
                disableSubmitMobileCode: false,
                getCodeButtonText: '获取验证码',
                intervalId: false
            };
        },
        created(){
            if(this.$route.query.code){
                this.code = this.$route.query.code
            }
        },
        methods:{
            onSubmit(){
                api.register({
                    mobile: this.mobile,
                    code: this.code,
                    captcha: this.captcha,
                    password: this.password,
                    payPassword: this.payPassword,
                }).then(res=>{
                    if(res.data.code === 0){
                        setToken(res.data.token)
                        this.$toast.success(res.data.msg)
                        this.$router.push("/")
                    }else{
                        this.$toast.fail(res.data.msg)
                    }
                })
            },
            // getCaptcha(){
            //     this.uuid = getUUID()
            //     console.log(this.uuid)
            //     this.captchaPath = this.$http.adornUrl(`/api/captcha?uuid=${this.uuid}`)
            // },
            beforeSend() {
                if(!isMobile(this.mobile)){
                    this.$toast.fail("请输入有效的手机号码")
                    return false;
                }

                if(!this.code){
                    this.$toast.fail("请输入推荐码")
                    return false;
                }
                return true;
            },
            sendSms(){
                if(!this.beforeSend()) return;
                api.sendRegisterSms({
                    mobile: this.mobile,
                    code: this.code
                }).then(res=>{
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
        }
    }
</script>

<style lang="scss" scoped>
    .mod-register{
        .logo-box{
            margin-top: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 200px;
            .logo{
                background-image: url("~@/assets/img/common/logo.png");
                overflow: hidden;
                background-position: center center;
                background-size: 100% 100%;
                background-repeat: no-repeat;
                width: 200px;
                height: 200px;
                display: block;
            }
        }

        .form {
            margin-top: 150px;
            .actions-box{
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top:20px;
                .forget{
                    padding-left:20px;
                    font-size: 25px;
                    color:#888;
                }

                .login{
                    color: $color-link;
                    padding-right:20px;
                    font-size: 25px;
                }
            }
        }
    }
</style>
