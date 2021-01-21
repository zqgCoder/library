import Vue from 'vue'
import Vuex from 'vuex'
//引入cookie
import Cookie from  'js-cookie'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    menu: [],
    currentMenu: {
      label: ''
    },
    tabsList: [{
      path: '/',
      label: '首  页',
      icon: 's-home'
    }],
    roles: 'upload',
    isAuthenticated: false,
    isCollapse: false
  },
  mutations: {
    //commit()提交事件，当新增一个标签页的时候，会根据是不是首页显示当前所在位置；还用来判断是否添加到tabsList中
    selectMenu(state, val) {
      console.log(val, 'val')
      if (val.label !== '首  页') {
        state.currentMenu.label = val.label
        // 判断是否含有该分页,找不到返回-1
        let result = state.tabsList.findIndex(item => item.label === val.label)
        result === -1 ? state.tabsList.push(val) : ''
      } else {
        state.currentMenu.label = ''
      }
    },
    // 关闭标签
    closeTab(state, val) {
      let result = state.tabsList.findIndex(item => item.label === val.label)
      state.tabsList.splice(result, 1)
    },
    //接受后台传递的菜单
    setMenu(state, val) {
      state.menu = val
      Cookie.set('menu', JSON.stringify(val))
    }
  },
  //清除cookie中的菜单
  clearMenu(state) {
    state.menu = []
    cookie.remove('menu')
  },
  getters: {
    doneCurrentMenu(state) {
      return state.currentMenu
    }
  },
  actions: { //异步性操作

  }
})
