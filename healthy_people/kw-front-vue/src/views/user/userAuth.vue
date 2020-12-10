<template>
    <div class="mod-auth-code">
        <van-nav-bar
                title="实名认证"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />

        <div v-if="account.realStatus === 1">
            <div>正在审核中...</div>
        </div>
        <div v-else-if="account.realStatus === 2 || account.realStatus === -1">
            <van-cell-group>
                <van-cell title="姓名" :value="account.realName"/>
                <van-cell title="身份证号码" :value="account.idCard" />
                <van-cell title="状态" :value="account.realStatus | statusFilter" />
            </van-cell-group>
        </div>
        <div v-else>
            <van-form class="form" @submit="onSubmit">
                <van-field
                        v-model="realName"
                        name="姓名"
                        label="姓名"
                        placeholder="请输入您的姓名"
                        :rules="[{ required: true, message: '请填写您的姓名' }]"
                />
                <van-field
                        v-model="idCard"
                        center
                        clearable
                        label="身份证号码"
                        placeholder="请输入您的身份证号码"
                        :rules="[{ required: true, message: '请填写您的身份证号码' }]"
                >
                </van-field>

                <div style="margin: 16px;">
                    <van-button round block type="primary" native-type="submit">
                        立即认证
                    </van-button>
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
                realName:'',
                idCard:''
            };
        },
        filters: {
            statusFilter :function(value){
                switch (value) {
                    case -1:
                        return '审核失败'
                    case 0:
                        return '待提交'
                    case 1:
                        return '待审核'
                    case 2:
                        return '已通过'
                }
            }
        },
        computed: {
            ...mapState('user', ['account'])
        },
        methods: {
            ...mapActions('user',['getUserInfo']),
            onSubmit(){
                this.$dialog.confirm({
                    title: '您确定提交吗',
                    message: '实名信息必须真实，否则不予通过'
                }).then(() => {
                    api.userAuth({
                        realName: this.realName,
                        idCard: this.idCard,
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
        created() {
            this.getUserInfo()
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
