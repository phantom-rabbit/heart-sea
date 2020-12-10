<template>
    <div class="transaction">
        <van-search v-model="value" placeholder="请输入搜索UID" @search="onSearch" />
        <div class="top_notice">
            <div class="d_left">
                <div class="d1">平台参考价</div>
                <div class="d2">¥{{proposedPrice}}</div>
                <div class="d3" @click="$router.push('/userTransaction')">我的交易</div>
            </div>
           <div class="d_right">
               <div class="d4">最高¥{{maxPrice.toFixed($config.precision)}}最低¥{{proposedPrice}}</div>
               <!--<div class="d4">涨幅:{{gain}}%</div>-->
               <div class="d4">24H交易量</div>
               <div class="d5">{{transMoney}}</div>
               <div class="d6">挂单量</div>
               <div class="d7">{{sumPrebuy}}</div>
            </div>
            <div class="d8" @click="$router.push(`/noticeList?title=规则&t=2`)">规则</div>
        </div>
        <div class="buyBtn" @click="showBuy=true">买</div>
        <div class="box_trade">
            <div class="tab_trade_box">
                <div class="tab_trade">
                    <div class="tab_trade_1" @click="search(1)">
                        <img src="~@/assets/img/transaction/buy.png" alt="">
                    </div>
                    <div class="tab_trade_2" @click="search(2)">
                        <img src="~@/assets/img/transaction/sell.png" alt="">
                    </div>
                </div>
            </div>
            <van-pull-refresh v-model="refreshingDaybills" @refresh="refresh">
            <van-list
                    v-model="loading"
                    :finished="finished"
                    finished-text="没有更多了"
                    @load="onLoad">
            <div class="trade_list" >
                <div class="item_buy"  v-for="item in list" :key="item.id">
                    <div :class="'pay_type'+item.payType">
                    </div>
                    <div class="pay_content">
                        <div class="name">UID:{{(item.buyerId+"").substr(0,2)}}***{{(item.buyerId+"").substr(3,2)}}</div>
                        <div class="name">单价:{{(NumberDeVide(item.price,item.total)).toFixed($config.precision)}}</div>
                        <div class="price">数量:{{item.numberSection}}</div>
                        <div class="remark">最近30天成交量:{{item.counts}}</div>
                    </div>
                    <div class="pay_btn" v-if="account.userId==item.buyerId&&item.status==1">已完成</div>
                    <div class="pay_btn" @click="cancle(item.id)" v-if="account.userId==item.buyerId&&item.status==0">结束求购</div>
                    <div class="pay_btn" @click="showPopup(item)" v-if="account.userId!=item.buyerId" >出售</div>
                </div>
            </div>
            </van-list>
            </van-pull-refresh>
        </div>
        <van-popup v-model="showBuy">
            <div class="dialog_box">
                <div class="cell_box">
                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>总量:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <input placeholder="请输入数量" v-model="buyNumber"  />
                        </div>
                    </div>
                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>起量:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <input placeholder="请输入起量" v-model="buyStartNum" />
                        </div>
                    </div>
                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>支付方式:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <select  @change="onchangePayType" name="payType" v-model="buyPayType">
                                <option value="">选择付款方式</option>
                                <option value="1">支付宝</option>
                                <option value="2">USDT</option>
                                <option value="3">银行卡</option>
                            </select>
                        </div>
                    </div>

                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>单价:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <input placeholder="请输入单价" v-model="aprice" />
                        </div>
                    </div>
                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>总价:</span>
                        </div>
                        <div class="dialog_cell_right">
                            {{NumberMul(aprice,buyNumber)}}{{dw}}
                        </div>
                    </div>

                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>交易密码:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <input placeholder="请输入交易密码" type="password" v-model="buyPaypassword" />
                        </div>
                    </div>


                    <div class="dialog_btns">
                        <div class="btn1" @click="showBuy=false,buyPaypassword=''">
                            取消
                        </div>
                        <div class="btn2" @click="submitBuypre">
                            确定
                        </div>
                    </div>
                </div>
            </div>
        </van-popup>



        <van-popup v-model="show">
            <div class="dialog_box">
                <div class="cell_box">
                    <div class="dialog_cell">
                    <div class="dialog_cell_left">
                        <span>总价:</span>
                    </div>
                     <div class="dialog_cell_right">
                         <span>  {{NumberMul(number,priceDj)}} <span v-if="payType==2">USDT</span>
                                                    <span v-else>CNY</span> </span>
                     </div>
                    </div>
                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>支付:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <span>{{payType|formatPayType}}</span>
                        </div>
                    </div>
                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>数量:</span>
                        </div>
                        <div class="dialog_cell_right">
                          <input placeholder="请输入数量" v-model="number" />
                        </div>

                    </div>
                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>手续费:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <span>{{NumberMul(number,account.transFee)}}</span>
                        </div>
                    </div>

                    <div class="dialog_cell">
                        <div class="dialog_cell_left">
                            <span>交易密码:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <input placeholder="请输入交易密码" type="password" v-model="paypassword" />
                        </div>
                    </div>

                    <div class="dialog_cell" v-if="userInfo.bindAuthCode === 1||googleAuth === 1">
                        <div class="dialog_cell_left">
                            <span>谷歌验证码:</span>
                        </div>
                        <div class="dialog_cell_right">
                            <input placeholder="请输入谷歌验证码" v-model="code" />
                        </div>
                    </div>
                    <div class="dialog_btns">
                        <div class="btn1" @click="show=false">
                            取消
                        </div>
                        <div class="btn2" @click="submitSell">
                            确定
                        </div>
                    </div>
                </div>
            </div>
        </van-popup>
        <tabs class="tabs" />
    </div>
</template>

<script>
    // @ is an alias to /src
    import tabs from '@/components/tabs.vue'
    import api from '@/api'
    import {mapActions, mapState} from 'vuex'
    export default {
        name: 'transaction',
        components: {
            tabs
        },
        computed: {

            ...mapState('user', ['account','userInfo'])
        },
        data() {
            return {
                showpaypassword:false,
                buyPaypassword:'',
                dw:'',
                value:'',
                refreshingDaybills: false,
                proposedPrice:'',
                buyNumber:0,
                buyPrice:0,
                buyStartNum:0,
                buyPayType:0,
                showBuy:false,
                purposeId:0,
                aprice:0,
                paypassword:'',
                list: [],
                loading: false,
                finished: false,
                page: 1,
                sumPrebuy: 0,
                minPrice: 0,
                maxPrice: 0,
                gain: 0,
                transMoney: 0,
                show: false,
                price:0,
                priceDj:0,
                payType:0,
                number:0,
                code:'',
                googleAuth: 0,
                a:''

            }
        },

        filters: {

            formatPayType :function(value){
                if(value=="1"){
                    return "支付宝";
                }
                if(value=="2"){
                    return "USDT";
                }
                if(value=="3"){
                    return "银行卡";
                }

            }
        },


        methods: {
            ...mapActions('user',['getUserInfo']),
            ...mapActions('common',['selectTab','getHomeInfo']),
                onchangePayType(){
                if(this.buyPayType==2){
                    this.dw='(USDT)'

                }else{
                    this.dw='(CNY)'
                }
            },
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
            submitSell(){
                let self = this;
                // if(!/\d+/.test(this.code)){
                //     this.$toast.fail('请输入有效的谷歌验证码')
                //     return;
                // }
                api.sell({
                    purposeId:this.purposeId,
                    amount:this.number,
                    paypassword:this.paypassword,
                    code:this.code,
                }).then(res=>{
                    if(res.data.code==0){
                    this.$toast.success(res.data.msg)
                    self.show=false;
                    this.refresh();
                    self.getBuyList();
                }
                console.log(res.data);
            })
            },
            submitBuypre(){
                this.buyPrice = this.NumberMul(this.aprice,this.buyNumber);
                this.$dialog.confirm({
                    title: '标题',
                    message: '总价格为'+this.buyPrice + (this.buyPayType==2?'USDT':'CNY')+',确定发布吗',
                })
                    .then(() => {
                    this.submitBuy();
                }).catch(() => {
                    // on cancel
                });
            },
            submitBuy(){
                this.buyPrice = this.NumberMul(this.aprice,this.buyNumber);
                let self = this;
                api.publishBuy({
                    paypassword:this.buyPaypassword,
                    payType:this.buyPayType,
                    total:this.buyNumber,
                    buyStart:this.buyStartNum,
                    price:this.buyPrice,
                }).then(res=>{
                    if(res.data.code==0){
                        this.$toast.success(res.data.msg)
                       // setTimeout(self.init(),3000);
                        this.showBuy=false;
                        self.init();
                }else{
                    this.$toast.fail(res.data.msg)
                }
                console.log(res.data);
            })
            },
            search(id) {
                if(id==1){
                    this.list = [];
                    this.loading = false;
                    this.finished = false;
                    this.page = 1;
                    this.onLoad();
                }
                if(id==2){
                    this.list = [];
                    this.loading = false;
                    this.page = 1;
                    self.finished = true;
                }
            },
            refresh() {
                this.list = [];
                this.loading = false;
                this.finished = false;
                this.page = 1;
            },
            showPopup(item) {
                console.log(item)
                this.number =item.numberSection.split('-')[1];
                this.priceDj = item.price/item.total;
                this.price = this.priceDj*this.number;
                this.payType = item.payType;
                this.purposeId =item.id;
                this.show = true;
            },
            init(){
             api.getIndexData({
                }).then(res=>{
                    if(res.data.code==0){
                        this.sumPrebuy = res.data.sumPrebuy;
                        this.minPrice = res.data.minPrice;
                        this.maxPrice = res.data.maxPrice;
                        this.gain = res.data.gain;
                        this.transMoney = res.data.transMoney;
                        this.proposedPrice = res.data.proposedPrice
                        this.googleAuth = res.data.googleAuth
                }
                    console.log(res.data);
            })
            },

            onSearch() {
                this.refresh();
            },
            onLoad() {
                if (this.refreshingDaybills) {
                    this.list = [];
                    this.page = 1;
                    this.refreshingDaybills = false;
                }
                let self = this;
                console.log("onLoad function strike!");
                this.loading = true;
                api.getBuyList({
                    page: this.page,
                    limit: 10,
                    buyerId:this.value
                }).then(res=>{
                    if(res.data.code === 0){
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
    ,
    created(){
        this.selectTab(3)
        console.log("created function strike!");
        //   setTimeout(this.end(),4000);
        this.init()
        this.getUserInfo();
        this.getHomeInfo();
    }
    }
</script>
<style lang="scss" scoped>
    .transaction {
        color: #999;
        background-image: url("~@/assets/img/common/bg_0.jpg");
        overflow: hidden;
        background-position: top center;
        background-size: 100% 92%;
        background-repeat: no-repeat;
        background-attachment: fixed;
        width: 750px;
        height: 1520px;
        display: flex;
        flex-flow: column;
        align-items: center;
        .van-search{
            width: 100%;
        }
        .buyBtn{
            top: 480px;
            left: 20px;
            background-color: #e26b1b;
            color: #f9f8f8;
            /* padding: 0.3rem; */
            border-radius: 50%;
            font-size: 1.6rem;
            position: fixed;
            z-index: 10;
            width: 3rem;
            height: 3rem;
            text-align: center;
            line-height: 3rem;
            bottom: 36%;
            filter: alpha(Opacity=80);
            -moz-opacity: 0.8;
            opacity: 0.8;
            font-weight: 500;
            text-shadow: 5px 2px 6px #000;
            animation: swing_delay 2s linear 5s infinite;
            -moz-animation: swing_delay 2s linear 5s infinite;
            -webkit-animation: swing_delay 2s linear 5s infinite;
        }
        .van-popup--center{
            top: 40%!important;
        }
        .van-popup{
            background-color:transparent;
            .dialog_box{
                background: url(~@/assets/img/transaction/bg_box2.png) no-repeat;
                background-size: 100% 100%;
                width: 280px;
                height: 400px;
                margin-left:16px ;
                margin-right:16px ;
                .cell_box{
                    padding-top: 60px;
                    .dialog_cell{
                        border-bottom: 1px solid #D9D9D9;
                        display: flex;
                        box-sizing: border-box;
                        width: 80%;
                        padding: 10px 16px;
                        margin: 0 auto;
                        overflow: hidden;
                        color: #323233;
                        font-size: 14px;

                        line-height: 24px;
                        .dialog_cell_left{
                            width: 120px;
                        }
                        .dialog_cell_right{
                           input{
                               display: block;
                               box-sizing: border-box;
                               width: 100%;
                               min-width: 0;
                               margin: 0;
                               padding: 0;
                               color: #323233;
                               line-height: inherit;
                               text-align: left;
                               background-color: transparent;
                               border: 0;
                               resize: none;
                           }
                        }
                    }
                    .dialog_btns{
                        width: 80%;
                        padding-top: 10px;
                        margin: 0 auto;
                        display: flex;
                        justify-content: center;
                        .btn1{
                            width: 80px;
                            height: 40px;
                            line-height: 40px;
                            text-align: center;
                            color: #fafafa;
                            margin-right: 20px;
                            background: url(~@/assets/img/transaction/btn_green.png) no-repeat;
                            background-size: 100% 100%
                        }
                        .btn2{
                            color: #fafafa;
                            height: 40px;
                            line-height: 40px;
                            width: 80px;
                            text-align: center;
                            margin-left: 20px;
                            background: url(~@/assets/img/transaction/btn_green.png) no-repeat;
                            background-size: 100% 100%;
                        }
                    }
                }
            }
        }
        .top_notice{
            width: 680px;
            height: 280px;
            margin-top:20px;
            display: flex;
            background: url(~@/assets/img/transaction/bg_panel.png),no-repeat;
            background-position: center center;
            background-size: 100% 100%;
            justify-content: space-around;
            padding: 20px;

            .d_left{
                flex-direction: column;
                display: flex;
                align-items: center;
                margin-top: 20px;
            }
            .d_right{
                margin-top: 20px;
            }
            .d8{

                line-height: 460px;
            }
        }
        .box_trade{
            background: url(~@/assets/img/transaction/bg_box.png) no-repeat;
            background-size: 100% 100%;
            height: 1000px;
            margin-left:16px ;
            margin-right:16px ;
            margin-top: 16px;
            overflow: hidden;
            .trade_list{
                margin: 0 50px;
                height: 660px;
                overflow-x:hidden;
                .item_buy{
                    display: flex;
                    background: url(~@/assets/img/transaction/bg_panel.png) no-repeat;
                    background-size: 100% 100%;
                    height: 200px;
                    align-items: center;
                    justify-content: space-evenly;
                    .pay_type1{
                        margin-left: 10px;
                        width: 90px;
                        height: 90px;
                        background-size: 100% 100%;
                        background-image: url("~@/assets/img/paytype/alipay.jpg");
                    }
                    .pay_type2{
                        width: 90px;
                        height: 90px;
                        background-size: 100% 100%;
                        background-image: url("~@/assets/img/paytype/usdt.jpg");
                    }
                    .pay_type3{
                        width: 90px;
                        background-size: 100% 100%;
                        height: 90px;
                        background-image: url("~@/assets/img/paytype/bank.jpg");
                    }
                    .pay_content{
                        display: flex;
                        flex-direction: column;
                        .price{
                            font-size: 28px;
                        }
                    }
                    .pay_btn{
                        background: #1386ff;
                        width: 100px;
                        color: #fff;
                        display: block;
                        text-align: center;
                        font-size: 16px;
                        height: 40px;
                        line-height: 40px;
                        border-radius: 15px;
                        margin-top: 1px;
                        margin-right: 2px;
                    }
                }
            }
            .tab_trade_box{
                margin-top:50px;
                height: 120px;
                display: flex;
                justify-content: center;
                align-items: center;
                .tab_trade{
                    display: flex;
                    width: 80%;
                    height: 100px;
                    background: url(~@/assets/img/transaction/circle.png) no-repeat;
                    background-size: 100% 100%;
                    justify-content: center;
                    align-items: center;
                    .tab_trade_1{
                        margin-right: 10px;
                        width: 45%;
                        img{
                            width: 100%;
                        }
                    }
                    .tab_trade_2{
                        margin-left: 10px;
                        width: 45%;
                        img{
                            width: 100%;
                        }
                    }
                }
            }

        }
    }
</style>
