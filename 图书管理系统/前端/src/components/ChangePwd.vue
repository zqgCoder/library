<template>
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="账号" prop="id">
      <el-input v-model="ruleForm.id" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="原始密码" prop="password">
      <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="newPassword">
      <el-input type="password" v-model="ruleForm.newPassword" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPassword">
      <el-input type="password" v-model="ruleForm.checkPassword" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import Qs from 'Qs'
  export default {
    mounted() {
      let query = this.$route.query;
      if (query !== null) {
        console.log(query.id)
        this.ruleForm.id = query.id
      }
    },
    data() {
      //验证原始密码
      var validatePasword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入原来的密码'));
        } else {
          this.$refs.ruleForm.validateField('newPassword');
        }
        callback();
      };
      //验证新密码
      var validateNewPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'));
        } else {
          if (this.ruleForm.newPassword !== '') {
            this.$refs.ruleForm.validateField('checkPassword');
          }
          callback();
        }
      };
      //验证确认密码
      var validateCheckPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.newPassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          id: '',
          password: '',
          checkPassword: '',
          newPassword: ''
        },
        rules: {
          password: [{
            validator: validatePasword,
            trigger: 'blur'
          }],
          newPassword: [{
            validator: validateNewPassword,
            trigger: 'blur'
          }],
          checkPassword: [{
            validator: validateCheckPassword,
            trigger: 'blur'
          }]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let url = ''
            if(sessionStorage.getItem("roles") == "user") {
              url = '/user/changePwd'
            } else {
              url = '/admin/changePwd'
            }
            let paramObj = {
              id: this.ruleForm.id,
              prePwd: this.ruleForm.password,
              newPwd: this.ruleForm.newPassword
            }
            // 发送请求
            var line = sessionStorage.getItem("roles")
            this.$server.postTest(url+'?'+Qs.stringify(paramObj)).then(res => {
              if (res.isOk === true) {
                // 清空数据
               sessionStorage.clear()
                this.$message({
                  message: res.message,
                  type: 'success'
                });
                if(line == 'admin') this.$router.push({path: '/adminlogin'})
                else this.$router.push({path: '/login'})
              } else {
                this.$message.error(res.message);
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style>
</style>
