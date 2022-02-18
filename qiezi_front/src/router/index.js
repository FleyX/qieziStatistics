import { createRouter, createWebHashHistory } from "vue-router";
import Home from "../views/Home.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/application/sign",
    name: "ApplicationSign",
    component: () => import("../views/ApplicationSign"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
