/**
 * PC端发布
 * post.html 的 js
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
        search_result_show: "none",
        search_list_show: "none",
        search_keyword: "",
        user: {
            id: "-1",
            name: "未登录"
        },
        search_user_data: [],
        search_topic_data: [],
        search_question_data: []
    },
    components: {
        "component-nav-bar": component_nav_bar,
        "component-footer": component_footer,
        "base-input": base_input
    },
    methods: {},
    create: function () {

    }
});

var editor = new Editor();
editor.render();

// 发布文章
function post() {
    var content = $("#content");
    content.val(editor.value());
    // 判断标题是否存在
    if (checkNullStr($("#title_input").val())) {
        alert(i18N.please + i18N.input + i18N.attribute.article.title);
        return false;
    }
    // 判断简介是否存在
    if (checkNullStr($("#introduction_input").val())) {
        alert(i18N.please + i18N.input + i18N.attribute.article.introduction);
        return false;
    }
    // 判断博文内容是否存在
    if (checkNullStr(content.val())) {
        alert(i18N.please + i18N.input + i18N.attribute.article.content);
        return false;
    }
    $.ajax({
        type: "post",
        url: "/article/post",
        data: $("#article_post_form").serialize(),
        success: function (json) {
            var status = json.status;
            alert(statusCodeToAlert(status));
            if (statusCodeToBool(status)) {
                location.replace("/index.html");
            }
        },
        error: function () {
            alert(i18N.network + i18N.alert.error);
        }
    })
}

// 事件绑定
// 切换语言时title也会切换
$(function () {
    document.title = main.i18N.post;
});

document.write("<script src=\"/js/base/base.js\"></script>");