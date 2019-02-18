/**
 * 加载公用的js库
 */
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
// 消息提示 Messenger
document.write("<script src=\"/js/lib/messenger.min.js\"></script>");
document.write("<script src=\"/js/lib/messenger-theme-flat.js\"></script>");
// 加载工具库
document.write("<script src=\"/js/base/tools.js\"></script>");
// 加载i18N国际化文件
document.write("<script src=\"/js/i18N/i18N.js\"></script>");

// 重写alert
function alert(msg, type) {
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom',
        theme: 'flat'
    };
    Messenger().post({
        message: msg,
        type: type,
        hideAfter: 3,
        showCloseButton: true,
    });
}

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