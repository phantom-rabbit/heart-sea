<template>
    <div class="transaction">
        <van-nav-bar class="nav-bar"
                     title="申请市代"
                     left-text="返回"
                     left-arrow
                     @click-left="$router.go(-1)"
        />

        <div class="msg_title">
            <div class="title t1">操作指南:</div>
            <div class="title">第一步:选择您所要代理的城市</div>
            <div class="title">第二步: {{sdInfo.sdStep2}}</div>
            <div class="title">第三步: {{sdInfo.sdStep3}}</div>
            <div class="title">第四步:待我们审核通过</div>
            <div class="title t3">温馨提示:</div>
            <div class="title">1.申请市代,该市每个注册人都会给你提供0.1的币奖励</div>
            <div class="title">2.享受商城板块优先进驻分红权利</div>
            <div class="title">3.享受游戏板块分红</div>
            <div class="title">4.享受30%手续费奖励</div>
        </div>

        <div class="msg_footer" style="" v-if="account.sdStatus==2" >
            <div class="title" style="width: 100%;text-align: center">收款账号</div>
            <div class="title" style="width: 100%;text-align: center">{{sdInfo.usdtToken}}</div>

        </div>

        <div class="status2"  v-if="account.sdStatus==2" >
            <div class="item" >
                <div class="item-a">
                   <van-button @click="doCopy(sdInfo.usdtToken)" >复制USDT地址</van-button>
                </div>
            </div>
            <div class="upload_business_title" style="text-align: center">交易凭证</div>
            <div class="content_footer" >
                <div class="upload_content">
                    <van-uploader   :after-read="afterRead"   v-model="fileList"  :max-count="1" />
                </div>
                <!--<div class="complaint_content"></div>-->
            </div>
        </div>
        <van-button type="info"  @click="submitUpload()"  v-if="account.sdStatus==2">提交复审</van-button>


        <div class="status1"  v-if="account.sdStatus==1||account.sdStatus==3||account.sdStatus==4">

        <van-cell-group >
            <van-cell title="代理城市" :value="account.sdProvinceCity" />
            <van-cell title="状态" :value="account.sdStatus|sdStatusFilter"   />
        </van-cell-group>
        </div>
        <div class="status0"  v-if="account.sdStatus==0||account.sdStatus==-1">
        <van-cell   is-link @click="showPopup"  v-model="carmodel">{{this.city}}</van-cell>
            <van-popup
                    v-model="show"
                    position="bottom"
                    :style="{ height: '50%' }">
                <van-area
                        @confirm="onConfirm" @cancel="onCancel"
                        columns-num="2"
                        :area-list="areaList"
                        :columns-placeholder="['请选择', '请选择']"
                        title="选择市代城市"
                />
            </van-popup>
        </div>
        <van-button type="info"  @click="submit()" v-if="account.sdStatus==0||account.sdStatus==-1">提交初审</van-button>



    </div>


</template>

<script>
    import api from '@/api'
    import {mapActions, mapState} from 'vuex'
    import areaList from '../../assets/js/area.js'
    import axios from 'axios'
    export default {
        name: "sdApply",
        data() {
            return {

                areaList,
                show:false,
                carmodel:'',
                city:'选择代理的城市',
                fileList: [
                ],
                url:''
            }

        },
        created(){
            this.getSdInfo()
            this.getUserInfo();
        },
        computed: {
            ...mapState('user', ['account']),
            ...mapState('common', ['sdInfo'])
        },
        filters:{
            sdStatusFilter: (status) => {
                const map = {
                    4: '成功',
                    3: '后台终审',
                    2: '待打款上传',
                    1: '后台初审',

                }
                return map[status]
            }
    },

        methods:{
            ...mapActions('common',['getSdInfo']),
            ...mapActions('user',['getUserInfo']),
                doCopy: function (code) {

                let that = this
                this.$copyText(code).then(function () {
                    that.$toast('复制成功')
                }, function () {
                    that.$toast('复制失败')
                })
            },
                afterRead(file) {


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
                    if(response.data.code==0){
                    this.url = response.data.url;
                }else{
                    this.$toast.fail(response.data.msg)
                }
                console.log(response.data)
            })


            },
            submitUpload(){
                if(this.url==''){
                    this.$toast.fail('图片不能为空')
                    return ;
                }
                if(this.account.sdStatus!=2){
                    this.$toast.fail('状态不正确')
                    return ;
                }
                api.sdphoto({
                    photo:this.url,
                }).then(res=>{
                    if(res.data.code === 0){
                    this.$toast.success(res.data.msg)
                    this.url = res.data.url;

                    // this.refresh()
                }else{
                    this.$toast.fail(res.data.msg)
                }
            });

            },

            submit(){
                if(this.city.indexOf("请选择") != -1||this.city==''){
                    this.$toast.fail('选择代理的城市')
                    return ;
                }

                if(this.city=='选择代理的城市'||this.city==''){
                    this.$toast.fail('选择代理的城市')
                    return ;
                }

                api.sdpublish({
                    provinceCity:this.city,
                }).then(res=>{
                    if(res.data.code === 0){
                    this.$toast.success(res.data.msg)
                    this.url = res.data.url;

                   // this.refresh()
                }else{
                    this.$toast.fail(res.data.msg)
                }
            });




            }
            ,

            // 弹出层显示
            showPopup() {
                this.show = true;

            },
            //确定选择城市
            onConfirm(val){
                if(val[1].name==''||val[1].name.indexOf("请选择") != -1){
                    this.$toast.fail('选择代理的城市')
                    return ;
                }
                console.log(val[0].name+val[1].name);
                this.city = val[0].name+"-"+val[1].name;
                this.show = false//关闭弹框
            },
            //取消选中城市
            onCancel(){
               // this.getSdInfo();
                this.show = false
                this.$refs.myArea.reset()// 重置城市列表
            }


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
        flex-flow: column;
        align-items: center;
        color: #999;

        .status1{
            width: 100%;
            display: flex;
            justify-content: center;
            .van-cell-group{
                border-radius: 5px;

                width: 94%;
                margin-top: 20px;
            }
        }
        .status2{
            .upload_business_title{
               margin-top: 10px;
            }
            width: 100%;
            flex-direction: column;

            display: flex;
            .content_footer{
                margin-left: 20px;
                margin-right: 20px;
                height: 224px;
                /*background-image: url("~@/assets/img/transcontent/bg_photo.png");*/
                background-position: center center;
                background-size: 100% 100%;

                background-repeat: no-repeat;

                /*display: flex;*/
                /*justify-content: center;*/
                display: flex;
                height: 100%;
                align-items: flex-end;
                justify-content: center;

                .upload_content{
                    //  background-image: url("~@/assets/img/transcontent/upload.png");
                    background-position: center center;
                    background-size: 100% 100%;
                    background-repeat: no-repeat;
                    width: 134px;
                    height: 134px;
                    padding: 50px;



                    img{
                        width: 134px;
                        height: 134px;
                    }
                }

                .complaint_content{
                    background-image: url("~@/assets/img/transcontent/complaint.png");
                    background-position: center center;
                    background-size: 100% 100%;

                    background-repeat: no-repeat;
                    margin-left: 22px;
                    width: 134px;
                    height: 134px;
                }

            }
            .item{
                margin-top: 20px;
                .item-a{

                    .van-cell{
                        white-space: nowrap;
                        text-overflow: ellipsis;
                        overflow: hidden;
                    }
                    line-height: 60px;
                    color:#999;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    .div{
                        height: 44px
                    }
                }
            }
        }
        .btnUpload{

            margin-top: 100px;
            background-image: url("~@/assets/img/transcontent/submit_btn.png");
            background-position: center center;
            background-size: 100% 100%;
            background-repeat: no-repeat;
            width: 238px;
            height: 78px;

        }
        .btn{

            margin-top: 200px;
            background-image: url("~@/assets/img/transcontent/submit_btn.png");
            background-position: center center;
            background-size: 100% 100%;
            background-repeat: no-repeat;
            width: 238px;
            height: 78px;

        }
        .nav-bar{
            width: 100%;
            background-color: transparent;
        }

        .status0{
            margin-top: 60px;
            width: 100%;
        }
        .msg_footer{
            padding: 20px;
            color: white;
            word-break:break-all;
        }
        .msg_title{
            font-size: 24px;
            margin-top: 18px;
            width: 707px;
            height: 480px;
            background-image: url("~@/assets/img/transcontent/bg_sd.png");
            background-position: center center;
            background-size: 100% 100%;
            background-repeat: no-repeat;
            .title{

                margin-left: 26px;
            }
            .t1{

                margin-top: 29px;
            }
            .t3{
                margin-top: 89px;
            }
        }
    }

</style>