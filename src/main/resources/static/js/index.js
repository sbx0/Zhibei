/**
 * PC端首页
 * index.html 的 js
 *
 * @param json 一般是Ajax获得到的json字符串
 * @param json.object 通用对象json字符串
 * @param json.objects 通用对象列表json字符串
 */
// Vue.js
var main = new Vue({
    el: '#main',
    data: {
        i18N: i18N,
        not_login: true,
        index: 1,
        page: 1,
        size: 10,
        attribute: "time",
        direction: "DESC",
        total_pages: 0,
        total_elements: 0,
        search_result_show: "none",
        search_list_show: "none",
        search_keyword: "",
        user: {
            id: "-1",
            name: "未登录"
        },
        article_data: [],
        question_data: [],
        search_user_data: [],
        search_topic_data: [],
        search_question_data: [],
        hot_topic_data: [
            {id: 1, name: "测试热门话题1", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 2, name: "测试热门话题2", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 3, name: "测试热门话题3", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 4, name: "测试热门话题4", question: "11", watch: "11", img: "../img/topic-mid-img.png"},
            {id: 5, name: "测试热门话题5", question: "11", watch: "11", img: "../img/topic-mid-img.png"}
        ],
        hot_user_data: [
            {id: 1, name: "测试用户1", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 2, name: "测试用户2", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 3, name: "测试用户3", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 4, name: "测试用户4", question: "11", like: "11", img: "../img/avatar-min-img.png"},
            {id: 5, name: "测试用户5", question: "11", like: "11", img: "../img/avatar-min-img.png"}
        ]
    },
    components: {
        "component-nav-bar": component_nav_bar,
        "component-footer": component_footer
    },
    methods: {},
    create: function () {

    }
});

// 初始化
init();

// 初始化
function init() {
    getArticle();
}

// 加载更多
function loadMore() {
    main.page++;
    getArticle();
}

// 获取文章
function getArticle() {
    $.ajax({
        type: "get",
        url: "/article/index?page=" + main.page +
            "&size=" + main.size +
            "&attribute=" + main.attribute +
            "&direction=" + main.direction,
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.objects === null) {
                main.page--;
                alert(i18N.no_more_result);
            } else if (main.page === 1) {
                main.article_data = json.objects;
            } else {
                var article_data = [];
                for (var i = 0; i < main.article_data.length; i++) {
                    article_data.push(main.article_data[i]);
                }
                for (var j = 0; j < json.objects.length; j++) {
                    article_data.push(json.objects[j]);
                }
                main.article_data = article_data;
            }
            return false;
        },
        error: function () {
            alert(i18N.network + i18N.alert.error);
            return false;
        }
    });
}

// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.index;
});

document.write("<script src=\"/js/base/base.js\"></script>");