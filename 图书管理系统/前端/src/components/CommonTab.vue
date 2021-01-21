<template>
  <div class="tabs">
    <!-- 在main.vue中引入CommonTab.vue组件，首页不能关闭，通过for循环遍历标签 -->
    <el-tag :key="tag.label" size="small" v-for="tag in tags" :closable="tag.label !== '首  页'" :disable-transitions="false"
      @close="handleClose(tag)" @click="changeMenu(tag)">
      {{tag.label}}
    </el-tag>
  </div>
</template>

<script>
  import {
    mapState,
    mapMutations
  } from 'vuex'
  export default {
    computed: {
      ...mapState({
        tags: state => state.tabsList
      })
    },
    data() {
      return {
        dynamicTags: ['标签一', '标签二', '标签三'],
        inputVisible: false,
        inputValue: ''
      };
    },
    methods: {
      ...mapMutations({
        close: 'closeTab'
      }),
      handleClose(tag) {
        this.close(tag)
        this.$router.push({path:'/'})
      },
      // 点击tabs切换页面
      changeMenu(item) {
        this.$router.push({
          name: item.name
        })
        this.$store.commit('selectMenu', item)
      }
    }
  }
</script>

<style scoped>
  .tabs {
    padding: 20px;
  }

  .el-tag {
    margin-right: 15px;
  }
</style>
