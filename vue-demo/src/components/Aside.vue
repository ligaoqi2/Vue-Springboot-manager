<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        default-active="path"
        router
        class="el-menu-vertical-demo">
      <el-sub-menu index="1">
        <template #title>系统管理</template>
        <el-menu-item index="/user">用户管理</el-menu-item>
      </el-sub-menu>

      <el-menu-item index="/book">书籍管理</el-menu-item>

    </el-menu>
  </div>
</template>

<script>

export default {
  name: "Aside",
  data(){
    return{
      path: this.$router.path   // 设置默认高亮的菜单
    }
  },
  created() {
  }
}
</script>

<style scoped>

</style>