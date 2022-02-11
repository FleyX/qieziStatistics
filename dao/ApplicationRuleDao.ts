import ErrorHelper from "../util/ErrorHelper";
import SqliteHelper from "../util/SqliteHelper";

export default class ApplicationRuleDao {
	/**
	 * 查询所有
	 * @param obj 
	 * @returns 
	 */
	static async getAll(): Promise<Array<any>> {
		let res = await SqliteHelper.pool.all('select id,createdDate,updatedDate,name,comment,content from application_rule');
		return res;
	}


}