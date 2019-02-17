var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N,
        not_login: true,
        table: "user",
        index: 1,
        page: 1,
        size: 10,
        attribute: "id",
        direction: "ASC",
        total_pages: 0,
        total_elements: 0,
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
            file: {
                name: i18N.table.file,
                value: "file",
                data: {},
            },
            log: {
                name: i18N.table.log,
                value: "log",
                data: {},
            },
        },
        query_data: {},
        attribute_data: {},
        modal_data: [],
    },
    components: {
        "base-modal": base_modal,
    },
    methods: {
        update: function (id) {
            updateOne(id);
        }
    },
    create: function () {

    },
});

function bind() {
    // 取消之前的绑定
    $("#" + main.table + "ModalSubmitBtn").unbind();
    // 绑定模态框提交按钮点击事件
    $("#" + main.table + "ModalSubmitBtn").on('click', function () {
        saveOne(main.table + "_form");
    });
}

// 通用拼装
function build(data) {
    main.modal_data = [];
    var modal_data = [];
    for (var i = 0; i < main.attribute_data.length; i++) {
        var attribute = main.attribute_data[i];
        if (attribute.name === 'introduction') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: data[attribute.name],
                type: 'textarea',
            };
        } else if (attribute.name === 'email') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: data[attribute.name],
                type: 'email',
            };
        } else if (attribute.name === 'id') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: data[attribute.name],
                type: 'text',
                readonly: 'readonly'
            };
        } else if (attribute.type === 'String') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: data[attribute.name],
                type: 'text'
            };
        } else if (attribute.type === 'Date') {
            var time = "";
            if (data[attribute.name] != null) {
                time = Format(getDate(data[attribute.name].toString()), "yyyy-MM-dd")
            }
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: time,
                type: 'date'
            };
        } else if (attribute.type === 'Double' || attribute.type === 'Integer') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: data[attribute.name],
                type: 'number'
            };
        } else if (attribute.type === 'Boolean') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: data[attribute.name],
                type: 'checkbox',
            };
        } else if (attribute.type === 'List') {
            var time = "";
            var ids = [];
            if (data[attribute.name] != null) {
                for (var j = 0; j < data[attribute.name].length; j++) {
                    ids.push("" + data[attribute.name][j].id + "");
                }
            }
            getData("permission");
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                selected: ids,
                options: main["table_data"]["permission"]["data"],
                multiple: "multiple",
                type: 'select',
            };
        } else if (
            attribute.type === 'Role'
            || attribute.type === 'User'
            || attribute.type === 'Permission'
            || attribute.type === 'Certification'
            || attribute.type === 'Log'
            || attribute.type === 'UploadFile'
        ) {
            getData(attribute.type.toLowerCase());
            if (data[attribute.name] != null) data[attribute.name] = data[attribute.name]["id"];
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                selected: data[attribute.name],
                options: main["table_data"][attribute.type.toLowerCase()]["data"],
                type: 'select',
            };
        } else {
            modal_data[i] = {
                id: attribute.name,
                name: attribute.name,
                value: data[attribute.name],
                type: 'text'
            };
        }
    }
    main.modal_data = modal_data;
    bind();
}

// 通用保存
function saveOne(form_name) {
    $.ajax({
        type: "post",
        url: "/" + main.table + "/add",
        data: $("#" + form_name).serialize(),
        dataType: "json",
        async: false,
        success: function (json) {
            var status = json.status;
            alert(statusCodeToAlert(status));
            if (statusCodeToBool(status)) {
                query();
            }
            return false;
        },
        error: function () {
            alert(i18N.network + i18N.alert.error);
            return false;
        }
    });
}

// 通用删除
function deleteOne(id) {
    if (confirm("确认删除？")) {
        $.ajax({
            type: "get",
            url: "/" + main.table +
                "/delete?id=" + id,
            dataType: "json",
            async: false,
            success: function (json) {
                var status = json.status;
                if (statusCodeToBool(status)) {
                    query();
                }
                alert(statusCodeToAlert(status))
            },
            error: function () {
                alert(i18N.network + i18N.alert.error);
            }
        });
    }
}

// 通用修改
function updateOne(id) {
    var object;
    $.ajax({
        type: "get",
        url: "/" + main.table +
            "/" + id,
        dataType: "json",
        async: false,
        success: function (json) {
            object = json.object;
        },
        error: function () {
            alert(i18N.network + i18N.alert.error);
        }
    });
    build(object);
}

// 从链接获取参数
var page = getQueryVariable("page");
if (page != null) {
    main.page = page;
}
var size = getQueryVariable("size");
if (size != null) {
    main.size = size;
    $("#page_size_select").val(size);
}
var table = getQueryVariable("table");
if (table != null) {
    main.table = table;
    $("#table_select").val(table);
}
var attribute = getQueryVariable("attribute");
if (attribute != null) {
    main.attribute = attribute;
    $("#table_sort").val(attribute);
}
var direction = getQueryVariable("direction");
if (direction != null) {
    main.direction = direction;
    $("#table_sort_direction").val(main.direction);
}

// 初始化查询
query();
// 语言下拉栏选中
var i18N_config = getCookie("i18N_config")
if (i18N_config != "") {
    $("#i18N_select").val(getCookie("i18N_config"));
}
// 切换表
$("#table_select").change(function () {
    main.attribute = "id";
    main.page = 1;
    main.table = $("#table_select").val();
    main.size = $("#page_size_select").val();
    $("#table_sort").val("id");
    query();
});
// 语言切换
$("#i18N_select").change(function () {
    setCookie("i18N_config", $("#i18N_select").val(), 30);
    refresh();
});

// 上一页
function prev_query() {
    main.page = main.page - 1;
    query();
}

// 下一页
function next_query() {
    main.page = main.page + 1;
    query();
}

// 查询表
function ajax_query() {
    main.page = 1;
    main.size = $("#page_size_select").val();
    query();
}

// 查询表
function query() {
    var attribute = $("#table_sort").val();
    if (attribute != null) {
        main.attribute = attribute;
    }
    var direction = $("#table_sort_direction").val();
    if (direction != null) {
        main.direction = direction;
    }
    if (main.page == null) {
        main.page = 1;
    }
    if (main.page < 1) {
        main.page = 1;
    }
    if (main.size == null) {
        main.size = 10;
    }
    if (main.size < 1 || main.size > 1000) {
        main.size = 10;
    }
    // 获取表头
    $.ajax({
        type: "get",
        url: "/" + main.table + "/attribute",
        dataType: "json",
        async: false,
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
                main.attribute_data = json.attributes;
            } else {
                alert(statusCodeToAlert(status));
            }
        },
        error: function () {
            alert(i18N.network + i18N.alert.error);
        }
    });
    // 获取数据
    $.ajax({
        type: "get",
        url: "/" + main.table +
            "/list?page=" + main.page +
            "&size=" + main.size +
            "&attribute=" + main.attribute +
            "&direction=" + main.direction,
        dataType: "json",
        async: false,
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
                main["table_data"][main.table]["data"] = json.objects;
                main.query_data = json.objects;
                main.page = json.page;
                main.size = json.size;
                main.total_pages = json.total_pages;
                main.total_elements = json.total_elements;
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

// 获取某张表的前9999条数据
function getData(table) {
    $.ajax({
        type: "get",
        url: "/" + table +
            "/list?page=1" +
            "&size=9999",
        dataType: "json",
        async: false,
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
                main["table_data"][table]["data"] = json.objects;
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

// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.admin_message;
});