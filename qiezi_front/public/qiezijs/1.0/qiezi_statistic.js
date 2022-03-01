
(function () {
	var name = "qieziStatisticHtml";
	var callback = name + "CallBack";
	window[callback] = function (a) {
		var hostPvNode = document.getElementById(name + "HostPv");
		if (hostPvNode != null) {
			document.getElementById(name + "HostPvValue").innerText = a.totalPv;
			hostPvNode.style.display = "inline";
		}

		var hostUvNode = document.getElementById(name + "HostUv");
		if (hostUvNode != null) {
			document.getElementById(name + "HostUvValue").innerText = a.totalUv;
			hostUvNode.style.display = "inline";
		}

		var postNode = document.getElementById(name + "Post");
		if (postNode != null) {
			document.getElementById(name + "PostPv").innerText = a.pagePv;
			postNode.style.display = "inline";
		}
	};
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.defer = true;
	var requestHost = "https://qiezi.fleyx.com";
	if (window.qieziStatisticHost !== undefined && window.qieziStatisticHost.trim().length > 0) {
		requestHost = window.qieziStatisticHost;
	} else if (window.CONFIG && window.CONFIG.web_analytics.qieziStatistics.app_host) {
		var temp = window.CONFIG.web_analytics.qieziStatistics.app_host;
		if (temp.trim().length > 0) {
			requestHost = temp;
		}
	}
	var key = null;
	if (window.qieziStatisticKey && window.qieziStatisticKey.trim().length > 0) {
		key = window.qieziStatisticKey;
	} else if (window.CONFIG && window.CONFIG.web_analytics.qieziStatistics.app_key) {
		var temp = window.CONFIG.web_analytics.qieziStatistics.app_key;
		if (temp.trim().length > 0) {
			key = temp;
		}
	}
	console.log(requestHost, key);
	if (!key) {
		return;
	}
	script.src = requestHost +
		"/qiezi/api/application/visit?callBack=" +
		callback +
		"&key=" +
		key;
	document.getElementsByTagName("head")[0].appendChild(script);
})();
