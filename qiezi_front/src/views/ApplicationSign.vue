<template>
  <div class="title">应用注册</div>
  <a-form :model="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" style="width: 80%; margin: 0 auto">
    <a-form-item label="网站名">
      <a-input type="text" v-model:value="form.name" />
    </a-form-item>
    <a-form-item label="域名">
      <a-input type="text" v-model:value="form.host" />
    </a-form-item>
    <a-form-item label="验证码">
      <div class="captchaItem">
        <a-input type="text" v-model:value="form.code" style="flex: 1" />
        <img style="height: 3em; cursor: pointer" :src="imgData" alt="验证码" @click="onCaptchaClick" title="点击刷新" />
      </div>
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" @click="onSubmit">创建</a-button>
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14 }" v-if="keySecret.key" style="text-align: left">
      <div>请注意记录以下的key和secret,key用于uv,pv统计时身份标识,secret用于后续进阶功能的身份认证</div>
      key: <a-typography-text strong>{{ keySecret.key }}</a-typography-text
      ><br />
      secret: <a-typography-text strong>{{ keySecret.secret }}</a-typography-text>
    </a-form-item>
  </a-form>
</template>

<script setup>
import { onMounted, ref, reactive } from "vue";
import { get, post } from "../util/HttpUtil";

let imgData = ref("");
let getCaptcha = async () => "data:image/png;base64," + (await get("/captcha/sign"));
onMounted(async () => {
  imgData.value = await getCaptcha();
});
let onCaptchaClick = async () => (imgData.value = await getCaptcha());

let form = reactive({
  code: "",
  name: "",
  host: "",
});
let keySecret = ref({});
let onSubmit = async () => {
  keySecret.value = reactive(await post("/application/sign", null, form));
  onCaptchaClick();
};
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
