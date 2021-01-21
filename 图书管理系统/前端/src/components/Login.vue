<template>
  <div class="login" :style="note">
    <p style="color: #fff; margin: 75px 0; font-size: 40px; text-align: center;">{{msg}}</p>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="50px" class="demo-ruleForm">
      <el-form-item label="账号" prop="userId" class="font-test">
        <el-input v-model.number="ruleForm.userId"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass" class="font-test">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  
  export default {
    name: "Login",
    data() {
      var checkuserId = (rule, value, callback) => {
        if (!value) {
          return callback(new Error("账号不能为空"));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error("账号只能为数字"));
          } else {
            callback();
          }
        }, 1000);
      };
      var validatePass = (rule, value, callback) => {
        if (value === "") {
          callback(new Error("请输入密码"));
        } else {
          if (this.ruleForm.checkPass !== "") {
            this.$refs.ruleForm.validateField("checkPass");
          }
          callback();
        }
      };
      return {
        msg: "图书管理系统",
        ruleForm: {
          pass: "",
          userId: ""
        },
        path: location.pathname,
        note: {
          backgroundImage: "url(" + require("../assets/image/timg.jpg") + ")",
          backgroundRepeat: "no-repeat",
          backgroundSize: "100% 100%",
          height: "100%",
          width: "100%",
          position: "fixed",
          margin: "-10px"
        },
        rules: {
          pass: [{
            validator: validatePass,
            trigger: "blur"
          }],
          userId: [{
            validator: checkuserId,
            trigger: "blur"
          }]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            //验证成功，提交
            console.log(this.$route.path)
            let url = ''
            if (this.$route.path === '/adminlogin') {
              url = '/admin/login'
            } else {
              url = '/user/login'
            }
            let paramObj = {
              id: this.ruleForm.userId,
              password: this.$md5(this.ruleForm.pass)
            }
            this.$server.postTest(url, paramObj).then(res => {
              if (res.isOk === true) {
                // 存放数据
                this.$store.isAuthenticated = true
                this.$store.role = res.data.role
                sessionStorage.setItem("isAuthenticated", "true")
                sessionStorage.setItem("roles", res.data.role)
                sessionStorage.setItem("userid",res.data.userid)
                this.$message({
                  message: res.message,
                  type: 'success'
                });
                //设置角色
                this.$store.roles = res.data.role
                this.$router.push({
                  path: '/'
                })
              } else {
                this.$message.error(res.message);
              }
            })
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  };
</script>

<style>
  .font-test {
    color: rgb(19, 18, 18);
  }

  .demo-ruleForm {
    width: 500px;
    margin: 0 auto;
    background-color: #ffffff;
    padding: 40px;
    border-radius: 8px;
  }
</style>
