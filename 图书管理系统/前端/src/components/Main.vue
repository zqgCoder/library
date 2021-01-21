<template>
  <el-container style="height: 100%">
    <el-aside style="width: 200px;">
      <common-aside></common-aside>
    </el-aside>
    <el-container>
      <el-header>
        <common-header></common-header>
      </el-header>
      <el-main class="bk">
        <div class="con">
          <div class="panel">
            <common-tab></common-tab>
            <router-view />
          </div>
        </div>
      </el-main>
      <el-footer>
        <common-footer></common-footer>
      </el-footer>
    </el-container>

  </el-container>
</template>

<script>
  import CommonHeader from "./CommonHeader.vue"
  import CommonAside from './CommonAside.vue'
  import CommonFooter from './CommonFooter.vue'
  import CommonTab from './CommonTab.vue'
  export default {
    name: "Main",
    components: {
      CommonHeader,
      CommonAside,
      CommonFooter,
      CommonTab
    }
  };
</script>

<style scoped>
  .el-header {
    background-color: #FFFFFF;
  }

  .bk {
    padding: 0px !important;
    background-color: #f1f2f6;
  }

  .con {
    background-color: #f1f2f6;
    padding: 10px;
  }

  .panel {
    background-color: white;
    padding: 10px;
  }
</style>
