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
        index: 1,
        page: 1,
        size: 10,
        attribute: "time",
        direction: "DESC",
        total_pages: 0,
        total_elements: 0,
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