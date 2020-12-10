<template>
    <div class="mod-register">
        <div class="logo-box">
            <div class="logo"/>
        </div>
        <van-form class="form" @submit="onSubmit">
            <van-field
                    v-model="mobile"
                    name="手机号码"
                    label="手机号码"
                    placeholder="手机号码"
                    :rules="[{ required: true, message: '请填写手机号码' }]"
            />

            <van-field
                    v-model="password"
                    type="password"
                    name="密码"
                    label="密码"
                    placeholder="密码"
                    :rules="[{ required: true, message: '请填写密码' }]"
            />

            <van-field
                    v-model="captcha"
                    center
                    clearable
                    label="图形验证码"
                    placeholder="请输入图形验证码"
                    :rules="[{ required: true, message: '请填写图形验证码' }]"
            >
                <template #button>
                    <div class="">
                        <img :src="captchaPath" @click="getCaptcha()" alt="" width="120" height="45">
                    </div>
                </template>
            </van-field>
            <div style="margin: 16px;">
                <van-button round block type="primary" native-type="submit">
                    立即登录
                </van-button>
                <div class="actions-box">
                    <div class="forget" @click="$router.push('/forget')">忘记密码?</div>
                    <div class="register" @click="$router.push('register')">立即注册</div>


                </div>
                <div class="actions-box-download" >
                    <div  class="forget"></div>
                    <a  href="http://d.7short.com/njfy">下载APP</a>
                </div>
            </div>
        </van-form>
    </div>
</template>

<script>
    import { getUUID, setToken } from '@/utils'
    import api from '@/api'
    export default {
        name: 'Register',
        data() {
            return {
                captchaPath:'',
                mobile:'',
                password:'',
                uuid:'',
                captcha:''
            };
        },
        created(){
            this.getCaptcha()
        },
        methods:{
            onSubmit(){
                api.login({
                    uuid: this.uuid,
                    mobile: this.mobile,
                    captcha: this.captcha,
                    password: this.password
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
            getCaptcha(){
                this.uuid = getUUID()
                console.log(this.uuid)
                this.captchaPath = this.$http.adornUrl(`/api/captcha?uuid=${this.uuid}`)
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
            .actions-box-download{
                display: flex;
              //  justify-content: center;
                align-items: center;

                margin-top:20px;
               flex-direction: row-reverse;
                a{
                    color: #4c8fff;

                    padding-left:20px;
                    font-size: 25px;

                }
                .forget{
                   visibility: hidden;
                    padding-left:20px;
                    font-size: 25px;
                    color:#888;
                }
            }

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

                .register{
                    color: $color-link;
                    font-size: 25px;
                    padding-right:20px;
                }
            }
        }
    }
</style>
