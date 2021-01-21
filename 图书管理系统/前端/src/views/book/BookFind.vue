<template>
	<div class="BookFind">
		<!-- 搜索框 -->
		<div class="state">
			<el-form :inline="true" :model="formInline" class="demo-form-inline">
				<el-form-item label="图书名">
					<el-input style="width: 300px;" v-model="formInline.name" placeholder="请输入要查询的图书名"></el-input>
				</el-form-item>
				<el-form-item label="种类名">
					<el-input v-model="formInline.kind" placeholder="请输入要查询的种类"></el-input>
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
					<el-table-column label="图书编号" prop="id" width="150">
					</el-table-column>
					<el-table-column label="图书名" prop="name" width="200">
					</el-table-column>
					<el-table-column label="图书种类" prop="kind" width="80">
					</el-table-column>
					<el-table-column label="出版社" prop="publish" width="180">
					</el-table-column>
					<el-table-column label="作者" prop="author" width="100">
					</el-table-column>
					<el-table-column label="价格" prop="price" width="80">
					</el-table-column>
					<el-table-column label="总数量" prop="num" width="80">
					</el-table-column>
					<el-table-column label="当前可借数量" prop="nnum" width="80">
					</el-table-column>
					<el-table-column label="图书描述" prop="description" width="280">
					</el-table-column>
					<el-table-column prop="option" label="操作" width="150" type="index">
						<template slot-scope="scope">
							<el-button @click="borrowBook(scope.row)" type="primary" size="small">申请借阅</el-button>
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
		created: function() {
			this.fuzzyFind(10, 1, '', '', '')
		}, // created
		data() {
			return {
				showData: false,
				formInline: {
					name: '',
					kind: '',
					author: ''
				},
				currentPage: 1,
				pageSize: 10,
				total: 20,
				tableData: [{
					id: '',
					name: '',
					kind: '',
					publish: '',
					author: '',
					price: '',
					num: '',
					nnum: '',
					description: ''
				}],
			}
		}, //data
		methods: {
			onSubmit() {
				console.log(this.formInline.name, '  onSubmitline')
				let url = "/book/fuzzyFind"
				// 添加请求数据
				let paramObj = {
					pageSize: this.pageSize,
					currentPage: this.currentPage,
					name: this.formInline.name,
					kind: this.formInline.kind,
					author: this.formInline.author
				}
				//请求成功后的回调--->数据填充
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.bookList
					this.total = res.data.totalData
					this.formInline.name = res.data.name
					this.showData = true
					this.formInline.kind = res.data.kind
					this.formInline.author = res.data.author
				})
			},
			// 改变当前页每页显示多少条数据的函数
			handleSizeChange(val) {
				this.pageSize = val
				this.fuzzyFind(val, this.currentPage, this.formInline.name, this.formInline.kind, this.formInline.author)
			},
			handleCurrentChange(val) {
				this.fuzzyFind(this.pageSize, val, this.formInline.name, this.formInline.kind, this.formInline.author)
			},
			fuzzyFind(pageSize, currentPage, name, kind, author) {
				let url = "/book/fuzzyFind"
				// 添加请求数据
				let paramObj = {
					pageSize: pageSize,
					currentPage: currentPage,
					name: this.formInline.name,
					kind: this.formInline.kind,
					author: this.formInline.author
				}
				//请求成功后的回调--->数据填充
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.bookList
					this.total = res.data.totalData
					this.formInline.name = res.data.name
					this.formInline.kind = res.data.kind
					this.formInline.author = res.data.author
				})
				this.loading = false
			},
			borrowBook(val) {
				if (val.nnum < 1) {
					this.$message({
						message: '当前可借阅图书不足，请耐心等待',
						type: 'warning'
					});
					return false
				}
				let url = "/borrow/borrowbook"
				// 添加请求数据
				let paramObj = {
					bid: val.id,
					sid: JSON.parse(sessionStorage.getItem("userid")),
					name: this.formInline.name,
					kind: this.formInline.kind,
					author: this.formInline.author
				}
				//请求成功后的回调--->数据填充
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.bookList
					this.total = res.data.totalData
					this.formInline.name = res.data.name
					this.showData = true
					this.formInline.kind = res.data.kind
					this.formInline.author = res.data.author
					this.$message({
						message: res.message,
						type: 'success'
					});
				})

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
