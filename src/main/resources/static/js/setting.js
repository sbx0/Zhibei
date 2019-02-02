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
    components: {
        "component-nav-bar": component_nav_bar,
        "component-footer": component_footer,
    },
    methods: {},
    create: function () {

    }
});

var md5File;
//监听分块上传过程中的时间点
WebUploader.Uploader.register({
    "before-send-file": "beforeSendFile",  // 整个文件上传前
    "before-send": "beforeSend",  // 每个分片上传前
    "after-send-file": "afterSendFile"  // 分片上传完毕
}, {
    //时间点1：所有分块进行上传之前调用此函数 ，检查文件存不存在
    beforeSendFile: function (file) {
        var deferred = WebUploader.Deferred();
        md5File = hex_md5(file.name + file.size);//根据文件名称，大小确定文件唯一标记，这种方式不赞成使用
        $.ajax({
            type: "POST",
            url: "/file/md5Check",
            data: {
                md5File: md5File, //文件唯一标记
            },
            async: false,  // 同步
            dataType: "json",
            success: function (response) {
                var status = response.status;
                if (statusCodeToBool(status)) {
                    deferred.resolve();  //文件不存在或不完整，发送该文件
                } else if (status == 7) {
                    var name = response.name;
                    var type = response.type;
                    var url = "http://" + window.location.host + "/upload/" + type + "/" + name;
                    $('#' + file.id).find('p.state').text("秒传成功");
                    $('#' + file.id).find('.progress').fadeOut();
                    $.ajax({
                        type: "GET",
                        url: "/file/avatar",
                        data: {
                            md5File: md5File,
                        },
                        cache: false,
                        async: false,  // 同步
                        dataType: "json",
                        success: function (response) {
                            var status = response.status;
                            if (status == 0) {
                                var url = response.url
                                url = "http://" + window.location.host + "/upload/" + url;
                                $("#picker").show();
                                $("#url").val(url);
                                $('#' + file.id).find('p.state').text("上传成功");
                                $('#' + file.id).find('.progress').fadeOut();
                            } else {
                                $('#' + file.id).find('p.state').text('merge error');
                                deferred.reject();
                            }
                        }
                    })
                } else {
                    $('#' + file.id).find('p.state').text(statusCodeToAlert(status));
                }
            }
        }, function (jqXHR, textStatus, errorThrown) { // 任何形式的验证失败，都触发重新上传
            deferred.resolve();
        });
        return deferred.promise();
    },
    //时间点2：如果有分块上传，则每个分块上传之前调用此函数，判断分块存不存在
    beforeSend: function (block) {
        var deferred = WebUploader.Deferred();
        $.ajax({
            type: "POST",
            url: "/file/chunkCheck",
            data: {
                md5File: md5File,  //文件唯一标记
                chunk: block.chunk,  //当前分块下标
            },
            dataType: "json",
            success: function (response) {
                if (response) {
                    deferred.reject(); //分片存在，跳过
                } else {
                    deferred.resolve();  //分块不存在或不完整，重新发送该分块内容
                }
            }
        }, function (jqXHR, textStatus, errorThrown) { //任何形式的验证失败，都触发重新上传
            deferred.resolve();
        });
        return deferred.promise();
    },
    //时间点3：分片上传完成后，通知后台合成分片
    afterSendFile: function (file) {
        var chunksTotal = Math.ceil(file.size / (5 * 1024 * 1024));
        if (chunksTotal >= 1) {
            //合并请求
            var deferred = WebUploader.Deferred();
            $.ajax({
                type: "POST",
                url: "/file/merge",
                data: {
                    name: file.name,
                    md5File: md5File,
                    chunks: chunksTotal
                },
                cache: false,
                async: false,  // 同步
                dataType: "json",
                success: function (response) {
                    var status = response.status;
                    if (status == 0) {
                        var url = response.url
                        url = "http://" + window.location.host + "/upload/" + url;
                        $("#picker").show();
                        $("#url").val(url);
                        $('#' + file.id).find('p.state').text("上传成功");
                        $('#' + file.id).find('.progress').fadeOut();
                        $.ajax({
                            type: "GET",
                            url: "/file/avatar",
                            data: {
                                md5File: md5File,
                            },
                            cache: false,
                            async: false,  // 同步
                            dataType: "json",
                            success: function (response) {
                                var status = response.status;
                                if (status == 0) {
                                    var url = response.url
                                    url = "http://" + window.location.host + "/upload/" + url;
                                    $("#picker").show();
                                    $("#url").val(url);
                                    $('#' + file.id).find('p.state').text("上传成功");
                                    $('#' + file.id).find('.progress').fadeOut();
                                } else {
                                    $('#' + file.id).find('p.state').text('merge error');
                                    deferred.reject();
                                }
                            }
                        })
                    } else {
                        $('#' + file.id).find('p.state').text('merge error');
                        deferred.reject();
                    }
                }
            });
            return deferred.promise();
        }
    }
});

var uploader = WebUploader.create({
    auto: true,// 选完文件后，是否自动上传。
    server: '/file/upload',// 文件接收服务端。
    pick: '#picker',// 选择文件的按钮。可选。
    chunked: true,// 开启分片上传
    chunkSize: 5 * 1024 * 1024,// 5M
    chunkRetry: 3,// 错误重试次数
    fileNumLimit: 1, // 只能上传一个文件
});

//上传添加参数
uploader.on('uploadBeforeSend', function (obj, data, headers) {
    data.md5File = md5File;
});

// 当有文件被添加进队列的时候
uploader.on('fileQueued', function (file) {
    $("#picker").hide();//隐藏上传框
    $("#thelist").append('<div id="' + file.id + '" class="item">' +
        '<h6 class="info">' + file.name + '</h4>' +
        '<p class="state"></p>' +
        '</div>');
});

// 文件上传过程中创建进度条实时显示。
uploader.on('uploadProgress', function (file, percentage) {
    var $li = $('#' + file.id),
        $percent = $li.find('.progress .progress-bar');

    // 避免重复创建
    if (!$percent.length) {
        $percent = $('<div class="progress progress-striped active">' +
            '<div class="progress-bar" role="progressbar" style="width: 0%"></div>' +
            '</div>').appendTo($li).find('.progress-bar');
    }
    $li.find('p.state').text('上传中');
    $percent.css('width', percentage * 100 + '%');
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