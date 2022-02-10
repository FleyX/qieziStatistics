import koa from "koa";
import Router from "koa-router";
import koaBody from "koa-body";
import * as path from "path";
import RouterMW from "./middleware/controllerEngine";

import config from "./config";
import handleError from "./middleware/handleError";
import init from "./middleware/init";
import SqliteUtil from './util/SqliteHelper';
import log from './util/LogUtil';
import { MysqlUtil } from "./util/MysqlHelper";


log.info(config);
const app = new koa();

let router = new Router({
	prefix: config.urlPrefix
});

app.use(require('koa-static')(path.join(config.rootPath, 'static')));

//表单解析
app.use(koaBody(config.bodyLimit));
//请求预处理
app.use(init);
//错误处理
app.use(handleError);

app.use(RouterMW(router, path.join(config.rootPath, "dist/api")));
(async () => {
	//初始化sqlite
	if (config.sqlite.enable) {
		await SqliteUtil.createPool();
	}
	//初始化mysql
	if (config.mysql.enable) {
		await MysqlUtil.createPool();
	}
	app.listen(config.port);
	log.info(`server listened `, config.port);
})();

app.on("error", (error) => {
	console.error(error);
})
