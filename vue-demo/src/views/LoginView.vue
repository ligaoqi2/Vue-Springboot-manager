<template>
  <div style="width: 100%; height: 100vh; background-color: darkslateblue; overflow: hidden">
    <div style="width: 400px; margin: 150px auto">
      <div style="color: #cccccc; font-size: 30px; text-align: center; padding: 30px">欢迎登录</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名">
            <template #prefix>
              <el-icon class="el-input__icon">
                <Avatar/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" show-password>
            <template #prefix>
              <el-icon class="el-input__icon">
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {Avatar, Lock} from '@element-plus/icons-vue'
import request from "@/utils/request";
import router from "@/router";

export default {
  name: "LoginView",
  data() {
    return {
      form: {},
      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: 'blur'},
        ],
        password:[
          {required: true, message: "请输入密码", trigger: 'blur'},
        ]
      }
    }
  },
  components:{
    Avatar,
    Lock,
  },
  methods: {
    login(){
      // 合法才发送请求
      this.$refs['form'].validate((valid) => {
        if(valid){
          // 发送请求
          request.post("/user/login", this.form).then(res => {
            if(res.code === '0'){
              this.$message({
                type: 'success',
                message: '登录成功'
              })
              this.$router.push('/')  // 登录成功页面跳转
            }else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>

</style>