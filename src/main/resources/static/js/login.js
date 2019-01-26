var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N,
        login_or_register: true, // 用于登陆与注册之间切换
    },
    components: {},
    methods: {
        login: function () {
            var check_username = checkStrIsIllegal($("#login_username").val());
            // 检测用户名是否合法
            if (check_username !== "") {
                alert(i18N.username + check_username);
                return false;
            }
            var register_password_str = $("#login_password").val();
            var check_password = checkNullStr(register_password_str);
            // 检测密码是否为空
            if (check_password) {
                alert(i18N.password + i18N.alert.empty);
                return false;
            }
            $.ajax({
                type: "post",
                url: "../user/login",
                data: $("#login_form").serialize(),
                dataType: "json",
                success: function (json) {
                    var status = json.status;
                    if (statusCodeToBool(status)) {

                    } else {

                    }
                },
                error: function () {
                    alert(i18N.network + i18N.alert.error);
                }
            });
        },
        register: function () {
            var check_username = checkStrIsIllegal($("#register_username").val());
            // 检测用户名是否合法
            if (check_username !== "") {
                alert(i18N.username + check_username);
                return false;
            }
            var register_password_str = $("#register_password").val();
            var check_password = checkNullStr(register_password_str);
            // 检测密码是否为空
            if (check_password) {
                alert(i18N.password + i18N.alert.empty);
                return false;
            }
            var register_r_password_str = $("#register_r_password").val();
            var check_r_password = checkNullStr(register_r_password_str);
            // 检测确认密码是否为空
            if (check_r_password) {
                alert(i18N.confirm + i18N.password + i18N.alert.empty);
                return false;
            } else if (register_r_password_str !== register_password_str) {
                // 两次密码不匹配
                alert(i18N.confirm + i18N.password + i18N.alert.error);
                return false;
            }
            $.ajax({
                type: "post",
                url: "../user/add",
                data: $("#register_form").serialize(),
                dataType: "json",
                success: function (json) {
                    var status = json.status;
                    if (statusCodeToBool(status)) {

                    } else {

                    }
                },
                error: function () {
                    alert(i18N.network + i18N.alert.error);
                }
            });
        }
    },
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

// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.login;
});