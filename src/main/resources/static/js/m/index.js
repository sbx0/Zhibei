if (!browser.versions.mobile) {
    var no_redirect = getQueryVariable("no_redirect");
    if (!no_redirect) {
        location.href = "/index.html"
    }
}
var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N,
        not_login: true,
        search_result_show: "none",
        search_list_show: "none",
        search_keyword: "",
        user: {
            id: "-1",
            name: "未登录",
        },
        article_data: [
            {
                id: 1,
                title: "测试问题",
                watch: 1,
                replay: 1,
                view: 1,
                time: "2小时前",
                content: "测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容",
                category: {
                    id: 1,
                    name: "建站运营",
                },
                user: {
                    id: 2,
                    name: "测试用户2",
                },
            },
            {
                id: 1,
                title: "测试问题",
                watch: 1,
                replay: 1,
                view: 1,
                time: "2小时前",
                content: "测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容测试文章内容",
                category: {
                    id: 1,
                    name: "建站运营",
                },
                user: {
                    id: 2,
                    name: "测试用户2",
                },
            },
        ],
        question_data: [
            {
                id: 1,
                title: "测试问题",
                watch: 1,
                replay: 1,
                view: 1,
                time: "2小时前",
                category: {
                    id: 1,
                    name: "插件",
                },
                user: {
                    id: 1,
                    name: "测试用户1",
                },
            },
            {
                id: 1,
                title: "测试问题",
                watch: 1,
                replay: 1,
                view: 1,
                time: "2小时前",
                category: {
                    id: 1,
                    name: "插件",
                },
                user: {
                    id: 1,
                    name: "测试用户1",
                },
            },
            {
                id: 1,
                title: "测试问题",
                watch: 1,
                replay: 1,
                view: 1,
                time: "2小时前",
                category: {
                    id: 1,
                    name: "插件",
                },
                user: {
                    id: 1,
                    name: "测试用户1",
                },
            },
        ],
        search_user_data: [],
        search_topic_data: [],
        search_question_data: [],
        hot_topic_data: [
            {id: 1, name: "测试热门话题1", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 2, name: "测试热门话题2", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 3, name: "测试热门话题3", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 4, name: "测试热门话题4", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 5, name: "测试热门话题5", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
        ],
    },
    components: {
        "component-header": component_header,
        "component-footer": component_footer,
        "component-menu": component_menu,
    },
    methods: {},
    create: function () {

    }
});

// 初始化用户信息
get_info();

// 方法定义
// 检测登陆状态
function get_info() {
    $.ajax({
        type: "get",
        url: "../user/info",
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

// 事件绑定
// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.index;
});
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
// 搜索框取消选中状态
$("#search_input_nav_bar").blur(function () {
    main.search_result_show = "none";
    main.search_list_show = "none"
});
// 搜索框选中
$("#search_input_nav_bar").focus(function () {
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