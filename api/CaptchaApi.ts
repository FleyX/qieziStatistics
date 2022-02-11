import { Context } from "koa";
import { create } from "svg-captcha";
import { v4 as uuid } from 'uuid';

import { RedisHelper } from '../util/RedisHelper';
const router = {};

/**
 * 获取验证码
 */
router["GET /captcha"] = async function (ctx: Context) {
	let key: string = uuid().replaceAll("-", "");
	let obj = create();
	await RedisHelper.client.set(key, obj.text);
	ctx.body = {
		key,
		data: obj.data
	};

};

export default router;
