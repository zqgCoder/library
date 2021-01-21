import Vue from 'vue'
import App from './App'
//  引入router，也就是index.js
import router from './router/index.js'

import store from './store/index.js'      //引入全局变量
import VueResource from 'vue-resource'
//引入并挂载md5，引用方式为this.$md5('holle')
import md5 from 'js-md5'
Vue.prototype.$md5 = md5

//导入http
import {server} from './api/config'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
var currentMenu = {
  title: ''
}
Vue.use(VueResource)
// 挂载到vue实例中，通过this.$server调用
Vue.prototype.$server = server

Vue.config.productionTip = false
Vue.prototype.$currentMenu = currentMenu
Vue.use(ElementUI)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  //  注入到vue实例对象中
  store,
  router: router,
  components: { App },
  template: '<App/>'
})
