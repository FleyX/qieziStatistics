import { createApp } from "vue";
import { message, Button, Form, Input, Typography } from "ant-design-vue";
import App from "./App.vue";
import router from "./router";
import "ant-design-vue/dist/antd.css";

const app = createApp(App);
app.use(router).use(Button).use(Form).use(Input).use(Typography).mount("#app");
app.config.globalProperties.$message = message;
window.globalVue = app;
