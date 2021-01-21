<template>
  <div class="UserList">
    <div class="user-find">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input v-model="formInline.username" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="formInline.gender" placeholder="">
            <el-option label="" value=""></el-option>
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addOne">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格部分 -->
    <div class="user-table"   v-loading="loading">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="id" label="账号" width="80">
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="100">
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="80">
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="180">
        </el-table-column>
        <el-table-column prop="address" label="住址" width="280">
        </el-table-column>
        <el-table-column prop="description" label="个性签名" width="280">
        </el-table-column>
        <el-table-column prop="option" label="操作" width="280" type="index">
          <template slot-scope="scope">
            <el-button @click="editUser(scope.row)" type="primary" size="small">编辑</el-button>
            <el-button @click="deleteUser(scope.row)" type="danger" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页部分 -->
    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="[50, 100, 200, 300]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  export default {
    created: function() {
      this.findAll(100,1,'','')
    },
    data() {
      return {
        pageSize: 100,
        currentPage: 1,
        total: 500,
        tableData: [], //表格数据
        formInline: {
          username: '',
          gender: ''
        },
        loading: true
      }
    }, // data
    methods: {
      editUser(row) {
        console.log(row.id)
        let form = {
          user: row,
        }
        this.$router.push({
          name: 'userupdate',
          query: form
        })
      },
      deleteUser(row) {
        console.log("删除用户" + row.id)
        this.$confirm('是否要删除该用户?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          let url = "/user/delete"
          let paramObj = {
            id: row.id
          }
          this.$server.exam(url, paramObj).then(res => {
            if (res.isOk) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.findAll(this.pageSize, this.currentPage)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleSizeChange(val) {
        this.pageSize = val
        this.findAll(val, this.currentPage)
      },
      handleCurrentChange(val) {
        this.findAll(this.pageSize, val)
      },
      //条件查询
      findAll(pageSize, currentPage, username, gender) {
        this.loading = true
        let url = "/user/findAll"
        // 添加请求数据
        let paramObj = {
          pageSize: pageSize,
          currentPage: currentPage,
          username: this.formInline.username,
          gender: this.formInline.gender
        }
        //请求成功后的回调--->数据填充
        this.$server.exam(url, paramObj).then(res => {
          this.tableData = res.data.userList
          this.total = res.data.totalData
          this.formInline.username = res.data.username
          this.formInline.gender = res.data.gender

        })
        this.loading = false
      },
      onSubmit() {
        console.log('submit!' + this.formInline.username + "------" + this.formInline.gender);
        this.findAll(this.pageSize, this.currentPage, this.formInline.username, this.formInline.gender)
      },
      addOne() {
        this.$router.push({path:'useradd'})
      }
    }
  }
</script>

<style>
  .el-table .cell {
    text-align: center;
  }
  .user-find {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
</style>
