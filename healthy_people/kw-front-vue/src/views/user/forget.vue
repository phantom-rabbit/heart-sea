<template>
    <div class="mod-forget">
        <van-nav-bar
                title="忘记密码"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <div>
            <van-form class="form" @submit="onSubmitStep1">
                <van-field
                        v-model="mobile"
                        type="number"
                        name="手机号码"
                        label="手机号码"
                        placeholder="手机号码"
                        :rules="[{ required: true, message: '请填写手机号码' }]"
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
                <van-field
                        v-model="code"
                        center
                        clearable
                        label="短信验证码"
                        placeholder="请输入短信验证码"
                >
                    <template #button>
                        <van-button native-type="button" style="width: 120px;height:40px;" type="primary"
                                    @click="sendSms">{{getCodeButtonText}}
                        </van-button>
                    </template>
                </van-field>
                <div style="margin: 16px;">
                    <van-button round block type="primary" native-type="submit">
                        下一步
                    </van-button>
                </div>
            </van-form>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import api from '@/api'
    import {getUUID, isMobile} from '@/utils'

    export default {
        name: 'User',
        data() {
            return {
                mobile: '',
                captcha: '',
                code: '',
                disableGetMobileCode: false,
                disableSubmitMobileCode: false,
                getCodeButtonText: '获取验证码',
                uuid: '',
                captchaPath: '',
            };
        },
        computed: {
            ...mapState('user', ['account', 'userInfo'])
        },
        created() {
            this.getCaptcha()
        },
        methods: {
            ...mapActions('user', ['getUserInfo']),
            onSubmitStep1() {
                api.forgetS1({
                    mobile: this.mobile,
                    code: this.code,
                    captcha: this.captcha,
                    uuid: this.uuid,
                }).then(res => {
                    if (res.data.code === 0) {
                        this.$toast.success(res.data.msg)
                        this.$router.push( {name:'forgetReset',  params:{id: res.data.uuid, auth: res.data.auth}})
                    } else {
                        this.$toast.fail(res.data.msg)
                    }
                })
            },
            getCaptcha() {
                this.uuid = getUUID()
                console.log(this.uuid)
                this.captchaPath = this.$http.adornUrl(`/api/captcha?uuid=${this.uuid}`)
            },
            beforeSend() {
                if (!isMobile(this.mobile)) {
                    this.$toast.fail("请输入有效的手机号码")
                    return false;
                }

                if (!this.captcha) {
                    this.$toast.fail("请输入图形验证码")
                    return false;
                }
                return true;
            },
            sendSms() {
                if (!this.beforeSend()) return;
                api.sendForgetSms({
                    mobile: this.mobile,
                    captcha: this.captcha,
                    uuid: this.uuid
                }).then(res => {
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
            }
        }
    }
</script>

<style lang="scss" scoped>
    .mod-forget {

    }
</style>
