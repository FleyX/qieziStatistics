#!/bin/bash
# 替换为自己的部署地址
export VUE_APP_QIEZI_HOST="http://localhost:8080";
# 替换为自己申请的key
export VUE_APP_QIEZI_KEY="d862c12a68ad4d579c6066ac2f064a07";

base=$(cd "$(dirname "$0")";pwd)
#
# 前端打包
docker run  --rm --user ${UID} -e VUE_APP_QIEZI_HOST=${VUE_APP_QIEZI_HOST} -e VUE_APP_QIEZI_KEY=${VUE_APP_QIEZI_KEY}   -v $base/qiezi_front:/opt/front node:lts-slim  bash -c "cd /opt/front &&   yarn --registry https://registry.npm.taobao.org && yarn build"
# 后端打包
docker run  --rm --user ${UID} -v $base/data/maven/mavenRep:/var/maven/.m2: -v $base/data/maven/settings.xml:/usr/share/maven/conf/settings.xml -v $base/qieziBackend:/code maven:3-openjdk-11-slim  bash -c "cd /code && mvn clean install"

