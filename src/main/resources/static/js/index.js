if (browser.versions.mobile) {
    var no_redirect = getQueryVariable("no_redirect");
    if (!no_redirect) {
        location.href = "/m/index.html"
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
        hot_user_data: [
            {id: 1, name: "测试用户1", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 2, name: "测试用户2", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 3, name: "测试用户3", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 4, name: "测试用户4", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 5, name: "测试用户5", question: "11", like: "11", img: "../img/avatar-min-img.png"},
        ],
    },
    components: {
        "component-nav-bar": component_nav_bar,
        "component-footer": component_footer,
    },
    methods: {},
    create: function () {

    }
});

// 事件绑定
// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.index;
});

document.write("<script src=\"/js/base/base.js\"></script>");