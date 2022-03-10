本应用是一款轻量级的 UV/PV 流量计数器，可用于各种网站流量统计，支持直接使用以及私有部署使用。

# 快速开始

## 获取 key/secret

1. 直接使用

   点击[应用注册](https://qiezi.fleyx.com/manage/#/application/sign),填写域名信息及验证码获取 key 和 secret
   ![应用注册](https://qiniupic.fleyx.com/blog/202203021523359.png)

2. 私有部署

   参考[部署文档](./deploy.md)，进行服务部署.

   部署完毕后，同样进行应用注册获取 key 和 secret,只是这种情况下是访问你自己的部署环境进行应用注册

## 添加 html 代码段

到这一步假设你已经获取了 key 和 secret

通过如下代码，可添加页面独立 PV 和网站整体 UV/PV

```html
<!-- 页面PV -->
<div style="text-align: center;">
  <div id="qieziStatisticHtmlPost" style="display: none;">当前页面访问次数:<span id="qieziStatisticHtmlPostPv"></span>次&nbsp;</div>
</div>
<!-- 网站整体UV/PV -->
<div style="text-align: center;">
  <div id="qieziStatisticHtmlHostPv" style="display: none;">总访问次数:<span id="qieziStatisticHtmlHostPvValue"></span>次&nbsp;</div>
  <div id="qieziStatisticHtmlHostUv" style="display: none;">&nbsp;总访客数:<span id="qieziStatisticHtmlHostUvValue"></span>人</div>
</div>
<script>
  //如果私有部署才需要填写此项，设置为你的私有部署地址
  //window.qieziStatisticHost = "http://localhost:8080";
  //设置上一节获取到的key
  window.qieziStatisticKey = "d862c12a68ad4d579c6066ac2f064a07";
</script>
<script src="https://qiezi.fleyx.com/qiezijs/1.0/qiezi_statistic.min.js" type="text/javascript"></script>
```

效果如下：

![效果图](https://qiniupic.fleyx.com/blog/202203021545574.png)

## 定制化样式

上面的 html 代码段只是简单的使用实例，可自行定制化样式，只需要保持存在上述**id**存在即可.
