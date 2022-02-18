
(function () {
  var name = "qieziStatistic9527";
  var callback = name + "CallBack";
  window[callback] = function (a) {
    var hostNode = document.getElementById(name + "Host");
    if (hostNode != null) {
      document.getElementById(name + "HostPv").innerText = a.totalPv;
      document.getElementById(name + "HostUv").innerText = a.totalUv;
      hostNode.style.display = "inline";
    }

    var postNode = document.getElementById(name + "Post");
    if (postNode != null) {
      document.getElementById(name + "PostPv").innerText = a.pagePv;
      postNode.style.display = "inline";
    }
  };
  setTimeout(
    () => {
      var script = document.createElement("script");
      script.type = "text/javascript";
      script.defer = true;
      script.src =
        (window.qieziStatisticHost == undefined ? "https://qiezi.fleyx.com" : window.qieziStatisticHost) +
        "/qiezi/api/application/visit?callBack=" +
        callback +
        "&key=" +
        window.qieziStatisticKey;
      document.getElementsByTagName("head")[0].appendChild(script);
    },
    window.qieziStatisticKey == undefined ? 1000 : 1,
  );
})();
