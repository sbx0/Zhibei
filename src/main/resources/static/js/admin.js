/**
 * 后台管理
 * admin.html 的 js
 *
 * @param json 一般是Ajax获得到的json字符串
 * @param json.object 通用对象json字符串
 * @param json.objects 通用对象列表json字符串
 */
// Vue.js
var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N, // 国际化配置读取
        not_login: true, // 是否登陆
        table: "user", // 默认表
        index: 1, // 用于分页显示逻辑
        page: 1, // 当前页数
        size: 10, // 每页条数
        attribute: "id", // 默认按编号属性排序
        direction: "ASC", // 默认正序
        total_pages: 0, // 结果一共有多少页
        total_elements: 0, // 一共有多少结果
        query_data: {}, // 查询结果
        attribute_data: {}, // 表头数据
        modal_data: [], // 用于模版显示的数据
        // 表数据
        table_data: {
            user: {name: i18N.table.user, value: "user", data: {}},
            role: {name: i18N.table.role, value: "role", data: {}},
            permission: {name: i18N.table.permission, value: "permission", data: {}},
            certification: {name: i18N.table.certification, value: "certification", data: {}},
            file: {name: i18N.table.file, value: "file", data: {}},
            log: {name: i18N.table.log, value: "log", data: {}},
            article: {name: i18N.table.article, value: "article", data: {}},
            comment: {name: i18N.table.comment, value: "comment", data: {}},
            category: {name: i18N.table.category, value: "category", data: {}},
            demand: {name: i18N.table.demand, value: "demand", data: {}},
            tag: {name: i18N.table.tag, value: "tag", data: {}},
            verify: {name: i18N.table.verify, value: "verify", data: {}},
            message: {name: i18N.table.message, value: "message", data: {}},
            alipay: {name: i18N.table.alipay, value: "alipay", data: {}},
            product: {name: i18N.table.product, value: "product", data: {}},
            wallet: {name: i18N.table.wallet, value: "wallet", data: {}},
        },
    },
    components: {
        "base-modal": base_modal // 基本模态框模版
    },
    methods: {
        // 修改某项
        update: function (id) {
            updateOne(id);
        }
    },
    create: function () {

    }
});

// 通用拼装模版数据
function build(data) {
    main.modal_data = [];
    var modal_data = [];
    for (var i = 0; i < main.attribute_data.length; i++) {
        var attribute = main.attribute_data[i];
        var attribute_name = '';
        if (i18N["attribute"][main.table] == undefined) {
            attribute_name = attribute.name;
        } else {
            if (i18N["attribute"][main.table][attribute.name] == undefined) {
                attribute_name = attribute.name;
            } else {
                attribute_name = i18N["attribute"][main.table][attribute.name];
            }
        }
        if (
            attribute.type === 'Role'
            || attribute.type === 'User'
            || attribute.type === 'Permission'
            || attribute.type === 'Certification'
            || attribute.type === 'Log'
            || attribute.type === 'UploadFile'
            || attribute.type === 'Article'
            || attribute.type === 'Comment'
            || attribute.type === 'Category'
            || attribute.type === 'Tag'
            || attribute.type === 'Demand'
            || attribute.type === 'Verify'
            || attribute.type === 'Message'
            || attribute.type === 'Alipay'
            || attribute.type === 'Product'
            || attribute.type === 'Wallet'
        ) {
            getData(attribute.type.toLowerCase());
            if (data[attribute.name] != null) data[attribute.name] = data[attribute.name]["id"];
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                selected: data[attribute.name],
                options: main["table_data"][attribute.type.toLowerCase()]["data"],
                type: 'select'
            };
        } else if (attribute.name === 'introduction' || attribute.name === 'content') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                value: data[attribute.name],
                type: 'textarea'
            };
        } else if (attribute.name === 'email') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                value: data[attribute.name],
                type: 'email'
            };
        } else if (attribute.name === 'id') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                value: data[attribute.name],
                type: 'text',
                readonly: 'readonly'
            };
        } else if (attribute.type === 'String') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
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
                name: attribute_name,
                value: time,
                type: 'date'
            };
        } else if (attribute.type === 'Double' || attribute.type === 'Integer') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                value: data[attribute.name],
                type: 'number'
            };
        } else if (attribute.type === 'Boolean') {
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                value: data[attribute.name],
                type: 'checkbox'
            };
        } else if (attribute.type === 'List') {
            var ids = [];
            if (data[attribute.name] != null) {
                for (var j = 0; j < data[attribute.name].length; j++) {
                    ids.push("" + data[attribute.name][j].id + "");
                }
            }
            var table = ''
            switch (attribute.name) {
                case 'tags':
                    table = 'tag'
                    break
                case 'permissions':
                    table = 'permission'
                    break
                case 'products':
                    table = 'product'
                default:
            }
            getData(table);
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                selected: ids,
                options: main["table_data"][table]["data"],
                multiple: "multiple",
                type: 'select'
            };
        } else {
            modal_data[i] = {
                id: attribute.name,
                name: attribute_name,
                value: data[attribute.name],
                type: 'text'
            };
        }
    }
    main.modal_data = modal_data;
    bind();
}

// 下拉选择表
var table_select = $("#table_select");
// i18N语言下拉选择
var i18N_select = $("#i18N_select");
// 从cookie中读取i18N配置
var i18N_config = getCookie("i18N_config");
// 如果cookie中没有
if (i18N_config !== "") {
    i18N_select.val(getCookie("i18N_config"));
}

// 语言切换事件绑定
i18N_select.change(function () {
    setCookie("i18N_config", i18N_select.val(), 30);
    refresh();
});
// 切换表事件绑定
table_select.change(function () {
    main.attribute = "id";
    main.page = 1;
    main.table = table_select.val();
    main.size = $("#page_size_select").val();
    $("#table_sort").val("id");
    query();
});

// 初始化
init();

// 初始化
function init() {
    getUrlVar();
    query();
}

// 从链接获取参数
function getUrlVar() {
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
        table_select.val(table);
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
}

// 模态框提交按钮事件绑定
function bind() {
    // 模态框提交按钮
    var modalSubmitBtn = $("#" + main.table + "ModalSubmitBtn");
    // 取消之前的绑定
    modalSubmitBtn.unbind();
    // 绑定点击事件
    modalSubmitBtn.on('click', function () {
        saveOne(main.table + "_form");
    });
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
    var object = null;
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
                if (json.objects === undefined) main.query_data = null
                else {
                    var objects = json.objects;
                    for (var i = 0; i < objects.length; i++) {
                        for (var key in objects[i]) {
                            if (objects[i][key] == null) continue;
                            if (objects[i][key] instanceof Object) {
                                if (objects[i][key]['name'] != undefined)
                                    objects[i][key] = objects[i][key]['name'];
                                else
                                    objects[i][key] = objects[i][key]['id'];
                            } else {
                                if (objects[i][key].length != null && objects[i][key].length > 50) {
                                    objects[i][key] = objects[i][key].substr(0, 50);
                                }
                            }
                        }
                    }
                    main.query_data = objects;
                    main.page = json.page;
                    main.size = json.size;
                    main.total_pages = json.total_pages;
                    main.total_elements = json.total_elements;
                }
                if (json.objects == null) {
                    alert(i18N.result + i18N.is + i18N.null);
                }
            } else {
                alert(statusCodeToAlert(status))
            }
        }
        ,
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