<template>
    <div class="mod-auth-code">
        <van-nav-bar
                title="设置"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />




        <div v-if="type=='usdt'">
            <div v-if="account.usdtStatus === 1">
                <van-cell-group>
                    <van-cell title="usdtToken" :value="account.usdtToken"/>
                </van-cell-group>
            </div>
            <div v-else>
            <van-form class="form" @submit="onSubmit">
                <van-field
                        v-model="usdtToken"
                        center
                        clearable
                        label="usdt"
                        placeholder="请填写您的usdt的token地址"
                        :rules="[{ required: true, message: '请填写您的usdt的token地址' }]"
                >
                </van-field>
                <div style="margin: 16px;">
                    <van-button round block type="primary" native-type="submit">
                        立即绑定
                    </van-button>
                </div>
            </van-form>
            </div>
        </div>

        <div v-if="type=='aliPay'">
            <div v-if="account.alipayStatus === 1">
                <van-cell-group>
                    <van-cell title="支付宝" :value="account.alipayNumber"/>
                </van-cell-group>
            </div>
            <div v-else>

        <van-form class="form" @submit="onSubmit">
            <van-field
                    v-model="aliPayNumber"
                    center
                    clearable
                    label="支付宝"
                    placeholder="请填写您的支付宝账号'"
                    :rules="[{ required: true, message: '请填写您的支付宝账号' }]"
            >
            </van-field>

            <div style="margin: 16px;">
                <van-button round block type="primary" native-type="submit">
                    立即绑定
                </van-button>
            </div>
        </van-form>

            </div>
        </div>
        <div v-if="account.bankStatus === 1&&type=='bank'">
            <van-cell-group>
                <van-cell title="银行" :value="account.bank"/>
                <van-cell title="分行" :value="account.branch"/>
                <van-cell title="银行卡号" :value="account.bankCardno"/>
            </van-cell-group>
        </div>
        <div v-else>

        <div v-if="type=='bank'">
        <van-form class="form" @submit="onSubmit">
            <div class="van-cell van-cell--center van-field">
                <div class="van-cell__title van-field__label"><span>银行</span></div>
                <div class="van-cell__value van-field__value">
                    <div class="van-field__body">
                        <van-dropdown-menu>

                            <van-dropdown-item v-model="bank"   :options="bankList" />
                        </van-dropdown-menu>
                    </div></div>
            </div>

            <van-field

                    v-model="branch"
                    center
                    clearable
                    label="分行"
                    placeholder="请填写分行'"
                    :rules="[{ required: true, message: '请填写分行' }]"
            >
            </van-field>

            <van-field
                    v-model="bankCardNo"
                    center
                    clearable
                    label="卡号"
                    placeholder="请填写卡号'"
                    :rules="[{ required: true, message: '请填写卡号' }]"
            >
            </van-field>

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
    import api from '@/api'

    export default {
        name: 'Payment',
        data (){
            return{
                bank: '中国工商银行',
                option1: [
                    // { text: '中国工商银行', value: '中国工商银行' },
                    // { text: '中国农业银行', value: '中国农业银行' },
                    // { text: '中国银行', value: '中国银行' },
                    // { text: '中国建设银行', value: '中国建设银行' },
                    // { text: '交通银行', value: '交通银行' }
                ],
                type:'aliPay',
                aliPayNumber:'',
                bankCardNo:'',
                usdtToken:'',
                branch:'',
            };
        },

        computed: {
            ...mapState('user', ['account']),
            ...mapState('common', ['bankList'])
        },
        methods: {
            ...mapActions('user',['getUserInfo']),
            ...mapActions('common',['getBanks']),
            onSubmit(){
                this.$dialog.confirm({
                    title: '您确定提交吗',
                    message: '请仔细确认您的信息'
                }).then(() => {
                    api.paymentSave({
                    type:this.type,
                    aliPayNumber:this.aliPayNumber,
                    usdtToken:this.usdtToken,
                    bank:this.bank,
                    branch:this.branch,
                    cardNo:this.bankCardNo
                    }).then(res=>{
                        if(res.data.code === 0){
                            this.$toast.success(res.data.msg)
                            this.getUserInfo()
                        }else{
                            this.$toast.fail(res.data.msg)
                        }
                    })
                }).catch(() => {

                    // on cancel
                });
            }
        },
        created(){
            this.type = this.$route.query.id;
            this.getUserInfo();
            this.getBanks();

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

                        overflow: hidden;
                        background-image: url("~@/assets/img/common/logo.png");
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
