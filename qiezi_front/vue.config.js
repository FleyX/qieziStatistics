//本地开发时使用，打包部署时请使用环境变量配置
//process.env.VUE_APP_QIEZI_HOST = "http://localhost:8080";
//process.env.VUE_APP_QIEZI_KEY = "aab3cad381f54ca5b7b9abeb2e09320a";
module.exports = {
  devServer: {
    proxy: {
      "/qiezi/api": {
        //这里最好有一个 /
        target: "http://localhost:8088", // 服务器端接口地址
        ws: true, //如果要代理 websockets，配置这个参数
        // 如果是https接口，需要配置这个参数
        changeOrigin: true, //是否跨域
        pathRewrite: {
          "^/qiezi/api": "/qiezi/api",
        },
      },
    },
  },
};
