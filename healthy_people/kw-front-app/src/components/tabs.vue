<template>
    <div class="tabs" >
        <div class="tabs-item" @click="tab(1)" :class="{active: tabIndex === 1}">
            <div class="tab tab_1"></div>
            <div class="txt">首页</div>
        </div>

        <div class="tabs-item" @click="tab(2)" :class="{active: tabIndex === 2}">
            <div class="tab tab_2"></div>
            <div class="txt">转换器</div>
        </div>

        <div class="tabs-item" @click="tab(3)" :class="{active: tabIndex === 3}">
            <div class="tab tab_3"></div>
            <div class="txt">交易</div>
        </div>

        <div class="tabs-item" @click="tab(4)" :class="{active: tabIndex === 4}">
            <div class="tab tab_4"></div>
            <div class="txt">我的</div>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    export default {
        name: 'Tabs',
        props: {
            msg: String
        },
        computed: {
            ...mapState('common', ['tabIndex'])
    },
    methods:{
    ...mapActions('common', ['selectTab']),
            tab (index) {
            if(this.tabIndex === index) return

            // this.selectTab(index)

            switch (index) {
                case 1: {
                    this.$router.push("/")
                    break;
                }
                case 2: {
                    this.$router.push("/transfer")
                    break;
                }
                case 3: {
                    this.$router.push("/transaction")
                    break;
                }
                case 4: {
                    this.$router.push('/user')
                    break;
                }
            }
        }
    },
    mounted(){
        this.$router.beforeEach((to, from, next) => { //全局钩子函数
            console.log(to)
        if (to.name === 'user') {
            this.selectTab(4)
        }
        next()
    });
    }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
    .tabs{
        position:fixed;
        bottom:0;
        left:0;
        z-index: 999999;
        background-position: bottom;
        background-size: contain;
        background-repeat: no-repeat;
        background-image: url("~@/assets/img/tabs/white_tab_bg.png");
        width: 750px;
        display: flex;
        justify-content: center;
        align-items: center;
        .tabs-item{
            color:#999;
            font-size:28px;
            font-weight: 600;
            flex: 1;
            display: flex;
            flex-flow: column;
            align-items: center;


            .tab{

                overflow: hidden;
                background-position: center center;
                background-size: 100% 100%;
                background-repeat: no-repeat;
                width: 105px;
                height: 105px;

                &.tab_1{
                    background-image: url("~@/assets/img/tabs/tab_1.png");
                }
                &.tab_2{
                    background-image: url("~@/assets/img/tabs/tab_2.png");
                }
                &.tab_3{
                    background-image: url("~@/assets/img/tabs/tab_3.png");
                }
                &.tab_4{
                    background-image: url("~@/assets/img/tabs/tab_4.png");
                }
            }

            &.active .tab{
                width: 110px;
                height: 110px;
            }
            &.active .txt{
                color: #6db3ff;
            }

        }
    }
</style>
