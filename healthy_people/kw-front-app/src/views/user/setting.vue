<template>
    <div class="mod-auth-code">
        <van-nav-bar
                title="设置"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <van-cell-group>
            <van-cell title="手机号码" :value="userInfo.mobile" />
            <van-cell title="登录密码" value="修改"  is-link @click="$router.push('/password')" />
            <van-cell title="交易密码" value="修改"  is-link @click="$router.push('/payPassword')" />
            <van-cell title="谷歌验证码"  v-if="gAuth === 1" :value="userInfo.bindAuthCode | bindAuthCodeFilter" :is-link="userInfo.bindAuthCode===0" @click="userInfo.bindAuthCode===0?$router.push('/bindAuthCode'):''"/>
        </van-cell-group>
        <div class="bottom-box">
            <van-button round plain type="danger" size="large" @click="logout()">退出</van-button>
        </div>


    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import {clearLoginInfo} from '@/utils'
    export default {
        name: 'Setting',
        data (){
            return{
                realName:'',
                idCard:''
            };
        },
        filters: {
            bindAuthCodeFilter :function(value){
                switch (value) {
                    case 0:
                        return '去绑定'
                    case 1:
                        return '已绑定'
                }
            }
        },
        computed: {
            ...mapState('user', ['userInfo','gAuth'])
        },
        methods: {
            ...mapActions('user',['getUserInfo', 'resetUserInfo']),
            logout(){
                clearLoginInfo()
                this.resetUserInfo();
                this.getUserInfo()
            }
        },
        created() {
            this.getUserInfo()
        }
    }
</script>

<style lang="scss" scoped>
    .mod-auth-code {
        .title{
            background-color: #f1f1f1;
            padding-left:20px;
            height: 80px;
            line-height: 80px;
        }

        .bottom-box{
            position:fixed;
            bottom:50px;

            width: 750px;

            display: flex;
            justify-content: center;
            align-items: center;

            button{
                width: 90%;
            }
        }


    }
</style>
