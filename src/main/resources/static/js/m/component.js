// 头部
var component_header = {
    props: ["not_login", "user"],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="header">\n    <a class="logo" href="/m/index.html">\n        <img src="../img/logo.png" alt="" width="52">\n    </a>\n    <span class="pull-right">\n        <div v-show="not_login">\n            <a href="../m/login.html" class="btn btn-mini btn-primary btn-normal">{{i18N.login}}</a>\n            <a href="../m/login.html?register=true" class="btn btn-mini btn-success btn-normal">{{i18N.register}}</a>\n        </div>\n        <div v-show="!not_login">\n            <a href="../m/setting.html">\n                {{user.name}}\n                <img :src="user.avatar" alt="" class="img" width="25">\n            </a>\n        </div>\n    </span>\n</div>'
};
// 尾部
var component_footer = {
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<footer class="text-center text-color-999 mb-50">\n    <span v-html="i18N.foot_html"></span>\n    <p>\n        <a href="/user/logout">{{i18N.logout}}</a>\n    </p>\n    <div align="center">\n        <select id="i18N_select">\n            <option :value="option.name" v-for="option in i18N.lib">{{option.value}}</option>\n        </select>\n    </div>\n</footer>'
};
// 菜单
var component_menu = {
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="nav">\n    <ul>\n        <li v-for="url in i18N.m_nav_data">\n            <a :href="url.path">\n                <div v-html="url.icon"></div>\n                {{url.text}}\n            </a>\n        </li>\n    </ul>\n</div>'
};