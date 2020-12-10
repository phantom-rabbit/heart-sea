<template>
    <div class="mod-auth-code">
        <van-nav-bar
                title="个人中心"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <van-cell-group>
            <van-cell title="邀请码" :value="account.power"/>
            <van-cell :title="$config.coin" :value="account.parentMoney"/>
            <van-cell title="MAC" :value="account.sonMoney"/>
            <van-cell title="冻结金额" :value="account.frezenParentMoney"/>
            <van-cell title="支付方式设置" is-link @click="$router.push('/payments')" />
        </van-cell-group>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'

    export default {
        name: 'Ucenter',
        data (){
            return{

            };
        },

        computed: {
            ...mapState('user', ['account']),

    },
    methods: {
    ...mapActions('user',['getUserInfo']),
    },
    created(){
        this.type = this.$route.query.id;
        this.getUserInfo();

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
