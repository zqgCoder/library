<template>
  <header>
    <div class="l-content">
      <!-- 用来收缩左侧导航栏 -->
      <el-button type="primary" icon="el-icon-menu" size="mini" @click="changeState"></el-button>

      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path: current.path}">
          {{current.label}}
        </el-breadcrumb-item>
      </el-breadcrumb>

    </div>
    <div class="r-content">
      <el-dropdown trigger="click">
        <span class="el-dropdown-link" style="color: #303133;">
          <!-- 后期想要动态添加图片，可以更改 -->
          <img src="../assets/image/avator.jpg" style="width: 45px; height: 45x; border-radius: 50%;" />
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-edit" v-if="this.roles == 'user'" @click.native="personCenter">个人中心</el-dropdown-item>
          <el-dropdown-item icon="el-icon-key" @click.native="changePwd">修改密码</el-dropdown-item>
          <el-dropdown-item icon="el-icon-s-release" @click.native="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </header>
</template>

<script>
  import {
    mapState
  } from 'vuex'
  export default {
    computed: {
      current() {
        return this.$store.getters.doneCurrentMenu
      }
    },
    created() {
      this.roles = sessionStorage.getItem("roles")
    },
    data() {
      return {
        roles: ''
      };
    },
    methods: {
      personCenter() {
        let form = {
          id: sessionStorage.getItem("userid")
        }
        this.$router.push({
          path: '/changeinfo',
          query: form,
        })
      },
      changePwd() {
        let form = {
          id: sessionStorage.getItem("userid")
        }
        this.$router.push({
          path: '/changepwd',
          query: form,
        })
      },
      logout() {
        this.$confirm('是否要退出?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          sessionStorage.clear()
          this.$store.isAuthenticated = false
          this.$store.roles = ['upload']
          this.$message({
            type: 'success',
            message: '删除退出!'
          });
          window.location.href="/";
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消退出'
          });
        });
      },
      changeState() {
        console.log(this.$store.state.isCollapse, 'isCollapse')
        this.$store.state.isCollapse === true ? this.$store.state.isCollapse = false : this.$store.state.isCollapse = true
      }
    }
  };
</script>

<style scoped>
  header {
    display: flex;
    height: 100%;
    align-items: center;
    justify-content: space-between;
    /* justify-content: space-between; 两端对齐 */
  }

  .l-content {
    display: flex;
    align-items: center;
    padding-left: 20px;
  }

  .el-button--mini,
  .el-button--mini.is-round {
    margin-right: 50px;
  }


</style>
