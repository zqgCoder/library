<template>
  <div class="UserAdd">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPassword">
        <el-input type="password" v-model="ruleForm.checkPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="ruleForm.gender" placeholder="请设置用户性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="ruleForm.phone"></el-input>
      </el-form-item>
      <el-form-item label="家庭住址" prop="address">
        <el-input v-model="ruleForm.address"></el-input>
      </el-form-item>
      <el-form-item label="个性签名" prop="description">
        <el-input v-model="ruleForm.description"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">添加</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      var validatePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPassword');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      var validPhone = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('手机号必填'));
        } else if (!(/^1[3456789]\d{9}$/.test(value))) {
          callback(new Error('手机号错误'));
        } else {
          callback();
        }
      }
      return {
        ruleForm: {
          username: '',
          password: '',
          checkPassword: '',
          gender: '男',
          phone: '',
          address: '',
          description: ''
        },
        rules: {
          username: [{
              required: true,
              message: '请输入用户名',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 5,
              message: '长度在 3 到 5 个字符',
              trigger: 'blur'
            }
          ],
          password: [{
            required: true,
            validator: validatePassword,
            trigger: 'blur'
          }],
          checkPassword: [{
            required: true,
            validator: validatePass2,
            trigger: 'blur'
          }],
          gender: [{
            required: true,
            message: '请选择性别',
            trigger: 'change'
          }],
          phone: [{
            type: 'array',
            required: true,
            validator: validPhone,
            trigger: 'blur'
          }],
          address: [{
            required: true,
            message: '请填写家庭住址',
            trigger: 'blur'
          }],
          description: [{
            required: true,
            message: '请写一句话展示自己',
            trigger: 'blur'
          }]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.ruleForm)
            let url = "/user/add"
            // 添加请求数据
            let paramObj = {
              username: this.ruleForm.username,
              password: this.$md5(this.ruleForm.password),
              gender: this.ruleForm.gender,
              phone: this.ruleForm.phone,
              address: this.ruleForm.address,
              description: this.ruleForm.description
            }
            this.$server.postTest(url, paramObj).then(res => {
              this.$message({
                message: '添加成功',
                type: 'success'
              });
              this.ruleForm = {
                username: '',
                password: '',
                checkPassword: '',
                gender: '男',
                phone: '',
                address: '',
                description: ''
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
