# docker-compose 部署

1. 首先安装 docker 和 docker-compose
2. 修改.env 文件，配置 mysql，redis 地址，以及访问端口
3. 执行`build.sh`,进行编译
4. 执行`docker-compose up -d`启动服务，启动完毕后通过服务器 ip+第二部配置的访问端口进行访问
5. 更新代码时先执行`build.sh`,再执行`docker-compose restart`重启服务
