<template>
	<div class="BookAdd">
		<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

			<el-form-item label="图书编号" prop="bid">
				<el-input v-model="ruleForm.bid" type="number"></el-input>
			</el-form-item>
			<el-form-item label="借阅人编号" prop="sid">
				<el-input v-model="ruleForm.sid" type="number"></el-input>
			</el-form-item>
			<el-form-item label="借阅时间" prop="btime">
				<el-date-picker v-model="ruleForm.btime" type="date" placeholder="选择借书日期" :picker-options="pickerOptionsStart">
				</el-date-picker>
			</el-form-item>
			<el-form-item label="归还时间" prop="endtime">
				<el-date-picker v-model="ruleForm.endtime" type="date" placeholder="选择还书日期" :picker-options="pickerOptionsEnd">
				</el-date-picker>
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
					bid: '',
					sid: '',
					btime: '',
					endtime: ''
				},
				pickerOptionsStart: {
					disabledDate: time => {
						let endDateVal = this.ruleForm.endtime;
						if (endDateVal) {
							return time.getTime() > new Date(endDateVal).getTime();
						}
					}
				},
				pickerOptionsEnd: {
					disabledDate: time => {
						let beginDateVal = this.ruleForm.btime;
						if (beginDateVal) {
							return (time.getTime() < new Date(beginDateVal).getTime() - 1 * 24 * 60 * 60 * 1000);
						}
					}
				},
				rules: {
					bid: [{
						required: true,
						message: '请输入图书编号',
						trigger: 'blur'
					}],
					sid: [{
						required: true,
						message: '请输入读者编号',
						trigger: 'blur'
					}],
					btime: [{
						type: 'date',
						required: true,
						message: '请选择借书时间',
						trigger: 'blur'
					}],
					endtime: [{
						type: 'date',
						required: true,
						message: '请选择归还时间',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			submitForm(formName) {

				this.$refs[formName].validate((valid) => {
					if (valid) {
						console.log(this.ruleForm, 'submit borrow' + this.ruleForm.bid)
						let url = "/borrow/add"
						// 添加请求数据
						let paramObj = {
							bid: this.ruleForm.bid,
							sid: this.ruleForm.sid,
							btime: this.ruleForm.btime,
							endtime: this.ruleForm.endtime
						}
						this.$server.postTest(url, paramObj).then(res => {
							this.$message({
								message: res.message,
								type: 'success'
							});
							this.ruleForm = {
								bid: '',
								sid: '',
								btime: '',
								endtime: ''
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
