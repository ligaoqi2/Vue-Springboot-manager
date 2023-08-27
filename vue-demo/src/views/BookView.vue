<template>
  <div style="padding: 10px">

    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <!--      <el-button type="primary">导入</el-button>-->
      <!--      <el-button type="primary">导出</el-button>-->
    </div>

    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>

    <!--    表格-->
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="name" label="名称"/>
      <el-table-column prop="price" label="单价"/>
      <el-table-column prop="author" label="作者"/>
      <el-table-column prop="createTime" label="出版时间"/>
      <el-table-column prop="cover" label="封面">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :preview-src-list="[scope.row.cover]"
          />
        </template>
      </el-table-column>
      <!--      数据库中为下划线的会在前端转换为驼峰, mybatis框架做的-->

      <el-table-column fixed="right" width="150" label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--    分页-->
    <div style="margin: 10px 0">
      <el-pagination
          v-model:current-page="currentPage4"
          v-model:page-size="pageSize4"
          :page-sizes="[5, 10, 20]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

    <!--    弹窗-->
    <el-dialog v-model="dialogVisible" title="提示" width="30%">
      <el-form :model="form" label-width="120px" style="width: 80%">

        <el-form-item label="名称">
          <el-input v-model="form.name"/>
        </el-form-item>

        <el-form-item label="单价">
          <el-input v-model="form.price"/>
        </el-form-item>

        <el-form-item label="作者">
          <el-input v-model="form.author"/>
        </el-form-item>

        <el-form-item label="出版时间">
          <el-date-picker v-model="form.createTime" value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable/>
        </el-form-item>

        <el-form-item label="封面">
          <el-upload ref="upload" action="http://localhost:8085/files/upload" :on-success="filesUploadSuccess">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确认
        </el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: 'BookView',
  components: {},

  data() {
    return {
      // 表单设置
      form: {},

      // 弹窗设置
      dialogVisible: false,

      // 查询
      search: '',

      // 分页设置
      currentPage4: 1,
      pageSize4: 10,
      total: 0,

      // 表格数据
      tableData: []
    }
  },

  created() {
    this.load()
  },

  methods: {
    //文件上传成功
    filesUploadSuccess(res) {
      console.log(res)
      this.form.cover = res.data
    },

    // 查
    load() {
      request.get("/book", {
        params: {
          pageNum: this.currentPage4,
          pageSize: this.pageSize4,
          search: this.search,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records;
        this.total = res.data.total;

      })
    },

    //增
    add() {
      this.dialogVisible = true;
      this.form = {};
      this.$refs['upload'].clearFiles()       //清除历史文件列表
    },
    save() {
      if (this.form.id) {
        // 更新
        request.put("/book", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load()     //修改后重新加载界面
          this.dialogVisible = false
        })
      } else {
        // 新增
        request.post("/book", this.form).then(res => {
          console.log(res)
          this.$message({
            type: "success",
            message: "新增成功"
          })
          this.load()     //新增后重新加载界面
          this.dialogVisible = false
        })
      }
    },

    // 改
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true;
      this.$nextTick(()=>{                    //nextTick 解决调用时元素不存在的错误
        this.$refs['upload'].clearFiles();        //处理未来元素的方法
      })
    },

    // 删
    handleDelete(id) {
      console.log(id)
      request.delete("/book/" + id).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "删除成功"
              })
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
            this.load()
          }
      )
    },

    handleSizeChange(pageSize) {   //改变当前每页的个数触发
      this.pageSize4 = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) { //改变当前页码触发
      this.currentPage4 = pageNum
      this.load()
    },
  }

}
</script>
