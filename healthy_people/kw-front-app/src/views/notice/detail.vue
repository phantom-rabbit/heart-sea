<template>
    <div class="mod-notice">
        <van-nav-bar
                :title="noticeDetail.title"
                left-text="返回"
                left-arrow
                @click-left="$router.go(-1)"
        />


        <div class="content" v-html="noticeDetail.content"></div>

    </div>
</template>

<script>
    import { mapState} from 'vuex'
    export default {
        name: 'Home',
        computed: {
            ...mapState('common', ['noticeDetail'])
        },
        mounted(){
            setTimeout(()=>{
                if(!this.noticeDetail || !this.noticeDetail.id){
                    this.$router.push('/')
                }
            },0)
        }
    }
</script>
<style>
    .mod-notice .content .vedio{
        width: 700px;
        height: 400px;
    }
</style>
<style lang="scss" scoped>

   .mod-notice{
       .content{
           padding-left:20px;
       }
   }
</style>
