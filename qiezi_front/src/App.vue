<template>
  <router-view />

  <div  style="">
    <div id="qieziStatisticHtmlHostPv" style="display: inline-block; width: 50%; text-align: right">页面访问次数:<span id="qieziStatisticHtmlHostPvValue"></span>次&nbsp;</div>
    <div id="qieziStatisticHtmlHostUv" style="display: inline-block; width: 50%; text-align: left">&nbsp;总访客数:<span id="qieziStatisticHtmlHostUvValue"></span>人</div>
  </div>
</template>

<script setup>
import { onMounted } from "@vue/runtime-core";

onMounted(async () => {
  let host = process.env.VUE_APP_QIEZI_HOST;
  console.log("qieziHost:" + host);
  if (host && host.trim().length > 0) {
    window.qieziStatisticHost = host;
  }
  let key = process.env.VUE_APP_QIEZI_KEY;
  console.log("qieziKey:" + key);
  if (key && key.trim().length > 0) {
    window.qieziStatisticKey = key;
  }
});
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
