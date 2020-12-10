import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import { PasswordInput, NumberKeyboard,Search,Tab, Tabs ,Area, Uploader,Form,Field,Button,List,PullRefresh,Cell,NavBar,Panel,Popup,DropdownMenu, DropdownItem, CellGroup, Dialog,CountDown,Empty  } from 'vant';
import 'vant/lib/index.css';
import config from '@/utils/config'
import VueClipboard from 'vue-clipboard2'
import FastClick from 'fastclick' // 去除手机端点击300ms
import * as math from "mathjs"

FastClick.attach(document.body)
FastClick.prototype.onTouchEnd = function (event) {
    if (event.target.hasAttribute('type') && event.target.getAttribute('type') === 'text') {
        event.preventDefault()
        return false
    }
}

Vue.use(require('vue-moment'));
Vue.use(VueClipboard)
// 全局注册
Vue.use(PasswordInput);
Vue.use(NumberKeyboard);

Vue.use(Search);
Vue.use(Tab);
Vue.use(Tabs);
Vue.use(Area);
Vue.use(Uploader);
Vue.use(Popup)
Vue.use(Form);
Vue.use(Field);
Vue.use(Button);
Vue.use(List);
Vue.use(PullRefresh);
Vue.use(Cell);
Vue.use(Panel);
Vue.use(NavBar);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(CellGroup);
Vue.use(Dialog);
Vue.use(CountDown);
Vue.use(Empty);




import httpRequest from '@/utils/httpRequest'
Vue.prototype.$http = httpRequest // ajax请求方法
Vue.prototype.$config = config
Vue.prototype.NumberMul = function(arg1, arg2) {
    if(arg1==''||arg1==null){
        arg1=0;
    }
    if(arg2==''||arg2==null){
        arg2=0;
    }

    const d = math.bignumber(arg1)
    const e = math.bignumber(arg2)
    return Math.floor(math.multiply(d,e)*100)/100;
}
Vue.prototype.NumberDeVide = function(arg1, arg2) {
    const d = math.bignumber(arg1)
    const e = math.bignumber(arg2)
    return Math.floor(math.divide(d,e)*100)/100;
}

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
