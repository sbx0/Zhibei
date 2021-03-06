// ------------------------------------------------------------------------------------------------------ //
// 常用方法
// 1.日期格式化
// var time = Format(getDate(TIME.toString()), "yyyy-MM-dd HH:mm:ss")
// ------------------------------------------------------------------------------------------------------ //

// 检测用户名是否违法
function checkStrIsIllegal(str) {
    str = str.trim();
    // 为空
    if (checkNullStr(str)) {
        return i18N.alert.empty;
    }
    // 中间有空格
    if (str.indexOf(" ") !== -1) {
        return i18N.alert.illegal;
    }
    // 有特殊字符
    if (checkSpecialStr(str)) {
        return i18N.alert.special_str
    }
    return "";
}

// log输出
function log(text) {
    console.log(text);
}

// 刷新页面
function refresh() {
    location.reload();
}

// 设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires + ";path=/";
}

// 获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

//  状态码转换成语句
function statusCodeToAlert(status) {
    switch (status) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
            return i18N.status[status];
            break;
        default:
            return "操作失败";
    }
}

// 状态码转换成true或false
function statusCodeToBool(status) {
    switch (status) {
        case 0:
            return true;
            break;
        default:
            return false;
    }
}

// 返回顶部
$(function () {
    $(window).scroll(function () {
        if ($(window).scrollTop() > 250)
            $('div.go-top').show();
        else
            $('div.go-top').hide();
    });
    $('div.go-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 250);
    });
});

// 检测字符串是否为空
function checkNullStr(str) {
    if (str == null) return true;
    if (str.length === 0) return true;
    if (str.trim() === "") return true;
    if (str.trim().length === 0) return true;
    return false;
}

// 检测有没有特殊字符
function checkSpecialStr(str) {
    for (var i = 0; i < str.length; i++) {
        // 中文符号
        if (/^[~·@#￥%……&*（）——+{}【】|：“；‘《》，。/？、]*$/.test(str.substr(i, 1))) return true;
        // 英文符号
        if (/^[~`!@#$%^&*()\-=+{}\\|\[\];:'"<>,./?]*$/.test(str.substr(i, 1))) return true;
    }
    return false;
}

// 获取日期
function getDate(strDate) {
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
        function (a) {
            return parseInt(a, 10) - 1;
        }).match(/\d+/g) + ')');
    return date;
}

// 计算日期差
function DateMinus() {
    var sdate = new Date(arguments[0].replace(/-/g, "/"));
    var edate;
    if (arguments.length == 1) edate = new Date();
    else if (arguments.length == 2) edate = new Date(arguments[1].replace(/-/g, "/"));
    var days = sdate.getTime() - edate.getTime();
    var day = parseInt(days / (1000 * 60 * 60 * 24));
    return day;
}

// 日期格式化
function Format(now, mask) {
    var d = now;
    var zeroize = function (value, length) {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    }
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0) {
        switch ($0) {
            case 'd':
                return d.getDate()
            case 'dd':
                return zeroize(d.getDate())
            case 'ddd':
                return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()]
            case 'dddd':
                return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()]
            case 'M':
                return d.getMonth() + 1
            case 'MM':
                return zeroize(d.getMonth() + 1)
            case 'MMM':
                return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()]
            case 'MMMM':
                return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()]
            case 'yy':
                return String(d.getFullYear()).substr(2)
            case 'yyyy':
                return d.getFullYear()
            case 'h':
                return d.getHours() % 12 || 12
            case 'hh':
                return zeroize(d.getHours() % 12 || 12)
            case 'H':
                return d.getHours()
            case 'HH':
                return zeroize(d.getHours())
            case 'm':
                return d.getMinutes()
            case 'mm':
                return zeroize(d.getMinutes())
            case 's':
                return d.getSeconds()
            case 'ss':
                return zeroize(d.getSeconds())
            case 'l':
                return zeroize(d.getMilliseconds(), 3)
            case 'L':
                var m = d.getMilliseconds()
                if (m > 99) m = Math.round(m / 10)
                return zeroize(m)
            case 'tt':
                return d.getHours() < 12 ? 'am' : 'pm'
            case 'TT':
                return d.getHours() < 12 ? 'AM' : 'PM'
            case 'Z':
                return d.toUTCString().match(/[A-Z]+$/)
            default:
                return $0.substr(1, $0.length - 2)
        }
    })
}

// 防止浏览器缓存
function noCache(url) {
    //  var getTimestamp = Math.random()
    var args = url.split("?");
    var newUrl = args[0];
    if (args[0] == url) {
        var getTimestamp = new Date().getTime();
        newUrl = url + "?noCache=" + getTimestamp;
    } else {
        newUrl += "?";
        var pars = args[1].split("&");
        var isNoCache = false;
        for (var i = 0; i < pars.length; i++) {
            var par = pars[i].split("=");
            if (par[0] == "noCache") {
                par[1] = new Date().getTime();
                isNoCache = true;
            }
            newUrl += par[0] + "=" + par[1];
            if (i < pars.length - 1) newUrl += "&";
        }
        if (!isNoCache) {
            var getTimestamp = new Date().getTime();
            newUrl += "&noCache=" + getTimestamp;
        }
    }
    return newUrl;
}

// 获取url参数
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return null;
}