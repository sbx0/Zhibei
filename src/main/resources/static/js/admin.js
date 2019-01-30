var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N,
        not_login: true,
        table_data: {
            user: {
                name: i18N.table.user,
                value: "user",
                data: {},
            },
            role: {
                name: i18N.table.role,
                value: "role",
                data: {},
            },
            permission: {
                name: i18N.table.permission,
                value: "permission",
                data: {},
            },
            certification: {
                name: i18N.table.certification,
                value: "certification",
                data: {},
            },
        },
        query_data: {}
    },
    components: {},
    methods: {},
    create: function () {

    }
});

// 初始化查询
query();

// 切换表
$("#table_select").change(function () {
    query();
});

// 查询表
function query() {
    $.ajax({
        type: "get",
        url: "../" + $("#table_select").val() + "/list",
        dataType: "json",
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
                main.query_data = json.objects;
                if (json.objects == null) {
                    alert(i18N.result + i18N.is + i18N.null);
                }
            } else {
                alert(statusCodeToAlert(status))
            }
        },
        error: function () {
            alert(i18N.network + i18N.alert.error);
        }
    });
}

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
    document.title = main.i18N.admin_message;
});