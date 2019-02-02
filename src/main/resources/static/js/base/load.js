var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {     // 移动终端浏览器版本信息
            trident: u.indexOf('Trident') > -1, // IE内核
            presto: u.indexOf('Presto') > -1, // opera内核
            webKit: u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/), // 是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或uc浏览器
            iPhone: u.indexOf('iPhone') > -1, // 是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, // 是否iPad
            webApp: u.indexOf('Safari') == -1 // 是否web应该程序，没有头部与底部
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
};
// console.log(browser.language);//系统语言
// console.log(browser.versions);//设备信息判断
// 加载公用的js库
// Vue.js 2.5.22
document.write("<script src=\"/js/lib/vue-2.5.22.js\"></script>");
// document.write("<script src=\"/js/lib/vue_dev-2.5.22.js\"></script>");
// jQuery 3.3.1
document.write("<script src=\"/js/lib/jquery-3.3.1.min.js\"></script>");
// popper 1.12.9
document.write("<script src=\"/js/lib/popper-1.12.9.min.js\"></script>");
// // bootstrap 4.0.0
// document.write("<script src=\"/js/lib/bootstrap-4.0.0.min.js\"></script>");
// 加载FontAwesome 5.0.0
document.write("<script src=\"/js/lib/fontawesome-5.0.0.js\"></script>");

// 加载工具库
document.write("<script src=\"/js/base/tools.js\"></script>");
// 加载i18N国际化文件
document.write("<script src=\"/js/i18N/i18N.js\"></script>");
// 加载自定义组件
// 判断是否是移动设备打开
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return null;
}

var no_redirect = getQueryVariable("no_redirect");
if (browser.versions.mobile) {
    if (!no_redirect) {
        document.write("<script src=\"/js/m/component.js\"></script>");
    } else {
        document.write("<script src=\"/js/component.js\"></script>");
    }
} else {
    if (!no_redirect) {
        document.write("<script src=\"/js/component.js\"></script>");
    } else {
        document.write("<script src=\"/js/m/component.js\"></script>");
    }
}