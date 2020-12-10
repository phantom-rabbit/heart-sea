<template>
    <div class="transaction">
        <div class="bg">
            <img src="~@/assets/img/common/bg_0.jpg" width="100%" height="100%"/>
        </div>
        <van-nav-bar class="nav-bar"
                     left-text="返回"
                     left-arrow
                     style="background-color: transparent"
                     @click-left="$router.go(-1)"
        />
        <div class="transfertab">
            <div  class="tab"><i><img  src="~@/assets/img/usertransaction/tab_1.png"  @click="search(1)" alt=""></i></div>
            <div  class="tab"><i><img src="~@/assets/img/usertransaction/tab_2.png" @click="search(2)" alt=""></i></div>
            <div  class="tab"><i><img src="~@/assets/img/usertransaction/tab_3.png" @click="search(3)" alt=""></i></div>
        </div>
        <div class="box">
            <div class="usertanslist" v-if="type==1||type==2">
                <van-list
                        class="user-list"
                        v-model="loading"
                        :finished="finished"
                        finished-text="没有更多了"
                        @load="onLoad" >
                    <div class="usertransitem"  v-for="item in list" :key="item.id">
                        <div :class="'pay_type'+item.payType">
                        </div>
                        <div class="pay_content" @click="gocontent(item)" >
                            <div class="pay_left">
                                <div>交易号: </div>
                                <div>数量: </div>
                                <div>总价: </div>
                                <div>订单状态: </div>
                            </div>
                            <div class="pay_right">
                                <div>{{item.id}} </div>
                                <div>{{item.amount}} </div>
                                <div>{{item.price}}{{item.payTye|payTypeFilter}} </div>
                                <div>{{item.status|statusFilter}}  </div>
                            </div>
                        </div>
                    </div>
                </van-list>
            </div>
            <div class="usertanslist_purpose" v-if="type==3">
                <van-list
                        class="user-list"
                        v-model="loadingPurpose"
                        :finished="finishedPurpose"
                        finished-text="没有更多了"
                        @load="onLoadData" >
                    <div class="usertransitem"  v-for="item in listPurpose" :key="item.id">
                        <div :class="'pay_type'+item.payType">
                        </div>
                        <div class="pay_content" >
                            <div class="pay_left">

                                <div>数量: </div>
                                <div>已购: </div>
                                <div>总价: </div>
                                <div>订单状态: </div>
                            </div>
                            <div class="pay_right">

                                <div>{{item.total}} </div>
                                <div>{{item.hasBuy}} </div>
                                <div>{{item.price}} </div>
                                <div>{{item.status|pStatusFilter}}  </div>
                                <div class="btn" v-if="item.status==0" @click="cancle(item.id)">完成订单 </div>
                            </div>
                        </div>
                    </div>
                </van-list>
            </div>
        </div>

        <tabs class="tabs" />
    </div>
</template>

<script>
    // @ is an alias to /src
    import tabs from '@/components/tabs.vue'
    import api from '@/api'
    export default {
        name: 'usertransaction',
        data() {
            return {
                list: [],
                loading: false,
                finished: false,
                page: 1,
                type:1,
                userId:0,
                listPurpose: [],
                loadingPurpose: false,
                finishedPurpose: false,
                pagePurpose: 1



            }
        },
        filters:{
            pStatusFilter(value){
                if(value==0){
                    return "求购中"
                }
                if(value==1){
                    return "已完成"
                }
            },

            //  状态 (0待上传交易凭证1已上传 2完成 -1交易关闭)
            statusFilter(value){
                if(value==0){
                    return "待上传交易凭证"
                }
                if(value==1){
                    return "已上传交易凭证"
                }
                if(value==-1){
                    return "交易关闭"
                }
                else{
                    return "交易完成"
                }
            },
            payTypeFilter(value){
                if(value==2){
                    return "USDT"
                }else{
                    return "CNY"
                }
            }
        },
        components: {
            tabs
        },methods:{
            cancle(id){




                this.$dialog.confirm({
                    title: '标题',
                    message: '确认完成求购吗'
                }).then(() => {
                    api.cancle({
                        purposeId:id

                    }).then(res=>{
                        if(res.data.code==0){
                            this.$toast.success(res.data.msg)
                            self.show=false;
                            this.refresh();
                            self.getBuyList();

                        }

                        console.log(res.data);
                    })
                    // on confirm
                }).catch(() => {
                    // on cancel
                });
            },
            gocontent(item){

                console.log(this.userId)
                if(item.sellerId==this.userId){
                    console.log("seller")

                    this.$router.push({name:'transeller',
                        query:{
                            id:item.id,
                            userId:this.userId
                        }
                    })
                }
                if(item.buyerId==this.userId){
                    console.log("buyer")
                    this.$router.push({name:'transbuyer',
                        query:{
                            id:item.id,
                            userId:this.userId
                        }
                    })
                }



            },

            search(id) {
                this.type = id;
                this.refresh();

                //  this.onLoad();


            },
            refresh() {
                this.list = [];
                this.loading = false;
                this.finished = false;
                this.page = 1;
                this.listPurpose = [];
                this.loadingPurpose = false;
                this.finishedPurpose = false;
                this.pagePurpose = 1;
            },

            onLoadData(){

                let self = this;
                api.getBuyList({
                    pagePurpose: this.page,
                    limit: 10,
                    self:1
                }).then(res=>{
                    if(res.data.code === 0){
                        this.userId = res.data.userId;
                        let totalPages = res.data.page.totalPage;
                        if (self.pagePurpose >= totalPages) {
                            self.finishedPurpose = true;
                        } else {
                            self.pagePurpose = self.pagePurpose + 1;
                        }
                        if (res.data.page.list instanceof Array) {
                            res.data.page.list.forEach(item => {
                                self.listPurpose.push(item);
                            })
                        }

                    }else{
                        self.finished = true;
                        this.$toast.fail(res.data.msg)
                    }
                    this.loading = false;
                })
            },
            onLoad() {

                let self = this;
                console.log("onLoad function strike!");
                this.loading = true;
                api.getTransList({
                    page: this.page,
                    limit: 10,
                    type:this.type
                }).then(res=>{
                    if(res.data.code === 0){
                        this.userId = res.data.userId;
                        let totalPages = res.data.page.totalPage;
                        if (self.page >= totalPages) {
                            self.finished = true;
                        } else {
                            self.page = self.page + 1;
                        }
                        if (res.data.page.list instanceof Array) {
                            res.data.page.list.forEach(item => {
                                self.list.push(item);
                            })
                        }

                    }else{
                        self.finished = true;
                        this.$toast.fail(res.data.msg)
                    }
                    this.loading = false;
                })
            }

        }

    }

</script>
<style>
    .transaction .user-list{
        height: 100%;
        padding-bottom:200px;
    }

    .transaction .van-hairline--bottom:after{
        border-bottom-width:0;
    }
</style>
<style lang="scss" scoped>
    .transaction {
        .bg {
            position: fixed;
            top:0;
            left:0;
            z-index: -1;
            width: 100%;
            height: 100%;
        }
        color:#999;
        display: flex;
        flex-flow: column;
        align-items: center;

        .box{
            position:fixed;
            top:180px;
            width: 100%;
            height: 80%;
            overflow-y: scroll;
            touch-action: pan-y;
            -webkit-overflow-scrolling: touch;
        }

        .nav-bar{
            position: fixed;
            width: 750px;
        }
        .transfertab{
            position:fixed;
            top:87px;
            width: 750px;
            height: 87px;
            align-items: center;
            justify-content: center;
            display: flex;
            .tab img{
                height: 82px;
            }
        }

        .usertanslist_purpose{
            width: 750px;
            display: flex;
            flex-flow: column;
            align-items: center;
            .usertransitem{
                background-image: url("~@/assets/img/usertransaction/bg_item.png");
                background-position: center center;
                background-size: 100% 100%;
                background-repeat: no-repeat;
                width: 717px;
                height: 226px;
                display: flex;
                justify-content: space-around;
                align-items: center;
                .pay_content{
                    display: flex;
                    width: 100%;
                    justify-content: space-between;
                    .pay_left{
                        div {
                            line-height: 40px;
                        }

                        margin-left: 27px;
                        display: flex;
                        flex-direction: column;
                    }
                    .pay_right{
                        text-align: right;
                        div {
                            line-height: 40px;
                        }

                        display: flex;
                        margin-right: 37px;
                        flex-direction: column;
                        .btn{
                            background-size: 100% 100%;
                            width: 130px;
                            height: 50px;
                            color: white;
                            background-image: url("~@/assets/img/usertransaction/btn.png");
                            text-align: center;
                            line-height: 50px;
                        }
                    }
                }

                .pay_type1{

                    margin-left: 27px;
                    width: 172px;
                    height: 132px;
                    background-size: 100% 100%;
                    background-image: url("~@/assets/img/usertransaction/alipay.png");
                }
                .pay_type2{
                    margin-left: 27px;
                    width: 172px;
                    height: 132px;
                    background-size: 100% 100%;
                    background-image: url("~@/assets/img/usertransaction/usdt.png");
                }
                .pay_type3{
                    margin-left: 27px;
                    width: 172px;
                    background-size: 100% 100%;
                    height: 132px;
                    background-image: url("~@/assets/img/usertransaction/bank.png");
                }
            }
        }

        .usertanslist{
            width: 750px;
            display: flex;
            justify-content: center;
            .usertransitem{
                background-image: url("~@/assets/img/usertransaction/bg_item.png");
                background-position: center center;
                background-size: 100% 100%;
                background-repeat: no-repeat;
                width: 717px;
                height: 211px;
                display: flex;
                justify-content: space-around;
                align-items: center;
                .pay_content{
                    display: flex;
                    width: 100%;
                    justify-content: space-between;
                    .pay_left{
                        margin-left: 27px;
                        display: flex;
                        flex-direction: column;
                        div {
                            line-height: 40px;
                        }

                    }
                    .pay_right{
                        text-align: right;
                        display: flex;
                        margin-right: 37px;
                        flex-direction: column;
                        div {
                            line-height: 40px;
                        }

                    }

                }

                .pay_type1{

                    margin-left: 27px;
                    width: 172px;
                    height: 132px;
                    background-size: 100% 100%;
                    background-image: url("~@/assets/img/usertransaction/alipay.png");
                }
                .pay_type2{
                    margin-left: 27px;
                    width: 172px;
                    height: 132px;
                    background-size: 100% 100%;
                    background-image: url("~@/assets/img/usertransaction/usdt.png");
                }
                .pay_type3{
                    margin-left: 27px;
                    width: 172px;
                    background-size: 100% 100%;
                    height: 132px;
                    background-image: url("~@/assets/img/usertransaction/bank.png");
                }
            }
        }

    }
</style>
