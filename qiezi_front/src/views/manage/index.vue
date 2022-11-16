<template>
  <div>应用管理</div>
</template>

<script setup>
import { onMounted, ref, reactive } from "vue";
import { get, post } from "../util/HttpUtil";
</script>

<style lang="less" scoped>
.title {
  font-size: 2em;
  font-weight: 600;
}
.captchaItem {
  display: flex;
}
</style>
