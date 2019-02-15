var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N,
        roles: {},
        permissions: {},
        role_modal_data: [
            {
                id: 'id',
                name: 'ID',
                type: 'text',
                placeholder: '请输入ID',
            },
            {
                id: 'name',
                name: '名称',
                type: 'text',
                placeholder: '请输入名称',
            },
        ],
        permission_modal_data: [
            {
                id: 'id',
                name: 'ID',
                type: 'text',
                placeholder: '请输入ID',
            },
            {
                id: 'name',
                name: '名称',
                type: 'text',
                placeholder: '请输入名称',
            },
        ],
    },
    components: {
        "base-modal": base_modal,
    },
    methods: {},
    create: function () {

    },
});

getRoles();
getPermissions();

// 更新权限列表
function updatePermissionList() {
    $.ajax({
        type: "get",
        url: "/permission/initialize",
        dataType: "json",
        success: function (json) {
            var status = json.status;
            alert(statusCodeToAlert(status));
        },
    });
}

// 获取角色信息
function getRoles() {
    $.ajax({
        type: "get",
        url: "/role" +
            "/list?page=1" +
            "&size=1000" +
            "&attribute=id" +
            "&direction=ASC",
        dataType: "json",
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
                main.roles = json.objects;
                if (json.objects == null) {
                    alert(i18N.result + i18N.is + i18N.null);
                }
            } else {
                alert(statusCodeToAlert(status))
            }
        },
    });
}

// 获取角色信息
function getPermissions() {
    $.ajax({
        type: "get",
        url: "/permission" +
            "/list?page=1" +
            "&size=1000" +
            "&attribute=id" +
            "&direction=ASC",
        dataType: "json",
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
                main.permissions = json.objects;
                if (json.objects == null) {
                    alert(i18N.result + i18N.is + i18N.null);
                }
            } else {
                alert(statusCodeToAlert(status))
            }
        },
    });
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