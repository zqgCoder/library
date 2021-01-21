<template>
	<div class="BookAdd">
		<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

			<el-form-item label="书名" prop="name">
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
			<el-form-item label="图书信息" prop="description">
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
			return {
				ruleForm: {
					name: '',
					kind: '',
					publish: '',
					author: '',
					price: '',
					num: '',
					nnum: '',
					describe: ''
				},
				rules: {
					name: [{
							required: true,
							message: '请输入图书名',
							trigger: 'blur'
						},
						{
							min: 2,
							max: 10,
							message: '长度在 2 到 20 个字符',
							trigger: 'blur'
						}
					],
					kind: [{
						required: true,
						message: '请输入图书类别',
						trigger: 'blur'
					}],
					publish: [{
						required: true,
						message: '请添加出版社',
						trigger: 'blur'
					}],
					author: [{
						required: true,
						message: '请填写图书作者',
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
						require: true,
						message: '请输入图书当前可借阅图书数量',
						trigger: 'blur'
					}],
					description: [{
						require: true,
						message: '请输入图书信息的描述',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						console.log(this.ruleForm, 'submit book' + this.ruleForm.name)
						let url = "/book/add"
						// 添加请求数据
						// id name kind publish author price num nnum description
						let paramObj = {
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
								message: res.message,
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
  .el-table .cell {
    text-align: center;
  }
  .BookAdd {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
</style>
