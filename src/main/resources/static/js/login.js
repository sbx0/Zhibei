var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N,
        login_or_register: true, // 用于登陆与注册之间切换
    },
    components: {},
});

// 登陆与注册之间界面切换
function login_or_register() {
    main.login_or_register = !main.login_or_register;
}

// 语言下拉栏选中
var i18N_config = getCookie("i18N_config")
if (i18N_config != "") {
    $("#i18N_select").val(getCookie("i18N_config"));
}

// 语言切换
$("#i18N_select").change(function () {
    setCookie("i18N_config", $("#i18N_select").val(), 30);
    refresh();
});

$(function () {
    document.title = main.i18N.login;
});