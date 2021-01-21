<template>
	<div class="BookList">
		<div class="book-find">
			<el-form :inline="true" :model="formInline" class="demo-form-inline">
				<el-form-item label="图书名">
					<el-input v-model="formInline.name" placeholder="请输入查询的图书名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="addOne">新增</el-button>
				</el-form-item>
			</el-form>
		</div>
		<div class="book-table">
			<template>
				<el-table :data="tableData" border style="width: 100%">
				  <el-table-column prop="id" label="编号" width="150">
				  </el-table-column>
				  <el-table-column prop="name" label="图书名" width="200">
				  </el-table-column>
				  <el-table-column prop="kind" label="种类" width="80">
				  </el-table-column>
				  <el-table-column prop="price" label="出版社" width="180">
				  </el-table-column>
				  <el-table-column prop="author" label="作者" width="100">
				  </el-table-column>
				  <el-table-column prop="price" label="价格" width="80">
				  </el-table-column>
				  <el-table-column prop="num" label="总数量" width="80">
				  </el-table-column>
				  <el-table-column prop="nnum" label="可借数量" width="80">
				  </el-table-column>
				  <el-table-column prop="description" label="图书描述" width="280">
				  </el-table-column>
				  <el-table-column prop="option" label="操作" width="280" type="index">
				    <template slot-scope="scope">
				      <el-button @click="editBook(scope.row)" type="primary" size="small">编辑</el-button>
				      <el-button @click="deleteBook(scope.row)" type="danger" size="small">删除</el-button>
				    </template>
				  </el-table-column>
				</el-table>
			</template>
		</div>
		<!-- 分页部分 -->
		<div class="block">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
			 :page-sizes="[10, 20, 30, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	export default {
		created: function() {
			this.findAll(10, 1, '')
		}, // created
		data() {
			// id name kind publish author price num nnum description
			return {
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
				formInline: {
					name: ''
				},
				total: 3,
				pageSize: 10,
				currentPage: 1
			}
		}, // data
		methods: {
			//条件查询
			findAll(pageSize, currentPage, name) {
				let url = "/book/findAll"
				// 添加请求数据
				let paramObj = {
					pageSize: pageSize,
					currentPage: currentPage,
					name: this.formInline.name
				}
				//请求成功后的回调--->数据填充
				this.$server.exam(url, paramObj).then(res => {
					this.tableData = res.data.bookList
					this.total = res.data.totalData
					this.formInline.name = res.data.name
				})
				this.loading = false
			},
			editBook(row) {
				console.log(row, '编辑图书')
				let form = {
					book: row,
				}
				this.$router.push({
					name: 'bookupdate',
					query: form
				})
			},
			deleteBook(row) {
				console.log("删除用户" + row.id)
				this.$confirm('是否要删除该条图书信息?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					center: true
				}).then(() => {
					let url = "/book/delete"
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
			}, //deleteBusInfo
			onSubmit() {
				console.log('name' + this.formInline.name + 'onSubmit')
				this.findAll(this.pageSize, this.currentPage, this.name)
			}, //onSubmit, 查找
			addOne() {
				console.log('addOne')
				this.$router.push({
					path: 'bookadd'
				})
			},
			handleSizeChange(val) {
				this.pageSize = val
				this.findAll(val, this.currentPage)
			},
			handleCurrentChange(val) {
				this.findAll(this.pageSize, val)
			}
		} // methods
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
</style>
