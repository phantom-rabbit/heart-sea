<template>
    <div class="mod-forget">
        <van-nav-bar
                title="修改登录密码"
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
                <van-field
                        v-model="oldPassword"
                        type="password"
                        name="原密码"
                        label="原密码"
                        placeholder="原密码"
                        :rules="[{ required: true, message: '请填写原密码' }]"
                />
                <van-field
                        v-model="password"
                        type="password"
                        name="新密码"
                        label="新密码"
                        placeholder="新密码"
                        :rules="[{ required: true, message: '请填写新密码' }]"
                />
                <div style="margin: 16px;">
                    <van-button round block type="primary" native-type="submit">
                        重置密码
                    </van-button>
                </div>
            </van-form>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import api from '@/api'
    import {clearLoginInfo} from '@/utils'
    export default {
        name: 'User',
        data (){
            return{
                code:'',
                password:'',
                uuid:'',
                auth:false
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
            onSubmit(){
                api.alterPassword({
                    code: this.code,
                    password: this.password,
                    oldPassword: this.oldPassword
                }).then(res=>{
                    if(res.data.code === 0){
                        this.$toast.success(res.data.msg)
                        clearLoginInfo();
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
