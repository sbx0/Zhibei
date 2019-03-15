var i18N = {
    user: '用戶',
    website_name: '智貝',
    admin_management_system: '後台管理系統',
    logout: '退出登錄',
    search: '搜索',
    login: '登陸',
    login_title: '沒有帳號？點我註冊',
    register: '註冊',
    register_title: '已經註冊？點我登陸',
    username: '用戶名',
    password: '密碼',
    back: '返回',
    confirm: '確認',
    index: '首頁',
    network: '網絡',
    submit: '提交',
    certification: '認證',
    apply: '申請',
    close: '關閉',
    error_message: '頁面發生錯誤',
    admin_message: '這裡是管理員界面',
    login_message: '已登錄',
    search_message: '輸入關鍵字進行搜索',
    loading: '加載中',
    result: '結果',
    is: '為',
    null: '空',
    comment: '評論',
    replay: '回复',
    ask_question: '發起問題',
    notification: '通知',
    read_more: '查看更多',
    message: '私信',
    post: '發布',
    i_know: '我知道了',
    setting: '設置',
    discover: '發現',
    view: '瀏覽',
    watch: '關注',
    article: '文章',
    hot: '熱門',
    more: '更多',
    topic: '話題',
    question: '問題',
    page_number: '頁數',
    total_elements: '總條數',
    page_size: '每頁條數',
    personal: '個人',
    information: '資料',
    email: '郵箱',
    introduction: '簡介',
    avatar: '頭像',
    upload: '上傳',
    uploading: '上傳中',
    save: '保存',
    desktop: '桌面',
    mobile: '手機',
    version: '版本',
    integral: '積分',
    level: '等級',
    type: '種類',
    license: '相關證件號',
    license_alert: '身份證號 或 營業執照號 或 院校名稱 或 機構名稱',
    file: '文件',
    certification_file_alert: '請提交對應的身份證或者組織機構代碼證件掃描',
    sort: '排序',
    asc: '升序',
    desc: '降序',
    direction: '方向',
    info: '信息',
    under_review: '審核中',
    failed: '失敗',
    role: '角色',
    permission: '權限',
    log: '日誌',
    management: '管理',
    please: '請',
    input: '輸入',
    name: '名稱',
    id: '編號',
    available: '可用',
    father: '父親',
    permissions: '權限',
    update: '修改',
    delete: '刪除',
    add: '增加',
    no_more_result: '沒有更多了',
    table_init: '表',

    certification_type: {
        personal: '個人認證',
        admin: '管理員認證',
        enterprise: '企業認證',
        university: '院校認證',
        mechanism: '機構認證',
    },

    lib: lib,
    m_nav_data: [
        {id: 1, text: '發現', path: '/m/index.html', icon: '<i class=\'icon xfind\'></i>'},
        {id: 2, text: '話題', path: '/m/index.html', icon: '<i class=\'icon theme\'></i>'},
        {id: 3, text: '我的', path: '/m/user.html', icon: '<i class=\'icon my\'></i>'},
    ],
    nav_data: [
        {id: 1, text: '首頁', path: '/index.html', icon: '<i class=\'fas fa-home\'></i>'},
        {id: 2, text: '登陸', path: '/login.html', icon: '<i class=\'fas fa-sign-in-alt\'></i>'},
        {id: 3, text: '後台', path: '/user/admin', icon: '<i class=\'fas fa-sign-in-alt\'></i>'},
    ],
    nav_more_data: [
        {id: 1, text: '後台', path: '/user/admin', icon: ''},
        {id: 2, text: '手機版', path: '/m/index.html?no_redirect=true', icon: ''},
        {id: 3, text: 'GitHub', path: 'https://github.com/sbx0/Zhibei', icon: ''},
    ],
    nav_user_data: [
        {id: 1, text: '私信', path: '#', icon: '<i class=\'fas fa-comments\'></i>'},
        {id: 2, text: '設置', path: '/setting.html', icon: '<i class=\'fas fa-wrench\'></i>'},
        {id: 3, text: '退出', path: '/user/logout', icon: '<i class=\'fas fa-sign-out-alt\'></i>'},
    ],
    nav_post_data: [
        {id: 1, text: '問題', path: '#', icon: '<i class=\'fas fa-question\'></i>'},
        {id: 2, text: '懸賞', path: '#', icon: '<i class=\'fas fa-award\'></i>'},
        {id: 3, text: '文章', path: '/post.html', icon: '<i class=\'far fa-newspaper\'></i>'},
        {id: 4, text: '建議', path: '#', icon: '<i class=\'fas fa-comment-medical\'></i>'},
    ],
    category_data: [
        {id: 1, text: '全部', path: '#', icon: '<i class=\'fas fa-question\'></i>'},
        {id: 2, text: '精選', path: '#', icon: '<i class=\'fas fa-award\'></i>'},
        {id: 3, text: '公告', path: '#', icon: '<i class=\'far fa-newspaper\'></i>'},
        {id: 4, text: '建議', path: '#', icon: '<i class=\'fas fa-comment-medical\'></i>'},
    ],
    tab_data: [
        {id: 1, text: '最新', path: '#', icon: '<i class=\'fas fa-question\'></i>'},
        {id: 2, text: '熱門', path: '#', icon: '<i class=\'fas fa-award\'></i>'},
        {id: 3, text: '懸賞', path: '#', icon: '<i class=\'fas fa-award\'></i>'},
        {id: 4, text: '等待回复', path: '#', icon: '<i class=\'far fa-newspaper\'></i>'},
    ],
    foot_html: '智貝科技有限公司 2019',
    table: {
        user: '用戶',
        tag: '標籤',
        category: '分類',
        certification: '申請',
        demand: '需求',
        role: '角色',
        permission: '權限',
        file: '文件',
        log: '日誌',
        article: '文章',
        comment: '評論',
        verify: '驗證',
        message: '消息',
        alipay: '支付宝',
        product: '商品',
        wallet: '钱包',
    },
    attribute: {
        user: {
            id: '編號',
            name: '用戶名稱',
            nickname: '暱稱',
            password: '密碼',
            introduction: '簡介',
            registerTime: '註冊時間',
            banned: '是否封禁',
            phone: '手機',
            email: '郵箱',
            avatar: '頭像',
            sex: '性別',
            role: '角色',
            birthday: '生日',
            integral: '積分',
            level: '等級',
            exp: '經驗',
            exp_max: '經驗上限',
        },
        role: {
            id: '編號',
            name: '角色名稱',
            introduction: '簡介',
            registerTime: '註冊時間',
            available: '是否可用',
            father: '父親',
            permissions: '權限',
        },
        permission: {
            id: '編號',
            name: '權限名稱',
            introduction: '簡介',
            url: '權限鏈接',
            str: '權限字符串',
            available: '是否可用',
            father: '父親',
        },
        certification: {
            id: '編號',
            info: '認證信息',
            type: '認證類型',
            passed: '是否通過',
            start_time: '開始時間',
            end_time: '結束時間',
            user: '申請者',
            license: '相關證件號',
            img: '相關證件掃描件',
        },
        file: {
            id: '編號',
            name: '文件名',
            originalName: '原始文件名',
            ext: '文件後綴',
            type: '文件類型',
            md5: '唯一識別碼',
            size: '大小',
            time: '上傳時間',
            banned: '是否封禁',
        },
        log: {
            id: '編號',
            ip: 'IP地址',
            user: '操作者',
            time: '操作時間',
            runTime: '運行時間',
            args: '參數',
            method: '方法',
            className: '類名',
            url: '鏈接',
        },
        article: {
            id: '編號',
            title: '標題',
            time: '發表時間',
            introduction: '簡介',
            content: '內容',
            lastChangeTime: '上次修改時間',
            password: '密碼',
            views: '查看數',
            comments: '評論數',
            likes: '喜歡數',
            dislikes: '不喜歡數',
            top: '置頂排序指數',
            author: '作者',
            tags: '標籤',
        },
        comment: {
            id: '編號',
            path: '路徑',
            time: '發表時間',
            floor: '樓層',
            content: '評論內容',
            likes: '喜歡數',
            dislikes: '不喜歡數',
            top: '置頂排序指數',
            father: '父親',
            poster: '發布者'
        },
        category: {
            id: '編號',
            name: '名稱',
            introduction: '簡介',
            cover: '封面',
            father: '父親',
        },
        demand: {
            id: '編號',
            title: '標題',
            time: '發佈時間',
            cover: '封面',
            content: '內容',
            endTime: '截止時間',
            budget: '預算',
            category: '分類',
            poster: '發布者',
        },
        tag: {
            id: '編號',
            name: '名稱',
            introduction: '簡介',
            cover: '封面',
        },
        verify: {
            id: '編號',
            type: '種類',
            md5: '驗證地址',
            used: '是否使用過',
            parameter: '參數',
            time: '發佈時間',
        },
        message: {
            id: '編號',
            content: '內容',
            ip: 'IP地址',
            sendUser: '發送者',
            receiveUser: '接收者',
            sendTime: '發送時間',
            receiveTime: '接收時間',
            type: '種類',
        },
        alipay: {
            id: '编号',
            outTradeNo: '平台订单号',
            tradeNo: '支付宝订单号',
            createTime: '创建时间',
            endTime: '结束时间',
            buyer: '购买者',
            type: '种类',
            products: '商品列表',
            amount: '实付金额',
        },
        product: {
            id: '编号',
            name: '名称',
            price: '金额',
            startTime: '开卖时间',
            endTime: '结束时间',
            seller: '买家',
        },
        wallet: {
            id: '编号',
            money: '拥有的钱',
            owner: '谁的',
        },
    },
    alert: {
        empty: '為空',
        illegal: '不合法',
        special_str: '特殊字符',
        error: '錯誤',
    },
    status: [
        '操作成功',
        '操作失敗',
        '異常錯誤',
        '暫未登錄',
        '超出限制',
        '查無結果',
        '暫無權限',
        '重複操作'
    ],
};