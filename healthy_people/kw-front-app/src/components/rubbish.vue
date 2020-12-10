<template>
    <div class="rubbish">
        <div ref="rubbish"></div>
        <!--<transition name="fade">-->
        <div v-show="show">
            <div :ref="'rubbish-item'+index" class="rubbish-item"
                 v-for="(item,index) in amountIcon" :key="index" :style="position[index]">
                <div class="r" :class="'r_'+item.icon"/>
            </div>
        </div>
        <!--</transition>-->
        <div class="amount">+{{amount}}</div>

        <div class="mod-ball" @click="start()">
            <div ><!--:class="{moveX:startHomeAutoCollect}"-->
                <div class="ball-box"> <!--:class="{moveY:startHomeAutoCollect}"-->
                    <div ref="ball" class="smash-ball" >
                        <div class="orbit" :class="{hide:!startHomeAutoCollect}"></div>
                        <div class="orbit" :class="{hide:!startHomeAutoCollect}"></div>
                        <div class="orbit" :class="{hide:!startHomeAutoCollect}"></div>
                        <div class="ball"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import {TweenLite, TweenMax} from 'gsap'
    // import ball from '@/components/ball.vue'
    export default {
        name: 'rubbish',
        data() {
            return {
                disabled: [],
                show: false,
                tween_a: [],
                tween_timer: [],
                tween_b: [],
                position: [],
                amount: 0
            }
        },
        computed: {
            ...mapState('common', ['totalAmount', 'totalAmounted', 'startHomeAutoCollect','amountIcon', 'rbNum'])
        },
        methods: {
            ...mapActions('common', ['getHomeInfo', 'setTotalAmounted', 'addAccountAmount', 'startHomeAutoCollectAction']),
            ...mapActions('user', ['saveIncome']),
            handleStyle() {
                return {
                    left: (Math.random() * 80) + '%',
                    top: (Math.random() * 50 + 30) + '%',
                }
            },
            start() {

                let that = this;
                // if(!this.startHomeAutoCollect){
                //     that.$toast.success("启动成功")
                // }
                this.startHomeAutoCollectAction(true)
                setTimeout(() => {
                    that.amountIcon.forEach((item, index) => {
                        setTimeout(() => {
                            that.move(that.$refs['rubbish-item' + index], item.amount, index)
                        }, (index + 1) * 2 * 1000)
                    })
                }, 3000)
            },
            move(e, amount, index) {
                if (this.disabled[index]) {
                    return;
                }
                this.disabled[index] = true

                //清除定时器
                if (this.tween_timer[index]) {
                    clearInterval(this.tween_timer[index])
                }
                //暂停跳动
                if (this.tween_a[index]) {
                    this.tween_a[index].pause()
                }
                if (this.tween_b[index]) {
                    this.tween_b[index].pause()
                }

                let rect = {};
                rect.top = e[0].offsetTop;
                rect.left = e[0].offsetLeft;
                rect.height = e[0].offsetHeight;
                rect.width = e[0].offsetWidth;

                const top = parseFloat(rect.top + rect.height * 2);//获取点击的dom的位置
                const left = parseFloat(rect.left)//获取点击的dom的位置

                let target = this.$refs.ball.getBoundingClientRect()

                let targetTop = target.top;
                let targetLeft = target.left;

                let x = parseFloat(targetLeft - left-10)
                let y = parseFloat(targetTop - top)

                let that = this;

                TweenLite.to(e, 4, {
                    x: x, y: y, onComplete: function () {
                        that.amount = amount;
                        TweenLite.fromTo('.amount', 3, {
                            opacity: 1,
                            x: window.innerWidth / 2 - 35,
                            y: y+120
                        }, {opacity: 0, x: window.innerWidth / 2 - 20, y: y - 300})
                        that.addAccountAmount(amount)
                        that.setTotalAmounted(this.totalAmounted + amount)

                        if(that.rbNum -1 === index){
                            that.startHomeAutoCollectAction(false)
                        }
                    }
                });
                TweenLite.fromTo(e, 3, {opacity: 1}, {opacity: 0});
                TweenLite.fromTo(e, 3, {scale: 1}, {scale: 0});

                if (index === 0) {
                    this.saveIncome()
                }

            }
        },
        mounted() {
            let that = this;
            if (this.show) return;
            setTimeout(() => {
                let fx = []
                that.amountIcon.forEach((item, index) => {
                    that.position[index] = that.handleStyle()
                    fx[index] = Math.random() > 0.5;
                    fx[index] = !fx[index];
                    let a = ((Math.random()*100000) % 30) * (fx[index] ? -1 : 1)
                    let target = that.$refs['rubbish-item' + index];
                    TweenMax.fromTo(target, 3, {opacity: 0}, {opacity: 1});
                    let sec = 1
                    if (target) {
                        that.tween_timer[index] = setInterval(() => {
                            that.tween_a[index] = new TweenMax(target, sec, {y: a,});
                            that.tween_b[index] = new TweenMax(target, sec, {y: -a, delay: sec});

                        }, sec * 2 * 1000)
                    }
                    if (index === that.amountIcon.length - 1) {
                        that.show = true
                    }
                })
            }, 1000)
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>

    @keyframes a_bg {
        0% {
            transform: scale(.6);
            opacity: 0;
        }
        50% {
            transform: scale(1);
            opacity: .7;
        }
        100% {
            transform: scale(1.3);
            opacity: 0;
        }
    }

    .rubbish {
        position: fixed;
        top: 25%;
        width: 100%;
        left: 0;
        z-index: 10;

        .amount {
            opacity: 0;
            font-size: 30px;
            color: #fff;
            width: 120px;
            height: 120px;
            background-color: #4284e7;
            border-radius: 120px;
            line-height: 120px;
            text-align: center;
            font-weight: 900;
        }

        /*background-color: #fff;*/
        height: 40%;
        /*position: relative;*/
        .rubbish-item {
            width: 130px;
            height: 130px;
            position: absolute;
            z-index: 999;

            .r {
                overflow: hidden;
                background-position: center center;
                background-size: 100% 100%;
                background-repeat: no-repeat;
                width: 100%;
                height: 100%;

                /*animation: a_bg 1s linear infinite;*/
                &_0 {
                    background-image: url("~@/assets/img/home/rb_0.png");
                }

                &_1 {
                    background-image: url("~@/assets/img/home/rb_1.png");
                }

                &_2 {
                    background-image: url("~@/assets/img/home/rb_2.png");
                }

                &_3 {
                    background-image: url("~@/assets/img/home/rb_3.png");
                }

                &_4 {
                    background-image: url("~@/assets/img/home/rb_4.png");
                }

                &_5 {
                    background-image: url("~@/assets/img/home/rb_5.png");
                }

                &_6 {
                    background-image: url("~@/assets/img/home/rb_6.png");
                }

                &_7 {
                    background-image: url("~@/assets/img/home/rb_7.png");
                }

                &_8 {
                    background-image: url("~@/assets/img/home/rb_8.png");
                }

                &_9 {
                    background-image: url("~@/assets/img/home/rb_9.png");
                }

                &_10 {
                    background-image: url("~@/assets/img/home/rb_10.png");
                }

                &_11 {
                    background-image: url("~@/assets/img/home/rb_11.png");
                }

                &_12 {
                    background-image: url("~@/assets/img/home/rb_12.png");
                }

                &_13 {
                    background-image: url("~@/assets/img/home/rb_13.png");
                }

                &_14 {
                    background-image: url("~@/assets/img/home/rb_14.png");
                }

                &_15 {
                    background-image: url("~@/assets/img/home/rb_15.png");
                }

                &_16 {
                    background-image: url("~@/assets/img/home/rb_16.png");
                }

                &_17 {
                    background-image: url("~@/assets/img/home/rb_17.png");
                }

                &_18 {
                    background-image: url("~@/assets/img/home/rb_18.png");
                }

                &_19 {
                    background-image: url("~@/assets/img/home/rb_19.png");
                }
            }
        }


        .fade-enter-active, .fade-leave-active {
            transition: opacity .5s;
        }

        .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
        {
            opacity: 0;
        }
    }


    .mod-ball {
        background: transparent;
        display: flex;
        margin: 0;
        position: absolute;
        top: 100px;
        left: 0;
        width: 100%;
        height: 100%;
        bottom: 100px;
        z-index: 2;
        justify-content: center;
        align-items: flex-end;


        .moveX {
            animation: moveX 12.5s ease-in-out infinite alternate;
            margin: auto;
        }

        .moveY {
            animation: moveY 15.7s ease-in-out infinite alternate;
        }

        .smash-ball,
        .smash-ball div {
            width: 120px;
            height: 120px;
        }

        .smash-ball {
            transform-style: preserve-3d;
        }

        .orbit {
            border-width: 3px 3px 3px 0;
            border-style: solid;
            border-color: #ffffff #f7f7f7 transparent transparent;
            border-radius: 50%;
            filter: blur(1px);
            -webkit-filter: blur(1px);
            position: absolute;
            top: -3px;
            left: -3px;
            z-index: 1;

            &.hide{
                border:none;
            }
        }

        .orbit:nth-of-type(1) {
            transform: scale(1.3) rotateX(70deg) rotateY(60deg) rotateZ(0deg);
            animation: orbit-1 1.5s linear infinite;
        }

        .orbit:nth-of-type(2) {
            transform: scale(1.3) rotateX(70deg) rotateY(120deg) rotateZ(0deg);
            animation: orbit-2 1.5s linear infinite;
        }

        .orbit:nth-of-type(3) {
            transform: scale(1.3) rotateX(70deg) rotateY(180deg) rotateZ(0deg);
            animation: orbit-3 1.5s linear infinite;
        }

        .ball {
            filter: saturate(150%);
            -webkit-filter: saturate(150%);
            background-color: #268dc5;
            background-image:radial-gradient(#2196F3, #91c2e5);
            background-size: 100px 100px;
            border-radius: 50%;
            box-shadow: 0 0 5px 1.5px #03A9F4, 0 0 10px 10px #8cccff;
            overflow: hidden;
            position: relative;
            animation: smashBall 1s linear infinite;
        }

        .ball::before,
        .ball::after {
            content: "";
            display: block;
            position: absolute;
            width: 100%;
            height: 100%;
        }

        .ball::before {
            /* background-image: linear-gradient(90deg, transparent, transparent 16.6px, black 16.6px, black 38.9px, transparent 38.9px, transparent), linear-gradient(transparent, transparent 57.1px, black 57.1px, black 66.1px, transparent 66.1px, transparent), radial-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 0) 62%, #000000 62%, #000000); */
             opacity: 0.45;
        }

        .ball::after {
            background-image: radial-gradient(#ffffff, rgba(0, 0, 0, 0) 64%);
        }

        @keyframes orbit-1 {
            from {
                transform: scale(3.3) rotateX(70deg) rotateY(60deg) rotateZ(0deg);
            }
            to {
                transform: scale(3.3) rotateX(70deg) rotateY(60deg) rotateZ(360deg);
            }
        }
        @keyframes orbit-2 {
            from {
                transform: scale(3.3) rotateX(70deg) rotateY(120deg) rotateZ(60deg);
            }
            to {
                transform: scale(3.3) rotateX(70deg) rotateY(120deg) rotateZ(420deg);
            }
        }
        @keyframes orbit-3 {
            from {
                transform: scale(3.3) rotateX(70deg) rotateY(180deg) rotateZ(120deg);
            }
            to {
                transform: scale(3.3) rotateX(70deg) rotateY(180deg) rotateZ(480deg);
            }
        }
        @keyframes smashBall {
            from {
                background-position: 0 200px;
                box-shadow: 0 0 5px 2.5px white, 0 0 15px 11px #8cccff;
            }
            50% {
                box-shadow: 0 0 5px 2px white, 0 0 15px 9px #8cccff;
            }
            to {
                background-position: 0 100px;
                box-shadow: 0 0 5px 2.5px white, 0 0 15px 10px #8cccff;
            }
        }
        @keyframes moveX {
            from {
                transform: translateX(-40vw);
            }
            to {
                transform: translateX(25vw);
            }
        }
        @keyframes moveY {
            from {
                transform: translateY(-8vh);
            }
            to {
                transform: translateY(25vh);
            }
        }
    }
</style>
