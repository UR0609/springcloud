import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {initMenu} from './utils/menuUtils';
import service from './utils/request.js'

const axios = require('axios');
axios.defaults.withCredentials = true;
axios.defaults.baseURL = 'http://localhost:8661/'

Vue.prototype.$axios = service
Vue.use(ElementUI);
Vue.config.productionTip = false

// 配置一个全局前置守卫
router.beforeEach((to, from, next)=> {
      // 首先判断目标页面是不是Login，若是Login页面，则直接通过，因为登录页不需要菜单数据
      // if (to.name == 'Login') {
      //   next();
      //   return;
      // }
      // 判断当前用户是否已经登录，否则跳回登录页
      // ........

      // 先初始化菜单数据
      initMenu(router, store);
      // 再进入下一个页面
      next();
    }
)

Vue.prototype.getToken = function() {
  let token = localStorage.getItem("token");
  return token;
}
Vue.prototype.setToken = function(token) {
  localStorage.setItem("token", token);
}
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
