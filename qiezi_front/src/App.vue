<template>
  <router-view />

  <div  style="">
    <div id="qieziStatisticHtmlHostPv" style="display: none; width: 50%; text-align: right">页面访问次数:<span id="qieziStatisticHtmlHostPvValue"></span>次&nbsp;</div>
    <div id="qieziStatisticHtmlHostUv" style="display: none; width: 50%; text-align: left">&nbsp;总访客数:<span id="qieziStatisticHtmlHostUvValue"></span>人</div>
  </div>
</template>

<script setup>
import { onMounted } from "@vue/runtime-core";

</script>

<style lang="less">
html {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
