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
        query_data: {}
    },
    components: {},
    methods: {},
    create: function () {

    },
});

// 通用删除
function deleteOne(id) {
    if (confirm("确认删除？")) {
        $.ajax({
            type: "get",
            url: "/" + main.table +
                "/delete?id=" + id,
            dataType: "json",
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
    $.ajax({
        type: "get",
        url: "../" + main.table +
            "/list?page=" + main.page +
            "&size=" + main.size +
            "&attribute=" + main.attribute +
            "&direction=" + main.direction,
        dataType: "json",
        success: function (json) {
            var status = json.status;
            if (statusCodeToBool(status)) {
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

// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.admin_message;
});