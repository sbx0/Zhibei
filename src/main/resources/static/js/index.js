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
        index: 1,
        page: 1,
        size: 10,
        attribute: "id",
        direction: "ASC",
        total_pages: 0,
        total_elements: 0,
        search_result_show: "none",
        search_list_show: "none",
        search_keyword: "",
        user: {
            id: "-1",
            name: "未登录",
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

getArticle();

function loadMore() {
    main.page++;
    getArticle();
}

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
                for (var i = 0; i < json.objects.length; i++) {
                    article_data.push(json.objects[i]);
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

// 事件绑定
// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.index;
});

document.write("<script src=\"/js/base/base.js\"></script>");