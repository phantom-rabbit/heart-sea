<template>
    <div class="transaction">
        <div class="left"><img src="~@/assets/img/usertransaction/left.png" @click="$router.go(-1)"></div>

        <div class="seller_content">
            <div class="content_time">
                {{transDetail.status|statusFilter}}
                <van-count-down :time="transDetail.lastTime*1000"/>
            </div>


            <div class="content_top">
                <div :class="'type_img'+transDetail.payType"></div>
                <div class="type_datas">

                    <div class="pay_left">
                        <div>交易号:</div>
                        <div>数量:</div>
                        <div>总价:</div>
                        <div>手续费:</div>
                        <div>卖家手机:</div>
                        <div>下单时间:</div>
                        <div>订单状态:</div>
                        <!--<div>剩余时间: </div>-->
                    </div>
                    <div class="pay_right">
                        <div>{{transDetail.id}}</div>
                        <div>{{transDetail.amount}}</div>
                        <div>{{transDetail.price}}{{transDetail.payType|payTypeFilter}}</div>
                        <div>{{transDetail.fee}}</div>
                        <div>{{transDetail.sellerPhone}}</div>
                        <div>{{transDetail.createTime|formatDate}}</div>
                        <div>{{transDetail.status|statusFilter}}</div>
                        <!--<div>{{transDetail.status|statusDateFilter}}   </div>-->
                    </div>
                </div>
            </div>
            <div class="content_footer">
                <div class="upload_content">
                    <van-uploader :after-read="afterRead" :deletable="transDetail   .status|statusFilter1"
                                  v-model="transDetail.fileList" :max-count="1"/>

                </div>


            </div>


        </div>

        <div class="msg_footer">
            <div class="title t1">温馨提示:</div>
            <div class="title">1.请登录收款账号确认是否收款</div>
            <div class="title">2.交易时效为120分钟，120分钟后交易自动关闭</div>
            <div class="title">3.恶意刷单会导致封号。</div>

        </div>
        <div v-if="transDetail.status==0" class="btn" @click="submit()"></div>


        <div class="msg_footer" style="display:flex;align-items: center;align-content: center">

            <div class="title" style="width: 100%;text-align: center">收款账号:{{transDetail.payNumber}}</div>

        </div>

        <tabs class="tabs"/>
    </div>
</template>

<script>

    import {mapActions, mapState} from 'vuex'
    // @ is an alias to /src
    import api from '@/api'
    import tabs from '@/components/tabs.vue'
    import moment from 'moment'

    import axios from 'axios'

    export default {
        data() {
            return {
                uploader: true,
                userId: 0,
                fileList: [],
                url: '',
            }
        },
        name: 'tranbuyer',
        filters: {
            formatDate: function (value) {
                return moment(value).format('YYYY-MM-DD HH:mm:ss')
            },
            statusFilter1(value) {
                if (value == 0) {
                    return true
                } else {
                    return false;
                }
            },

            //  状态 (0待上传交易凭证1已上传 2完成 -1交易关闭)
            statusFilter(value) {
                if (value == 0) {
                    return "待上传交易凭证"
                }
                if (value == 1) {
                    return "已上传交易凭证"
                }
                if (value == -1) {
                    return "交易关闭"
                } else {
                    return "交易完成"
                }
            },
            statusDateFilter(value) {
                if (value == -1) {
                    return "交易关闭"
                }


            },
            payTypeFilter(value) {
                if (value == 2) {
                    return "USDT"
                } else {
                    return "CNY"
                }
            }
        },
        components: {
            tabs
        }, methods: {
            ...mapActions('common', ['getTransDetail']),

            submit() {
                if (this.url != '') {
                    api.uploadPhoto({
                        transId: this.transDetail.id,
                        photo: this.url
                    }).then(res => {
                        if (res.data.code === 0) {
                            this.$toast.success(res.data.msg)
                            this.refresh()
                        } else {
                            this.$toast.fail(res.data.msg)
                        }
                    });
                } else {
                    this.$toast.fail("请先上传图片")
                }


            },

            afterRead(file) {

                file.status = 'uploading';
                file.message = '上传中...';
                var formData = new FormData();
                formData.append('file', file.file) // 'userfile' 这个名字要和后台获取文件的名字一样;
                formData.append('chunk', '0')
                formData.append("name", "nihao");
                console.log(formData.get('file'))

                let config = {
                    headers: {'Content-Type': 'multipart/form-data'}
                }

                let url = this.$http.adornUrl(`/api/upload`)


                const http = axios.create({
                    baseURL: process.env.VUE_APP_BASE_API,
                    timeout: 1000 * 30,

                    headers: {'Content-Type': 'multipart/form-data'}
                })
                // 添加请求头
                http.put(url, formData, config)
                    .then(response => {
                        if (response.data.code == 0) {
                            this.url = response.data.url;
                            file.status = 'done';
                            this.transDetal.status = 1;
                        } else {
                            file.status = 'failed';
                            file.message = '上传失败';
                            this.$toast.fail(response.data.msg)
                        }
                        console.log(response.data)
                    })


            }

        },
        computed: {
            ...mapState('common', ['transDetail'])
        },
        mounted() {
            this.$nextTick(() => {
                this.getTransDetail(this.$route.query.id);
            });
        }

    }

</script>


<style lang="scss" scoped>


    .transaction {
        background-image: url("~@/assets/img/common/bg_0.jpg");
        overflow: hidden;
        background-position: top center;
        background-size: 100% 92%;
        background-repeat: no-repeat;
        background-attachment: fixed;
        width: 750px;
        height: 1520px;
        display: flex;
        color: #999;
        flex-flow: column;
        align-items: center;

        .btn {
            margin-top: 200px;
            background-image: url("~@/assets/img/transcontent/submit_btn.png");
            background-position: center center;
            background-size: 100% 100%;
            background-repeat: no-repeat;
            width: 238px;
            height: 78px;
        }

        .left {

            width: 100%;
            height: 87px;

            img {
                margin-left: 34px;
                margin-top: 22px;
                height: 48px;
                width: 26px;
            }
        }

        .content_time {
            height: 102px;
            text-align: center;
        }

        .seller_content {
            width: 707px;
            height: 628px;
            background-image: url("~@/assets/img/transcontent/transcontent.png");
            background-position: center center;
            background-size: 100% 100%;
            background-repeat: no-repeat;
            flex-direction: column;

            display: flex;

            .content_footer {
                /*display: flex;*/
                /*justify-content: center;*/
                display: flex;
                height: 100%;
                align-items: flex-end;
                justify-content: center;

                .upload_content {
                    //  background-image: url("~@/assets/img/transcontent/upload.png");
                    background-position: center center;
                    background-size: 100% 100%;
                    background-repeat: no-repeat;
                    width: 134px;
                    height: 134px;
                    margin-right: 22px;
                    margin-bottom: 29px;

                    img {
                        width: 134px;
                        height: 134px;
                    }
                }

                .complaint_content {
                    background-image: url("~@/assets/img/transcontent/complaint.png");
                    background-position: center center;
                    background-size: 100% 100%;

                    background-repeat: no-repeat;
                    margin-left: 22px;
                    width: 134px;
                    height: 134px;
                }

            }

            .content_top {
                margin-top: 24px;
                display: flex;

                .type_img1 {
                    margin-left: 24px;
                    margin-top: 26px;
                    width: 172px;
                    height: 132px;
                    background-size: 100% 100%;
                    background-image: url("~@/assets/img/usertransaction/alipay.png");
                }

                .type_img2 {
                    margin-left: 24px;
                    margin-top: 26px;
                    width: 172px;
                    height: 132px;
                    background-size: 100% 100%;
                    background-image: url("~@/assets/img/usertransaction/usdt.png");
                }

                .type_img3 {
                    margin-left: 24px;
                    margin-top: 26px;
                    width: 172px;
                    height: 132px;
                    background-size: 100% 100%;
                    background-image: url("~@/assets/img/usertransaction/bank.png");
                }

                .type_datas {
                    margin-left: 24px;
                    margin-top: 26px;
                    display: flex;
                    width: 100%;
                    justify-content: space-between;
                    align-items: center;
                    font-size: 26px;
                    color: #515151;
                    font-family: "SimSun";

                    .pay_left {
                        div {
                            line-height: 40px;
                        }

                        justify-content: center;

                        display: flex;
                        flex-direction: column;
                    }

                    .pay_right {
                        div {
                            line-height: 40px;
                        }

                        justify-content: center;
                        display: flex;
                        // margin-right: 37px;
                        flex-direction: column;
                    }

                }
            }
        }

        .msg_footer {
            font-size: 24px;
            margin-top: 18px;
            width: 707px;
            height: 200px;
            background-image: url("~@/assets/img/transcontent/content_footer.png");
            background-position: center center;
            background-size: 100% 100%;
            background-repeat: no-repeat;

            .title {

                margin-left: 26px;
            }

            .t1 {
                margin-top: 29px;

            }
        }


    }
</style>
