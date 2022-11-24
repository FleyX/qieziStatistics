<template>
  <div class="title">数据下载</div>
  <a-form :model="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" style="width: 80%; margin: 0 auto">
    <a-form-item label="key">
      <a-input type="text" v-model:value="form.key" />
    </a-form-item>
    <a-form-item label="secret">
      <a-input type="text" v-model:value="form.secret" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">下载</a-button>
    </a-form-item>
  </a-form>
  <a id="download" :href="'/download?key=' + form.key + '&secret=' + form.secret" download class="download-a">download</a>
</template>

<script setup>
import { ref, reactive } from "vue";
import { get, post } from "../../util/HttpUtil";
import { message } from "ant-design-vue";

let form = reactive({
  key: "",
  secret: "",
});
let keySecret = ref({});
let onSubmit = async () => {
  let exist = await post("/application/check", null, form);
  if (exist) {
    document.getElementById("download").click();
  } else {
    message.error("key,secret不存在");
  }
};
</script>

<style lang="less" scoped>
.title {
  font-size: 2em;
  font-weight: 600;
  margin-bottom: 2em;
}
.download-a {
  position: fixed;
  left: -1000px;
}
</style>
