import * as path from 'path';

//后台所在绝对路径
const rootPath = path.resolve(__dirname, '..');

let config = {
	rootPath,
	port: process.env.PORT ? parseInt(process.env.PORT) : 8089,
	urlPrefix: '/qiezi/api',
	//是否为windows平台
	isWindows: process.platform.toLocaleLowerCase().includes("win"),
	//redis相关配置
	redis: {
		enable: true,
		url: "redis://localhost:6379"
	},
	//sqlite相关配置
	sqlite: {
		enable: false, //是否启用sqlite
		//相对于项目根目录
		filePath: "database.db",
		//sql存放地址，用于执行sql
		sqlFolder: "sqliteSqls"
	},
	//mysql相关配置
	mysql: {
		enable: true, //是否启用mysql
		sqlFolder: "mysqlSqls",
		connection: {
			host: "localhost",
			port: 3306,
			user: "root",
			password: "123456",
			database: "qiezi",
		}

	},
	bodyLimit: {
		formLimit: '2mb',
		urlencoded: true,
		multipart: true,
		formidable: {
			uploadDir: path.join(rootPath, 'files', 'temp', 'uploads'),
			keepExtenstions: true,
			maxFieldsSize: 1024 * 1024
		}
	}
};

export default config;
