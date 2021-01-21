import Vue from 'vue';
import Router from 'vue-router'

Vue.use(Router)


//使用router在routes中查找route
const router = new Router({
	//  添加routes
	routes: [
		//不需要权限，无需动态生成
		{
			path: '/login',
			name: 'Login',
			component: () => import('@/components/Login'),
			alias: '/adminlogin',
		},
		{
			path: '/',
			name: 'home',
			component: () => import('@/components/Main'),
			children: [{
					path: '/',
					name: 'borrowfind',
					component: () => import('@/views/borrow/BorrowFind'),
					meta: {
						roles: ['admin', 'user'],
						requireAuth: true
					}
				},
				{
					path: '/userlist',
					name: 'userlist',
					component: () => import('@/views/user/UserList'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/useradd',
					name: 'useradd',
					component: () => import('@/views/user/UserAdd'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/userupdate',
					name: 'userupdate',
					component: () => import('@/views/user/UserUpdate'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/borrowlist',
					name: 'borrowlist',
					component: () => import('@/views/borrow/BorrowList'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/borrowadd',
					name: 'borrowadd',
					component: () => import('@/views/borrow/BorrowAdd'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				}, 
				{
					path: '/borrowfind',
					name: 'borrowfind',
					component: () => import('@/views/borrow/BorrowFind'),
					meta: {
						roles: ['admin', 'user'],
						requireAuth: true
					}
				},
				{
					path: '/adminborrowfind',
					name: 'adminborrowfind',
					component: () => import('@/views/borrow/AdminBorrowFind'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/changepwd',
					name: 'changepwd',
					component: () => import('@/components/ChangePwd'),
					meta: {
						roles: ['admin', 'user'],
						requireAuth: true
					}
				},
				{
					path: '/changeinfo',
					name: 'changeinfo',
					component: () => import('@/components/ChangeInfo'),
					meta: {
						roles: ['user'],
						requireAuth: true
					}
				},
				{
					path: '/bookadd',
					name: 'bookadd',
					component: () => import('@/views/book/BookAdd'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/booklist',
					name: 'booklist',
					component: () => import('@/views/book/BookList'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/bookupdate',
					name: 'bookupdate',
					component: () => import('@/views/book/BookUpdate'),
					meta: {
						roles: ['admin'],
						requireAuth: true
					}
				},
				{
					path: '/bookfind',
					name: 'bookfind',
					component: () => import('@/views/book/BookFind'),
					meta: {
						roles: ['admin', 'user'],
						requireAuth: true
					}
				}
			]
		}
	]
})

router.beforeEach((to, from, next) => {
	const isAuthenticated = sessionStorage.getItem("isAuthenticated")
	console.log(isAuthenticated)
	// 去往登录页面
	if (to.path === '/login' || to.path === '/adminlogin') {
		// 已经登录
		if (isAuthenticated == "true") {
			next({
				path: '/'
			})
		} else { // 未登录
			next()
		}
	} else { // 不是去往登录页面
		if (isAuthenticated == "true") {
			var roles = sessionStorage.getItem("roles")
			// 要去往的页面允许当前角色访问，继续
			if (to.meta.roles.indexOf(roles) !== -1) {
				next()
			} else { // 要去往的页面不允许当前角色访问，跳转首页
				next({
					path: '/'
				})
			}
		} else { // 未登录
			next({
				path: '/login'
			})
		}
	}
})
export default router
