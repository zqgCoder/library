<template>
  <div class="BookUpdate">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="图书编号" prop="id">
        <el-input v-model="ruleForm.id" :disabled="true"></el-input>
      </el-form-item>
	  
      <el-form-item label="图书名" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="种类" prop="kind">
        <el-input v-model="ruleForm.kind"></el-input>
      </el-form-item>
      <el-form-item label="出版社" prop="publish">
        <el-input v-model="ruleForm.publish"></el-input>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="ruleForm.author"></el-input>
      </el-form-item>
	  <el-form-item label="价格" prop="price">
	    <el-input v-model="ruleForm.price"></el-input>
	  </el-form-item>
	  <el-form-item label="总数量" prop="num">
	    <el-input v-model="ruleForm.num"></el-input>
	  </el-form-item>
	  <el-form-item label="可借数量" prop="nnum">
	    <el-input v-model="ruleForm.nnum"></el-input>
	  </el-form-item>
      <el-form-item label="图书描述" prop="description">
        <el-input v-model="ruleForm.description"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    mounted() {
      let query = this.$route.query;
      if (query !== null) {
        console.log("get edit :" + query)
        this.ruleForm = query.book
      }
    },
    data() {
      return {
        ruleForm: {
          id: '',
          name: '',
          kind: '',
          publish: '',
          author: '',
		  price: '',
		  num: '',
		  nnum: '',
          description: ''
        },
        rules: {
          id: [{
            required: true,
            message: '图书编号不能为空',
            trigger: 'blur'
          }],
          name: [{
              required: true,
              message: '请输入图书名',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 20,
              message: '长度在 1 到 20 个字符',
              trigger: 'blur'
            }
          ],
          kind: [{
            required: true,
            message: '请输入图书类别',
            trigger: 'blur'
          },
		  {
			  min: 1,
			  max: 20,
			  message: '长度在 1 到 20 个字符',
			  trigger: 'blur'
		  }],
          publish: [{
            required: true,
            message: '请输入出版社信息',
            trigger: 'blur'
          }],
          author: [{
            required: true,
            message: '请输入作者',
            trigger: 'blur'
          }],
		  price: [{
		    required: true,
		    message: '请输入价格',
		    trigger: 'blur'
		  }],
		  num: [{
			  required: true,
			  message: '请输入图书最大数量',
			  trigger: 'blur'
		  }],
		  nnum: [{
		  			  required: true,
		  			  message: '请输入图书当前可借数量',
		  			  trigger: 'blur'
		  }],
          description: [{
            required: true,
            message: '请描述图书的信息',
            trigger: 'blur'
          }]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let url = "/book/update"
            // 添加请求数据
            let paramObj = {
              id: this.ruleForm.id,
              name: this.ruleForm.name,
              kind: this.ruleForm.kind,
              publish: this.ruleForm.publish,
              author: this.ruleForm.author,
			  price: this.ruleForm.price,
			  num: this.ruleForm.num,
			  nnum: this.ruleForm.nnum,
              description: this.ruleForm.description
            }
            this.$server.postTest(url, paramObj).then(res => {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.ruleForm = {
                name: '',
                kind: '',
                publish: '',
                author: '',
                price: '',
                num: '',
				nnum: '',
                description: ''
              }
              this.$router.push({path:'/'});
            })    //提交
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
