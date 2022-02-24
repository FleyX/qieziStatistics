#!/bin/bash
# 配置统计key
process.env.VUE_APP_QIEZI_HOST = "https://fleyx.com";
process.env.VUE_APP_QIEZI_KEY = "aab3cad381f54ca5b7b9abeb2e09320a";

base=$(cd "$(dirname "$0")";pwd)
# 前端打包
docker run -it --rm --user ${UID} -v $base/qiezi_front:/opt/front node:lts-buster-slim  bash -c "cd /opt/front &&   yarn --registry https://registry.npm.taobao.org && yarn build"
# 后端打包
docker run -it --rm --user ${UID} -v $base/data/maven/mavenRep:/var/maven/.m2: -v $base/data/maven/settings.xml:/usr/share/maven/conf/settings.xml -v $base/qieziBackend:/code maven:3-openjdk-11-slim  bash -c "cd /code && mvn clean install"

