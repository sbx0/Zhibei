var i18N = {
    user: "用户",
    website_name: "智贝",
    admin_management_system: "后台管理系统",
    logout: "退出登录",
    search: "搜索",
    login: "登陆",
    login_title: "没有帐号？点我注册",
    register: "注册",
    register_title: "已经注册？点我登陆",
    username: "用户名",
    password: "密码",
    back: "返回",
    confirm: "确认",
    index: "首页",
    network: "网络",
    submit: "提交",
    certification: "认证",
    apply: "申请",
    close: "关闭",
    error_message: "页面发生错误",
    admin_message: "这里是管理员界面",
    login_message: "已登录",
    search_message: "输入关键字进行搜索",
    loading: "加载中",
    result: "结果",
    is: "为",
    null: "空",
    comment: "评论",
    replay: "回复",
    ask_question: "发起问题",
    notification: "通知",
    read_more: "查看更多",
    message: "私信",
    post: "发布",
    i_know: "我知道了",
    setting: "设置",
    discover: "发现",
    view: "浏览",
    watch: "关注",
    article: "文章",
    hot: "热门",
    more: "更多",
    topic: "话题",
    question: "问题",
    page_number: "页数",
    total_elements: "总条数",
    page_size: "每页条数",
    personal: "个人",
    information: "资料",
    email: "邮箱",
    introduction: "简介",
    avatar: "头像",
    upload: "上传",
    uploading: "上传中",
    save: "保存",
    desktop: "桌面",
    mobile: "手机",
    version: "版本",
    integral: "积分",
    level: "等级",
    type: "种类",
    license: "相关证件号",
    license_alert: "身份证号 或 营业执照号 或 院校名称 或 机构名称",
    file: "文件",
    certification_file_alert: "请提交对应的身份证或者组织机构代码证件扫描",
    sort: "排序",
    asc: "升序",
    desc: "降序",
    direction: "方向",
    info: "信息",
    under_review: "审核中",
    failed: "失败",
    role: "角色",
    permission: "权限",
    log: "日志",
    management: "管理",
    please: "请",
    input: "输入",
    name: "名称",
    id: "编号",
    available: "可用",
    father: "父亲",
    permissions: "权限",
    update: "修改",
    delete: "删除",
    add: "增加",
    no_more_result: "没有更多了",
    table_init: "表",

    certification_type: {
        personal: "个人认证",
        admin: "管理员认证",
        enterprise: "企业认证",
        university: "院校认证",
        mechanism: "机构认证",
    },

    lib: lib,
    m_nav_data: [
        {id: 1, text: "发现", path: "/m/index.html", icon: "<i class=\"icon xfind\"></i>"},
        {id: 2, text: "话题", path: "/m/index.html", icon: "<i class=\"icon theme\"></i>"},
        {id: 3, text: "我的", path: "/m/user.html", icon: "<i class=\"icon my\"></i>"},
    ],
    nav_data: [
        {id: 1, text: "首页", path: "/index.html", icon: "<i class=\"fas fa-home\"></i>"},
        {id: 2, text: "登陆", path: "/login.html", icon: "<i class=\"fas fa-sign-in-alt\"></i>"},
        {id: 3, text: "后台", path: "/user/admin", icon: "<i class=\"fas fa-sign-in-alt\"></i>"},
    ],
    nav_more_data: [
        {id: 1, text: "后台", path: "/user/admin", icon: ""},
        {id: 2, text: "手机版", path: "/m/index.html?no_redirect=true", icon: ""},
        {id: 3, text: "GitHub", path: "https://github.com/sbx0/Zhibei", icon: ""},
    ],
    nav_user_data: [
        {id: 1, text: "私信", path: "#", icon: "<i class=\"fas fa-comments\"></i>"},
        {id: 2, text: "设置", path: "/setting.html", icon: "<i class=\"fas fa-wrench\"></i>"},
        {id: 3, text: "退出", path: "/user/logout", icon: "<i class=\"fas fa-sign-out-alt\"></i>"},
    ],
    nav_post_data: [
        {id: 1, text: "问题", path: "#", icon: "<i class=\"fas fa-question\"></i>"},
        {id: 2, text: "悬赏", path: "#", icon: "<i class=\"fas fa-award\"></i>"},
        {id: 3, text: "文章", path: "/post.html", icon: "<i class=\"far fa-newspaper\"></i>"},
        {id: 4, text: "建议", path: "#", icon: "<i class=\"fas fa-comment-medical\"></i>"},
    ],
    category_data: [
        {id: 1, text: "全部", path: "#", icon: "<i class=\"fas fa-question\"></i>"},
        {id: 2, text: "精选", path: "#", icon: "<i class=\"fas fa-award\"></i>"},
        {id: 3, text: "公告", path: "#", icon: "<i class=\"far fa-newspaper\"></i>"},
        {id: 4, text: "建议", path: "#", icon: "<i class=\"fas fa-comment-medical\"></i>"},
    ],
    tab_data: [
        {id: 1, text: "最新", path: "#", icon: "<i class=\"fas fa-question\"></i>"},
        {id: 2, text: "热门", path: "#", icon: "<i class=\"fas fa-award\"></i>"},
        {id: 3, text: "悬赏", path: "#", icon: "<i class=\"fas fa-award\"></i>"},
        {id: 4, text: "等待回复", path: "#", icon: "<i class=\"far fa-newspaper\"></i>"},
    ],
    foot_html: "智贝科技有限公司 2019",
    table: {
        user: "用户",
        tag: "标签",
        category: "分类",
        certification: "申请",
        demand: "需求",
        role: "角色",
        permission: "权限",
        file: "文件",
        log: "日志",
        article: "文章",
        comment: "评论",
        verify: "验证",
        message: "消息",
    },
    attribute: {
        user: {
            id: "编号",
            name: "用户名称",
            nickname: "昵称",
            password: "密码",
            introduction: "简介",
            registerTime: "注册时间",
            banned: "是否封禁",
            phone: "手机",
            email: "邮箱",
            avatar: "头像",
            sex: "性别",
            role: "角色",
            birthday: "生日",
            integral: "积分",
            level: "等级",
            exp: "经验",
            exp_max: "经验上限",
        },
        role: {
            id: "编号",
            name: "角色名称",
            introduction: "简介",
            registerTime: "注册时间",
            available: "是否可用",
            father: "父亲",
            permissions: "权限",
        },
        permission: {
            id: "编号",
            name: "权限名称",
            introduction: "简介",
            url: "权限链接",
            str: "权限字符串",
            available: "是否可用",
            father: "父亲",
        },
        certification: {
            id: "编号",
            info: "认证信息",
            type: "认证类型",
            passed: "是否通过",
            start_time: "开始时间",
            end_time: "结束时间",
            user: "申请者",
            license: "相关证件号",
            img: "相关证件扫描件",
        },
        file: {
            id: "编号",
            name: "文件名",
            originalName: "原始文件名",
            ext: "文件后缀",
            type: "文件类型",
            md5: "唯一识别码",
            size: "大小",
            time: "上传时间",
            banned: "是否封禁",
        },
        log: {
            id: "编号",
            ip: "IP地址",
            user: "操作者",
            time: "操作时间",
            runTime: "运行时间",
            args: "参数",
            method: "方法",
            className: "类名",
            url: "链接",
        },
        article: {
            id: "编号",
            title: "标题",
            time: "发表时间",
            introduction: "简介",
            content: "内容",
            lastChangeTime: "上次修改时间",
            password: "密码",
            views: "查看数",
            comments: "评论数",
            likes: "喜欢数",
            dislikes: "不喜欢数",
            top: "置顶排序指数",
            author: "作者",
            tags: "标签",
        },
        comment: {
            id: '编号',
            path: '路径',
            time: '发表时间',
            floor: '楼层',
            content: '评论内容',
            likes: '喜欢数',
            dislikes: '不喜欢数',
            top: '置顶排序指数',
            father: '父亲',
            poster: '发布者'
        },
        category: {
            id: '编号',
            name: '名称',
            introduction: '简介',
            cover: '封面',
            father: '父亲',
        },
        demand: {
            id: '编号',
            title: '标题',
            time: '发布时间',
            cover: '封面',
            content: '内容',
            endTime: '截止时间',
            budget: '预算',
            category: '分类',
            poster: '发布者',
        },
        tag: {
            id: '编号',
            name: '名称',
            introduction: '简介',
            cover: '封面',
        },
        verify: {
            id: '编号',
            type: '种类',
            md5: '验证地址',
            used: '是否使用过',
            parameter: '参数',
            time: '发布时间',
        },
        message: {
            id: "编号",
            content: "内容",
            ip: "IP地址",
            sendUser: "发送者",
            receiveUser: "接收者",
            sendTime: "发送时间",
            receiveTime: "接收时间",
            type: "种类",
        }
    },
    alert: {
        empty: "为空",
        illegal: "不合法",
        special_str: "特殊字符",
        error: '错误',
    },
    status: [
        "操作成功",
        "操作失败",
        "异常错误",
        "暂未登录",
        "超出限制",
        "查无结果",
        "暂无权限",
        "重复操作"
    ],
};