<template>
  <div>
    <el-container class="home-container">
      <el-header class="home-header">
        <span class="home_title">动态菜单 DEMO</span>
      </el-header>
      <el-container>
        <el-aside width="180px" class="home-aside">
          <div style="display: flex;justify-content: flex-start;width: 180px;text-align: left;">
            <el-menu style="background: #ececec;width: 180px;" unique-opened router>
              <!-- 遍历routes数据，根据routes中的数据渲染出el-submenu和el-menu-item -->
              <template v-for="(item,index) in this.routes">
                <el-submenu :key="index" :index="index+''">
                  <template slot="title">
                    <i :class="item.iconCls" style="color: #20a0ff;width: 14px;"></i>
                    <span slot="title">{{ item.name }}</span>
                  </template>
                  <el-menu-item width="180px"
                                style="padding-left: 30px;width: 170px;text-align: left"
                                v-for="child in item.children"
                                :index="child.path"
                                :key="child.path">{{ child.name }}
                  </el-menu-item>
                </el-submenu>
              </template>
            </el-menu>
          </div>
        </el-aside>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <keep-alive>
            <router-view v-if="this.$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!this.$route.meta.keepAlive"></router-view>

          <div v-show="this.$router.currentRoute.name === 'Home'">
            <Head></Head>
            <World></World>
          </div>

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<script>
// @ is an alias to /src
import World from "@/components/World.vue";
import Head from "@/components/Head.vue";

export default {
  name: "Home",
  methods: {},
  data() {
    return {}
  },
  computed: {
    // 在计算属性中返回 routes 数据
    routes() {
      return this.$store.state.routes
    }
  },
  components: {
    World,
    Head
  }
};
</script>

<style lang="stylus">
.home-container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.home-header {
  width: 100%;
  background-color: #20a0ff;
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: content-box;
  padding: 0px;
}

.home-aside {
  background-color: #ECECEC;
}

.home_title {
  color: #fff;
  font-size: 22px;
  display: inline;
  margin-left: 8px;
}

.el-submenu .el-menu-item {
  width: 180px;
  min-width: 175px;
}
</style>
