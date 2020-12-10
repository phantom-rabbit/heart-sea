<template>
    <div class="mod-auth-code">
        <van-nav-bar
                title="设置"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />
        <van-form class="form" @submit="onSubmit">
            <div class="van-cell van-cell--center van-field">
                <div class="van-cell__title van-field__label"><span>选择用户</span></div>
                <div class="van-cell__value van-field__value">
                    <div class="van-field__body">
                        <van-dropdown-menu>
                            <van-dropdown-item v-model="user"   :options="userList" />
                        </van-dropdown-menu>
                    </div></div>
            </div>

            <van-field
                    v-model="num"
                    center
                    clearable
                    label="数量"
                    placeholder="请填写数量'"
                    :rules="[{ required: true, message: '请填写数量' }]"
            >
            </van-field>

            <div style="margin: 16px;">
                <van-button round block type="primary" native-type="submit">
                    立即发放
                </van-button>
            </div>
        </van-form>

    </div>
</template>

<script>
    import api from '@/api'
    import {mapActions, mapState} from 'vuex'
    export default {
        name: "AwardCode",

        data (){
            return{
                num:0,
                userList:[],
                user:'请选择'
            };
        },
        computed: {
            ...mapState('user', ['account']),

        },
        methods: {
            ...mapActions('user',['getUserInfo']),
            onSubmit(){
                api.awardInvitecode({
                    awardUserId:this.user,
                    num:this.num
                }).then(res=>{
                    if(res.data.code === 0){
                    this.$toast.success(res.data.msg);
                    this.$router.go(-1);
                }else{
                    this.$toast.fail(res.data.msg);
                }
            });


            }
            ,
            getDirect(){
                api.getDirects({

                }).then(res=>{
                    if(res.data.code === 0){
                        this.userList = res.data.directs;

                }else{
                    this.$toast.fail(res.data.msg);
                }
            });
            }
        },

        created(){
            this.getUserInfo();
            this.getDirect();

        }

    }
</script>

<style scoped>

</style>