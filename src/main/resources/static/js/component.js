// Logo
var component_logo = {
    template: '<div class="aw-logo hidden-xs">\n    <a href="/index.html"></a>\n</div>'
};
// 搜索框
var component_search = {
    props: ["search_result_show", "search_list_show"],
    data: function () {
        return {
            i18N: i18N,
            search_keyword: "",
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
        }
    },
    template: '<div class="aw-search-box  hidden-xs hidden-sm">\n    <form class="navbar-search" action="" id="global_search_form" method="post">\n        <input class="form-control search-query"\n               type="text"\n               id="search_input_nav_bar"\n               v-model="search_keyword"\n               :placeholder="i18N.search_message"/>\n        <span :title="i18N.search" id="global_search_btns">\n            <i class="fas fa-search"></i>\n        </span>\n        <div class="aw-dropdown" :style="{display:search_result_show}">\n            <div class="mod-body">\n                <!--<p class="title">测试搜索标题</p>-->\n                <ul class="aw-dropdown-list collapse" :style="{display:search_list_show}">\n                    <li class="user clearfix" v-for="user in search_user_data">\n                        <a :href="\'/user/\'+user.id">\n                            <img :src="user.img">\n                            {{user.name}}\n                            <span class="aw-hide-txt">{{user.desc}}</span>\n                        </a>\n                    </li>\n                    <li class="topic clearfix" v-for="topic in search_topic_data">\n                        <span class="topic-tag">\n                            <a :href="topic.id" class="text">\n                                {{topic.name}}\n                            </a>\n                        </span>\n                        <span class="pull-right text-color-999">\n                            {{topic.number}} {{i18N.comment}}\n                        </span>\n                    </li>\n                    <li class="question clearfix" v-for="question in search_question_data">\n                        <i class="icon icon-bestbg pull-left"></i>\n                        <a class="aw-hide-txt pull-left" :href="question.id">\n                            {{question.title}}\n                        </a>\n                        <span class="pull-right text-color-999">{{question.comment_number}} {{i18N.replay}}</span>\n                    </li>\n                </ul>\n                <p class="search" style="display: block;">\n                    <span>{{i18N.search}}:</span>\n                    <a>{{search_keyword}}</a>\n                </p>\n            </div>\n            <div class="mod-footer">\n                <a href="javascript:void(0);"\n                   class="pull-right btn btn-mini btn-success publish"\n                   data-toggle="modal"\n                   data-target="#myModal">\n                    {{i18N.ask_question}}\n                </a>\n            </div>\n        </div>\n    </form>\n</div>',
};
// 菜单
var component_menu = {
    data: function () {
        return {
            i18N: i18N,
            notification_data: [
                {id: 1, title: "测试通知1"},
                {id: 2, title: "测试通知2"},
                {id: 3, title: "测试通知3"},
                {id: 4, title: "测试通知4"},
                {id: 5, title: "测试通知5"},
            ],
        }
    },
    template: '<div class="aw-top-nav navbar">\n    <div class="navbar-header">\n        <button class="navbar-toggle pull-left">\n            <span class="icon-bar"></span>\n            <span class="icon-bar"></span>\n            <span class="icon-bar"></span>\n        </button>\n    </div>\n    <nav role="navigation" class="collapse navbar-collapse bs-navbar-collapse">\n        <ul class="nav navbar-nav">\n            <li v-for="url in i18N.nav_data">\n                <a :href="url.path">\n                    <span v-html="url.icon"></span>\n                    {{url.text}}\n                </a>\n            </li>\n            <li>\n                <a href="notifications">\n                    <i class="fas fa-bell"></i>\n                    {{i18N.notification}}\n                </a>\n                <span class="badge badge-important" style="display:block" id="notifications_unread">\n                            999+\n                        </span>\n                <div class="aw-dropdown pull-right hidden-xs">\n                    <div class="mod-body">\n                        <ul id="header_notification_list">\n                            <li v-for="notification in notification_data">\n                                <a :href="notification.id">{{notification.title}}</a>\n                            </li>\n                        </ul>\n                    </div>\n                    <div class="mod-footer">\n                        <a href="notifications/">{{i18N.read_more}}</a>\n                    </div>\n                </div>\n            </li>\n            <li>\n                <a style="font-weight:bold;">· · ·</a>\n                <div class="dropdown-list pull-right">\n                    <ul id="extensions-nav-list">\n                        <li v-for="url in i18N.nav_more_data">\n                            <a :href="url.path">\n                                <span v-html="url.icon"></span>\n                                {{url.text}}\n                            </a>\n                        </li>\n                    </ul>\n                </div>\n            </li>\n        </ul>\n    </nav>\n</div>'
};
// 登陆注册
var component_login_register = {
    props: ["not_login", "user"],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="aw-user-nav">\n    <div v-show="!not_login">\n        <a href="javascript:void(0);" class="aw-user-nav-dropdown">\n            <img alt="" :src="user.avatar"/>\n            <span class="badge badge-important">999+</span>\n        </a>\n        <div class="aw-dropdown dropdown-list pull-right">\n            <ul class="aw-dropdown-list">\n                <li v-for="url in i18N.nav_user_data">\n                    <a :href="url.path">\n                        <span v-html="url.icon"></span>\n                        {{url.text}}\n                    </a>\n                </li>\n            </ul>\n        </div>\n    </div>\n    <a v-show="not_login" class="login btn btn-normal btn-primary" href="/login.html">\n        {{i18N.login}}\n    </a>\n    <a v-show="not_login" class="register btn btn-normal btn-success" href="/login.html">\n        {{i18N.register}}\n    </a>\n</div>'
};
var component_post = {
    props: ["not_login"],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div v-show="!not_login" class="aw-publish-btn">\n    <a id="header_publish" class="btn-primary" data-toggle="modal" data-target="#myModal">\n        <i class="fas fa-bullhorn"></i>\n        {{i18N.post}}\n    </a>\n    <div class="dropdown-list pull-right">\n        <ul>\n            <li v-for="url in i18N.nav_post_data">\n                <a :href="url.path">\n                    <span v-html="url.icon"></span>\n                    {{url.text}}\n                </a>\n            </li>\n        </ul>\n    </div>\n</div>',
};
// 导航条
var component_nav_bar = {
    props: ["search_result_show", "search_list_show", "not_login", "user"],
    components: {
        'component-logo': component_logo,
        'component-search': component_search,
        'component-menu': component_menu,
        'component-login-register': component_login_register,
        'component-post': component_post,
    },
    template: '<div class="aw-top-menu-wrap">\n    <div class="container">\n        <component-logo></component-logo>\n        <component-search\n                :search_result_show="search_result_show"\n                :search_list_show="search_list_show"\n        ></component-search>\n        <component-menu></component-menu>\n        <component-login-register\n                :not_login="not_login"\n                :user="user"\n        ></component-login-register>\n        <component-post\n                :not_login="not_login"\n        ></component-post>\n    </div>\n</div>'
};
// 页脚
var component_footer = {
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="aw-footer-wrap text-center ">\n    <div class="aw-footer" v-html="i18N.foot_html"></div>\n    <div align="center" class="mb-50">\n        <select id="i18N_select">\n            <option :value="option.name" v-for="option in i18N.lib">{{option.value}}</option>\n        </select>\n    </div>\n</div>'
};