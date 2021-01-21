<template>
	<div class="BookFind">
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
					<el-table-column label="当前可借数量" prop="nnum" width="100">
					</el-table-column>
					<el-table-column label="图书描述" prop="description" width="280">
					</el-table-column>
					<el-table-column label="借书时间" prop="btime" width="100">
					</el-table-column>
					<el-table-column label="最迟归还时间" prop="endtime" width="100">
					</el-table-column>
					<el-table-column label="归还时间" prop="backtime" width="80">
					</el-table-column>
					<el-table-column label="应缴金额" prop="money" width="80">
					</el-table-column>
					<el-table-column prop="option" label="操作" width="150" type="index">
						<template slot-scope="scope">
							<el-button @click="borrowBook(scope.row)" type="primary" size="small">申请还书</el-button>
						</template>
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
					author: ''
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
					isback: ''
				}],
			}
		}, //data
		methods: {
			onSubmit() {
				console.log(this.formInline.name, '  onSubmitline')
				let url = "/borrow/fuzzyFind"
				// 添加请求数据
				let paramObj = {
					pageSize: this.pageSize,
					currentPage: this.currentPage,
					name: this.formInline.name,
					isback: this.formInline.isback,
					author: this.formInline.author,
					userid: JSON.parse(sessionStorage.getItem("userid"))
				}
				//请求成功后的回调--->数据填充
				console.log("userid" + JSON.parse(sessionStorage.getItem("userid")))
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.borrowList
					this.total = res.data.totalData
					this.formInline.name = res.data.name
					this.showData = true
					this.formInline.isback = res.data.isback
					this.formInline.author = res.data.author
				})
			},
			// 改变当前页每页显示多少条数据的函数
			handleSizeChange(val) {
				this.pageSize = val
				this.fuzzyFind(val, this.currentPage, this.formInline.name, this.formInline.isback, this.formInline.author)
			},
			handleCurrentChange(val) {
				this.fuzzyFind(this.pageSize, val, this.formInline.name, this.formInline.isback, this.formInline.author)
			},
			fuzzyFind(pageSize, currentPage, name, isback, author) {
				let url = "/borrow/fuzzyFind"
				// 添加请求数据
				let paramObj = {
					pageSize: pageSize,
					currentPage: currentPage,
					name: this.formInline.name,
					isback: this.formInline.isback,
					author: this.formInline.author
				}
				//请求成功后的回调--->数据填充
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.borrowList
					console.log(this.tableData)
					this.total = res.data.totalData
					this.formInline.name = res.data.name
					this.formInline.isback = res.data.isback
					this.formInline.author = res.data.author
				})
				this.loading = false
			},
			borrowBook(val) {
				console.log(val)
				if (val.isback == 1) {
					this.$message({
						message: '已经还书，无需再次还书。',
						type: 'message'
					});
					return false
				}
				// 需要缴费
				if (val.money > 0) {
					this.$confirm('当前书已经逾期，是否要缴费', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'messsage'
					}).then(() => {
						// 开始还书
						let url = "/borrow/backbook"
						// 添加请求数据
						let paramObj = {
							id: val.id,
							bid: val.bid,
							sid: JSON.parse(sessionStorage.getItem("userid")),
							name: this.formInline.name,
							isback: this.formInline.isback,
							author: this.formInline.author
						}
						//请求成功后的回调--->数据填充
						this.$server.exam(url, paramObj).then(res => {
							this.tableData = res.data.borrowList
							console.log(this.tableData)
							this.total = res.data.totalData
							this.formInline.name = res.data.name
							this.formInline.isback = res.data.isback
							this.formInline.author = res.data.author
							this.$message({
								message: res.message,
								type: 'message'
							});
						})
					}).catch(() => {
						this.$message({
							type: 'info',
							message: '已取消还款'
						});
						return false
					})
					
				}
			
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
