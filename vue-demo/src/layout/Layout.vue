<template>
  <div>
    <!--    头部-->
    <Header/>
    <div style="display: flex">
      <!--      侧边栏-->
      <Aside/>
      <!--      内容-->
      <router-view style="flex: 1"/>
    </div>

  </div>
</template>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: "Layout",
  components: {Header, Aside}
}
</script>

<style>

</style>