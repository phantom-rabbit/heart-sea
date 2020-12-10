<template>
    <div class="mod-auth-code">
        <van-nav-bar
                title="设置"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <div class="title">支付设置</div>
        <van-cell-group>
            <van-cell title="支付宝账号" @click="goPayment('aliPay')"  :value="account.alipayStatus|settingFilter" />
            <van-cell title="USDT地址"  @click="goPayment('usdt') " :value="account.usdtStatus|settingFilter"/>
            <van-cell title="银行卡" @click="goPayment('bank') " :value="account.bankStatus|settingFilter" />
            <!--<van-cell title="谷歌验证码" :value="userInfo.bindAuthCode | bindAuthCodeFilter" :is-link="userInfo.bindAuthCode===0" @click="userInfo.bindAuthCode===0?$router.push('/bindAuthCode'):''"/>-->
        </van-cell-group>

    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    export default {
        name: 'Payments',
        data (){
            return{

            };
        },
        filters: {
            settingFilter :function(value){
                switch (value) {

                    case 0:
                        return '未设置'
                    case 1:
                        return '已设置'

                }
            }
        },
        computed: {
            ...mapState('user', ['account']),
        },
        methods: {
            ...mapActions('user',['getUserInfo']),
              goPayment(id){
                this.$router.push({name:'Payment',
                        query:{
                            id:id
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
    .mod-auth-code {
        .title{
            background-color: #f1f1f1;
            padding-left:20px;
            height: 80px;
            line-height: 80px;
        }


    }
</style>
