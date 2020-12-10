<template>
    <div class="mod-forget">
        <van-nav-bar
                title="重置密码"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <div>
            <van-form class="form" @submit="onSubmitStep2">
                <!--<van-field v-if="auth"-->
                        <!--v-model="code"-->
                        <!--name="谷歌验证码"-->
                        <!--label="谷歌验证码"-->
                        <!--placeholder="谷歌验证码"-->
                        <!--:rules="[{ required: true, message: '请填写谷歌验证码' }]"-->
                <!--/>-->
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
    import {mapActions} from 'vuex'
    import api from '@/api'
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
            this.uuid = this.$route.params.id;
            this.auth = this.$route.params.auth;
        },
        methods: {
            ...mapActions('user',['getUserInfo']),
            onSubmitStep2(){
                api.forgetS2({
                    code: this.code,
                    password: this.password,
                    uuid: this.uuid,
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
