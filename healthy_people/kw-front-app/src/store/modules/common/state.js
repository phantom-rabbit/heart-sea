const ls = require('local-storage');
const initState = {
  //tabs页
  tabIndex: 1,
  //待领取
  totalAmount: 0,
  //通告
  noticeList:[],
  noticeHome:{},
  noticeDetail:{},
  //
  account: {},
  //已经点击领取记录
  totalAmounted: 0,
  iconNum: 20, //垃圾总数
  iconNames:['饮水桶','一次性餐具','刷子','包装袋','塑料桶','塑料玩具','塑料瓶','塑料袋','塑料餐盒','塑胶手套','易拉罐'
    ,'泡沫箱','洗手液瓶','渔网','烟头','牛奶盒','玻璃瓶','罐头','轮胎','麻绳'],
  rbNum:7, //每天随机数量
  amountIcon:[],//{icon:'',amount:'',name:''}
  bankList: [],
  transDetail:{},

  sdInfo:{},
  /**
   * 背景编号
   */
  bg: ls.get('bg') === null || ls.get('bg') === undefined  || ls.get('bg') === '' ? 0 : ls.get('bg'),
  /**
   * 首页机器运转
   */
  startHomeAutoCollect: false,
}


export default {
  ...initState
}
