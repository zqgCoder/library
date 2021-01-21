router.beforeEach((to, from, next) => {
  let isAuthenticated = this.store.isAuthenticated
  let role = this.store.roles
  //未登录且访问路径不是登录页
  if (to.name !== 'Login' && !isAuthenticated) next({
    name: 'Login'
  })
  //未登录且访问路径是登录页
  else if (to.name === 'Login' && !isAuthenticated) next()
  //已经登录访问登录页，跳转到首页
  else if (to.name === 'Login' && isAuthenticated) next({
    name: 'home'
  }) else {
    next() //next()方法后的代码也会执行
    // 已经登录
    // 1.如果路由表 没根据角色进行筛选,就筛选一次
    if (!addRouFlag) {
      addRouFlag = true
      // 2.根据用户的角色、和需要动态展示的路由，生成符合用户角色的路由
      var getRoutes = baseRoleGetRouters(permissionRouter, GetRole.split(","))
      // 3.利用global属性，让渲染菜单的组件sideMeuns.vue重新生成左侧菜单
      global.antRouter = fixedRouter.concat(getRoutes)
      // 4.将生成好的路由addRoutes
      router.addRoutes(fixedRouter.concat(getRoutes))
      // 5.push之后，会重新进入到beforeEach的钩子里,直接进入第一个if判断
      router.push({
        path: to.path
      })
    }
  }
})
