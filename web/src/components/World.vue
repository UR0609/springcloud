<template>
  <div class="World">
    <el-row>
      <el-button type="primary" @click="save">Save</el-button>
      <el-button type="success" @click="update">Update</el-button>
      <el-button type="info" @click="select">Select</el-button>
      <el-button type="danger" @click="del">Delete</el-button>
    </el-row>
    <br/>
    <el-row>
      <el-button type="primary" @click="p">进入P的世界</el-button>
      <el-button type="success" @click="vip">进入VIP的世界</el-button>
      <el-button type="info" @click="svip">进入SVIP的世界</el-button>
      <el-button type="danger" @click="logout">离开世界</el-button>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "World",
  data() {
    return {
      path: this.$route.path
    };
  },
  methods: {
    showMsg(result) {
      if (result.status === 200) {
        this.$message({
          showClose: true,
          message: result.msg,
          type: "success",
          duration: "600"
        });
      } else {
        this.$message({
          showClose: true,
          message: result.msg,
          type: "error",
          duration: "3000"
        });
      }
    },
    save() {
      this.$axios({
        method: "POST",
        url: "/test/save",
        data: {path: this.path},
      }).then(result => {
        this.showMsg(result);
      });
    },
    update() {
      this.$axios({
        method: "PUT",
        url: "/test/update",
        data: {},
      }).then(result => {
        this.showMsg(result);
      });
    },
    select() {
      this.$axios({
        method: "GET",
        url: "/test/select",
        data: {},
      }).then(result => {
        this.showMsg(result);
      });
    },
    del() {
      this.$axios({
        method: "DELETE",
        url: "/test/delete",
        data: {},
      }).then(result => {
        this.showMsg(result);
      });
    },
    p() {
      this.$axios({
        method: "GET",
        url: "/test/p",
        data: {},
      }).then(result => {
        this.showMsg(result);
      });
    },
    vip() {
      this.$axios({
        method: "GET",
        url: "/test/vip",
        data: {},
      }).then(result => {
        this.showMsg(result);
      });
    },
    svip() {
      this.$axios({
        method: "GET",
        url: "/test/svip",
        data: {},
      }).then(result => {
        this.showMsg(result);
      });
    },
    logout() {
      this.$axios({
        method: "POST",
        url: "/sys/logout",
        data: {},
      }).then(result => {
        if (result.status === 200) {
          this.$message({
            showClose: true,
            message: result.msg,
            type: "success",
            duration: "1600"
          });
        } else {
          this.$message({
            showClose: true,
            message: result.msg,
            type: "error",
            duration: "3000"
          });
        }
        this.setToken("");
        this.$router.push({path: "/"});
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus"></style>
