<template>
  <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" background-color="#00152a"
    text-color="#fff" active-text-color="#2592fb" :collapse="this.$store.state.isCollapse">
    <el-menu-item :index="item.path" v-for="item in noChildren" :key="item.path" @click="clickMenu(item)">
      <i :class="'el-icon-'+item.icon"></i>
      <span slot="title">{{item.label}}</span>
    </el-menu-item>
    <el-submenu index="index" v-for="(item,index) in hasChildren" :key="index">
      <template slot="title">
        <i :class="'el-icon-'+item.icon"></i>
        <span>{{item.label}}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item :index="subItem.path" @click="clickMenu(subItem)" v-for="(subItem,subIndex) in item.children"
          :key="subIndex">{{subItem.label}}</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
  export default {
    computed: {
      noChildren() {
        return this.asideMenu.filter(item => item.meta.roles.indexOf(sessionStorage.getItem("roles")) !== -1)
        //return this.asideMenu.filter(item => !item.children)
      },
      //判断是否含有二级子菜单
      hasChildren() {
        return this.asideMenu.filter(item => item.children)
      }
    },
    data() {
      return {
        asideMenu: [{
            path: '/',
            label: '首  页',
            name: 'home',
            icon: 's-home',
            meta: {
              roles: ['admin', 'user'],
            }
          },
          {
            path: '/bookfind',
            label: '图书查询',
            name: 'bookfind',
            icon: 'location-outline',
            meta: {
              roles: ['admin', 'user'],
            }
          },
			{
			  path: '/borrowfind',
			  label: '借阅查询',
			  icon: 'location-outline',
			  name: 'borrowfind',
			  meta: {
			    roles: ['user'],
			  }
		   },
			 {
			   path: '/adminborrowfind',
			   label: '借阅查询',
			   icon: 'location-outline',
			   name: 'adminborrowfind',
			   meta: {
			     roles: ['admin'],
			   }
			  },
			{
			  path: '/userlist',
			  label: '用户管理',
			  icon: 'user-solid',
			  name: 'userlist',
			  meta: {
			    roles: ['admin'],
			  }
			},
          {
            path: '/booklist',
            label: '图书管理',
            icon: 'user-solid',
            name: 'booklist',
            meta: {
              roles: ['admin'],
            }
          },
          {
            path: '/borrowlist',
            label: '借阅管理',
            icon: 'truck',
            name: 'borrowlist',
            meta: {
              roles: ['admin'],
            }
          }
        ] //aside
      }
    }, //data
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      // 点击标签后，把label添加到vuex的CurrentMenu中
      clickMenu(item) {
        this.$router.push({
          name: item.name
        })
        this.$store.commit('selectMenu', item)
      }
    }
  }
</script>


<style scoped>
  .el-menu {
    height: 100%;
    border: none;
  }
</style>
