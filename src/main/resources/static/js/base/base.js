// 初始化用户信息
get_info();

// 检测登陆状态
function get_info() {
    $.ajax({
        type: "get",
        url: "/user/info",
        dataType: "json",
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
                main.user = json.user;
                main.not_login = false;
            } else {
                main.not_login = true;
            }
        },
        error: function () {
            alert(i18N.network + i18N.alert.error);
        }
    });
}

// 语言下拉栏选中
var i18N_config = getCookie("i18N_config")
if (i18N_config != "") {
    $("#i18N_select").val(getCookie("i18N_config"));
}
// 语言切换
$("#i18N_select").on('change', function () {
    setCookie("i18N_config", $("#i18N_select").val(), 30);
    refresh();
});
// 搜索框取消选中状态
$("#search_input_nav_bar").on('blur', function () {
    main.search_result_show = "none";
    main.search_list_show = "none"
});
// 搜索框选中
$("#search_input_nav_bar").on('focus', function () {
    main.search_result_show = "block";
});
// 搜索框文字改变
$("#search_input_nav_bar").on('input', function () {
    if ($("#search_input_nav_bar").val() === "") {
        main.search_list_show = "none";
    } else {
        main.search_list_show = "block";
    }
});
// 导航条点击
$("#nav_bar_header_button").on('click', function () {
    $("#navigation").toggle();
});