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
	{
		path: "/download",
		name: "DataDownload",
		component: () => import("../views/download"),
	},
];

const router = createRouter({
	history: createWebHashHistory(),
	base: "manage",
	routes,
});

export default router;
