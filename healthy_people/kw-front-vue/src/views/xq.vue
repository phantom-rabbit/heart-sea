<template>
    <div class="temp">
    <ul>
        <li></li>
        <li><span></span></li>
        <li><span></span></li>
        <li><span></span></li>
        <li><span><span></span></span></li>
        <li><span></span></li>
        <li><span><span></span></span></li>
        <li><span></span></li>
        <li></li>
    </ul>
    </div>

</template>

<script>


    export default {
        name: 'Xq',
        data (){
            return{
                show: true,
                target:{},
                amount: 0,
            };
        },
        computed: {

        },


        created() {

        }
    }
</script>

<style lang="css" scoped>
    ul {
        height: 600px;
        width: 600px;
        margin: 50px auto;
        list-style: none;
    }
    ul li {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        border: 2px solid #333333;
    }
    li:nth-child(1) {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        box-shadow: 0 0 50px #B3C04C;
        background-color: #B3C04C;
    }
    li:nth-child(2) {
        width: 120px;
        height: 120px;
        border-radius: 50%;
        animation: rotate 30s linear infinite;
    }
    li:nth-child(2) span {
        width: 15px;
        height: 15px;
        border-radius: 50%;
        background-color: red;
        position: absolute;
        top: 0;
        left: 25px;
    }
    li:nth-child(3) {
        width: 180px;
        height: 180px;
        border-radius: 50%;
        animation: rotate 20s linear infinite;
    }
    li:nth-child(3) span {
        width: 20px;
        height: 20px;
        border-radius: 50%;
        background-color: green;
        position: absolute;
        top: 0;
        left: 35px;
    }
    li:nth-child(4) {
        width: 240px;
        height: 240px;
        border-radius: 50%;
        animation: rotate 20s linear infinite;
    }
    li:nth-child(4) span {
        width: 18px;
        height: 18px;
        border-radius: 50%;
        background-color: deepskyblue;
        position: absolute;
        top: 0;
        left: 60px;
    }
    li:nth-child(5) {
        width: 300px;
        height: 300px;
        border-radius: 50%;
        animation: rotate 10s linear infinite;
    }
    li:nth-child(5) > span {
        width: 25px;
        height: 25px;
        border-radius: 50%;
        background-color: #6dff39;
        position: absolute;
        top: 0;
        left: 135px;
        animation: rotate 10s linear infinite;
    }
    li:nth-child(5) > span span {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background-color: #ff40c0;
        position: absolute;
        top: 0;
        left: 30px;
    }
    li:nth-child(6) {
        width: 360px;
        height: 360px;
        border-radius: 50%;
        animation: rotate 30s linear infinite;
    }
    li:nth-child(6) span {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background-color: #faff27;
        position: absolute;
        top: 0;
        left: 130px;
    }
    li:nth-child(7) {
        width: 420px;
        height: 420px;
        border-radius: 50%;
        animation: rotate 40s linear infinite;
    }
    li:nth-child(7) > span {
        width: 25px;
        height: 25px;
        border-radius: 50%;
        background-color: #801377;
        position: absolute;
        top: 0;
        left: 190px;
        animation: rotate 15s linear infinite;
    }
    li:nth-child(7) > span span {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background-color: #4afff8;
        position: absolute;
        top: 0;
        left: 30px;
    }
    li:nth-child(8) {
        width: 480px;
        height: 480px;
        border-radius: 50%;
        animation: rotate 10s linear infinite;
    }
    li:nth-child(8) span {
        width: 15px;
        height: 15px;
        border-radius: 50%;
        background-color: #3f8042;
        position: absolute;
        top: 0;
        left: 175px;
    }
    li:nth-child(9) {
        width: 540px;
        height: 540px;
        border-radius: 50%;
    }
    @keyframes rotate {
        0% {
            transform: translate(-50%,-50%) rotate(0deg);
        }
        100% {
            transform: translate(-50%,-50%) rotate(7200deg);
        }
    }
    .temp{
        transform: rotateX(-30deg);
    }

    * {


        padding: 0;
        margin: 0;
    }
</style>
