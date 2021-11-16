<template>
  <div class="login">
    <Head></Head>
    <login-form></login-form>
  </div>
</template>

<script>
// @ is an alias to /src
import LoginForm from "@/components/LoginForm.vue";
import Head from "@/components/Head.vue";

export default {
  name: "Login",
  components: {
    LoginForm,
    Head
  },
  data() {
    return {};
  }
};
</script>

<style lang="stylus">
.login
  font-family Avenir, Helvetica, Arial, sans-serif
  -webkit-font-smoothing antialiased
  -moz-osx-font-smoothing grayscale
  text-align center
  color #2c3e50
  margin-top 60px
</style>
