# busonline

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).


export default new Router({
  routes: [
    {
      path: '/',
      components: () => import('@components/Main.vue')
    }
  ]
})

# 登录方法
	admin:
		10001                 123123
	user:
		1001				  123123