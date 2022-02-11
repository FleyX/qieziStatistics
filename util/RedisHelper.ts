import { createClient, RedisClientType } from "redis";
import config from "../config";
class RedisHelper {
	public static client: RedisClientType<any>;

	static async create() {
		this.client = await createClient({ url: config.redis.url });
		this.client.set("1","1")
	}
}

export {
	RedisHelper
}