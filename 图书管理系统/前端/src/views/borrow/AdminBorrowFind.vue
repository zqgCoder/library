<template>
	<div class="AdminBorrowFind">
		<!-- 搜索框 -->
		<div class="state">
			<el-form :inline="true" :model="formInline" class="demo-form-inline">
				<el-form-item label="图书名">
					<el-input style="width: 300px;" v-model="formInline.name" placeholder="请输入要查询的图书名"></el-input>
				</el-form-item>
				<el-form-item label="是否归还">
					<el-select v-model="formInline.isback" placeholder="">
						<el-option label="" value=""></el-option>
						<el-option label="归还" value="1"></el-option>
						<el-option label="未归还" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="作者">
					<el-input v-model="formInline.author" placeholder="请输入要查询的作者"></el-input>
				</el-form-item>
				<el-form-item label="用户编号">
					<el-input v-model="formInline.sid" placeholder="请输入要查询的用户编号"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit">查询</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 表格内容 -->
		<div class="find-table" v-show="showData">
			<template>
				<el-table :data="tableData" border style="width: 100%">
					<el-table-column label="借书编号" prop="id" width="150">
					</el-table-column>
					<el-table-column label="图书编号" prop="bid" width="150">
					</el-table-column>
					<el-table-column label="图书名" prop="name" width="200">
					</el-table-column>
					<el-table-column label="图书种类" prop="kind" width="100">
					</el-table-column>
					<el-table-column label="出版社" prop="publish" width="180">
					</el-table-column>
					<el-table-column label="作者" prop="author" width="100">
					</el-table-column>
					<el-table-column label="价格" prop="price" width="80">
					</el-table-column>
					<el-table-column label="总数量" prop="num" width="80">
					</el-table-column>
					<el-table-column label="当前可借数量" prop="nnum" width="150">
					</el-table-column>
					<el-table-column label="图书描述" prop="description" width="280">
					</el-table-column>
					<el-table-column label="借书时间" prop="btime" width="100">
					</el-table-column>
					<el-table-column label="最迟归还时间" prop="endtime" width="150">
					</el-table-column>
					<el-table-column label="归还时间" prop="backtime" width="80">
					</el-table-column>
					<el-table-column label="应缴金额" prop="money" width="80">
					</el-table-column>
					<el-table-column label="用户编号" prop="sid" width="100">
					</el-table-column>
					<el-table-column label="用户名" prop="username" width="100">
					</el-table-column>
					<el-table-column label="用户性别" prop="gender" width="100">
					</el-table-column>
				</el-table>
			</template>
		</div>
		<div class="find-page" v-show="showData">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
			 :page-sizes="[10, 20, 30, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	export default {
		data() {

			return {
				showData: false,
				formInline: {
					name: '',
					isback: '',
					author: '',
					sid: ''
				},
				currentPage: 1,
				pageSize: 10,
				total: 20,
				tableData: [{
					id: '',
					bid: '',
					name: '',
					kind: '',
					publish: '',
					author: '',
					price: '',
					num: '',
					nnum: '',
					description: '',
					btime: '',
					endtime: '',
					money: '',
					backtime: '',
					isback: '',
					sid: '',
					username: '',
					gender: ''
				}],
			}
		}, //data
		methods: {
			onSubmit() {
				console.log(this.formInline.name, '  onSubmitline')
				let url = "/borrow/AdminFuzzyFind"
				// 添加请求数据
				let paramObj = {
					pageSize: this.pageSize,
					currentPage: this.currentPage,
					name: this.formInline.name,
					isback: this.formInline.isback,
					author: this.formInline.author,
					sid: this.formInline.sid
				}
				//请求成功后的回调--->数据填充
				console.log("Borrow/AdminFuzzyFind -->onsubmit")
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.borrowList
					this.total = res.data.totalData
					this.formInline.name = res.data.name
					this.showData = true
					this.formInline.isback = res.data.isback
					this.formInline.author = res.data.author
					this.formInline.sid = res.data.sid
				})
			},
			// 改变当前页每页显示多少条数据的函数
			handleSizeChange(val) {
				this.pageSize = val
				this.AdminFuzzyFind(val, this.currentPage, this.formInline.name, this.formInline.isback, this.formInline.author, this.formInline.sid)
			},
			handleCurrentChange(val) {
				this.AdminFuzzyFind(this.pageSize, val, this.formInline.name, this.formInline.isback, this.formInline.author, this.formInline.sid)
			},
			AdminFuzzyFind(pageSize, currentPage, name, isback, author, sid) {
				let url = "/borrow/AdminFuzzyFind"
				// 添加请求数据
				let paramObj = {
					pageSize: pageSize,
					currentPage: currentPage,
					name: this.formInline.name,
					isback: this.formInline.isback,
					author: this.formInline.author,
					sid: this.formInline.sid
				}
				//请求成功后的回调--->数据填充
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.borrowList
					console.log(this.tableData)
					this.total = res.data.totalData
					this.formInline.name = res.data.name
					this.formInline.isback = res.data.isback
					this.formInline.author = res.data.author
					this.formInline.sid = res.data.sid
				})
				this.loading = false
			}
		}
	}
</script>

<style>
	demo-table-expand {
		font-size: 0;
	}

	.demo-table-expand label {
		width: 90px;
		color: #99a9bf;
	}

	.demo-table-expand .el-form-item {
		margin-right: 0;
		margin-bottom: 0;
		width: 50%;
	}

	.el-step__icon.is-text {
		border-color: green;
		color: green;
	}

	.el-step.is-center .el-step__description {
		color: inherit;
		hover: green;
	}

	.el-step__description.is-wait {
		padding-top: 4px;
		padding-bottom: 4px;
		padding-left: 2px;
		padding-right: 5px;
		letter-spacing: 3px;
		writing-mode: tb-rl;
	}

	.el-step__head.is-wait {
		width: 40px;
	}

	.find-table {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}
</style>
