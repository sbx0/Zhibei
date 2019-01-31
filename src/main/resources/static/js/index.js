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
        search_user_data: [
            {id: 1, name: "测试用户1", desc: "测试介绍1", img: "/img/avatar-min-img.png"},
            {id: 2, name: "测试用户3", desc: "测试介绍2", img: "/img/avatar-min-img.png"},
            {id: 3, name: "测试用户2", desc: "测试介绍3", img: "/img/avatar-min-img.png"},
            {id: 4, name: "测试用户4", desc: "测试介绍4", img: "/img/avatar-min-img.png"},
            {id: 5, name: "测试用户5", desc: "测试介绍5", img: "/img/avatar-min-img.png"},
        ],
        search_topic_data: [
            {id: 1, name: "测试话题1", number: "1"},
            {id: 2, name: "测试话题2", number: "2"},
            {id: 3, name: "测试话题3", number: "3"},
            {id: 4, name: "测试话题4", number: "4"},
            {id: 5, name: "测试话题5", number: "5"},
        ],
        search_question_data: [
            {id: 1, title: "测试问题1", comment_number: "1"},
            {id: 2, title: "测试问题2", comment_number: "2"},
            {id: 3, title: "测试问题3", comment_number: "3"},
            {id: 4, title: "测试问题4", comment_number: "4"},
            {id: 5, title: "测试问题5", comment_number: "5"},
        ],
        notification_data: [
            {id: 1, title: "测试通知1"},
            {id: 2, title: "测试通知2"},
            {id: 3, title: "测试通知3"},
            {id: 4, title: "测试通知4"},
            {id: 5, title: "测试通知5"},
        ],
        hot_topic_data: [
            {id: 1, name: "测试热门话题1", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 2, name: "测试热门话题2", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 3, name: "测试热门话题3", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 4, name: "测试热门话题4", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 5, name: "测试热门话题5", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
        ],
        hot_user_data: [
            {id: 1, name: "测试用户1", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 2, name: "测试用户2", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 3, name: "测试用户3", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 4, name: "测试用户4", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 5, name: "测试用户5", question: "11", like: "11", img: "../img/avatar-min-img.png"},
        ],
    },
    components: {},
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

// 登陆与注册之间界面切换
function login_or_register() {
    main.login_or_register = !main.login_or_register;
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