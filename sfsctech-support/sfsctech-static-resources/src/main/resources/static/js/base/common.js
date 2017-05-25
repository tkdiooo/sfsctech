/**
 * <br>
 *
 * @author 张麒
 */

/**
 * 重写window.alert方法，用layer控件替换
 * @param msg
 * @param callback
 */
window.alert = function (msg, callback) {
    layer.msg(msg, {
        time: 0,
        shade: [0.8, '#393D49'],
        area: '300px',
        btn: ['确认']
        , yes: function (index) {
            invoke(callback);
            layer.close(index);
        }
    });
};
/**
 * 重写window.confirm方法，用layer控件替换
 * @param msg
 * @param callback
 */
window.confirm = function (msg, callback) {
    layer.msg(msg, {
        time: 0,
        shade: [0.8, '#393D49'],
        area: '300px',
        btn: ['确认', '取消']
        , yes: function (index) {
            invoke(callback);
            layer.close(index);
        }
    });
};
/**
 * 重写window.open方法，用layer控件替换
 * @param opt
 */
window.open = function (opt) {
    var index = layer.load();
    openDialog(opt);
    layer.close(index);
};
/**
 * 执行方法
 */
function invoke(callback, data) {
    if (callback !== null && callback !== undefined) {
        if (typeof callback === "function") {
            callback(data);
        } else {
            var method = eval(callback);
            new method(data);
        }
    }
}
/**
 * jQuery.hotkeys 键盘热键绑定方法
 * @param keys
 * @param callback
 */
function bindHotKey(keys, callback) {
    jQuery.hotkeys.add(keys, function () {
        invoke(callback);
    });
}
/**
 * 回车键事件绑定
 * @param elements
 * @param callback
 */
function bindEnterHotKey(elements, callback) {
    $('#' + elements).bind('keydown', function (evt) {
        if (evt.keyCode === 13) invoke(callback);
    });
}
/**
 * dialog窗口
 * @param opt
 */
function openDialog(opt) {
    var defaults = {
        type: 1,
        title: '弹出框',
        shadeClose: true,
        shade: [0.8, '#393D49'],
        move: false
    };
    var plugin = this;
    plugin.settings = $.extend({}, defaults, opt);
    layer.open(plugin.settings);
}

var win = {};
var browser = {};

/**
 * 初始化窗口高度、宽度
 */
function initWindow() {
    //获取当前浏览器宽度和高度
    //win.width = typeof window.innerWidth == 'undefined' ? document.documentElement.clientWidth : window.innerWidth;
    //win.height = typeof window.innerHeight == 'undefined' ? document.body.scrollHeight : window.innerHeight;
    win.width = $(window).width();
    win.height = $(document).height();
    // 高度最小600
    if (win.height < 600) {
        win.height = 600;
        $(document.body).height(win.height);
    }
    // 宽度最小960
    if (win.width < 960) {
        win.width = 960;
        $(document.body).width(win.width);
    }
}

/**
 * 初始化浏览器判断
 */
function initBrowser() {
    var userAgent = navigator.userAgent, rMsie = /(msie\s|trident.*rv:)([\w.]+)/, rFirefox = /(firefox)\/([\w.]+)/,
        rOpera = /(opera).+version\/([\w.]+)/, rChrome = /(chrome)\/([\w.]+)/, rSafari = /version\/([\w.]+).*(safari)/;
    var ua = userAgent.toLowerCase();
    // 判断是否是IE
    var match = rMsie.exec(ua);
    if (match !== null) {
        browser.type = 'IE';
        browser.version = match[2];
    } else if ((match = rFirefox.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rOpera.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rChrome.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rSafari.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else if ((match = rSafari.exec(ua)) !== null) {
        browser.type = match[1];
        browser.version = match[2];
    } else {
        browser.type = '';
        browser.version = '';
    }
}

/**
 * 禁用鼠标右键
 */
$(document).on('contextmenu', function () {
    return false;
});

$(function () {
    initBrowser();
    initWindow();
});

/**
 * 发送ajax请求
 * @param url 请求路径
 * @param data 数据
 * @param opt 设置
 * @param callback 回调方法
 */
function ajax_action(url, data, opt, callback) {
    var defaults = {
        contentType: "application/x-www-form-urlencoded",
        async: true,
        waiting: true
    };
    if (url.indexOf("?") !== -1) {
        url += "&ajaxTimeFresh=" + Math.random();
    } else {
        url += "?ajaxTimeFresh=" + Math.random();
    }
    var plugin = this;
    plugin.settings = $.extend({}, defaults, opt);
    $.ajax({
        url: url,
        method: "post",
        cache: false,
        dataType: "json",
        contentType: plugin.settings.contentType,
        async: plugin.settings.async,
        data: data,
        beforeSend: function () {
            if (plugin.settings.waiting) {
                layer.load(2, {
                    shade: [0.8, '#393D49']
                });
            }
        },
        success: function (data) {
            if (plugin.settings.waiting) {
                layer.closeAll('loading');
            }
            invoke(callback, data);
        },
        error: function (data) {
            alert(data.statusText, function () {
                layer.closeAll('loading');
            });
            if (typeof plugin.settings.errorHandle === "function") {
                plugin.settings.errorHandle(data);
            }
        }
    });
}

/**
 * ajax加载页面
 * @param url 请求路径
 * @param container 页面加载的容器
 * @param opt 设置
 */
function load_url(url, container, data, opt) {
    var defaults = {
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: 'html',
        waiting: true
    };
    if (url.indexOf("?") !== -1) {
        url += "&ajaxTimeFresh=" + Math.random();
    } else {
        url += "?ajaxTimeFresh=" + Math.random();
    }
    var plugin = this;
    plugin.settings = $.extend({}, defaults, opt);
    $.ajax({
        type: "POST",
        url: url,
        dataType: plugin.settings.dataType,
        contentType: plugin.settings.contentType,
        data: data,
        cache: false,
        async: true,
        beforeSend: function () {
            if (plugin.settings.waiting) {
                layer.load(2, {
                    shade: [0.8, '#393D49']
                });
            }
        },
        success: function (data) {
            if (plugin.settings.waiting) {
                layer.closeAll();
            }
            container.css({
                opacity: '0.0'
            }).html(data).delay(50).animate({
                opacity: '1.0'
            }, 300);
        },
        error: function (XMLHttpRequest, ajaxOptions, thrownError) {
            if (plugin.settings.waiting) {
                layer.closeAll();
            }
            var json = JSON.parse(XMLHttpRequest.responseText);
            alert(json.messages, to_url(json.url));
        }
    });
}

function to_url(url) {
    if (self !== top) {
        parent.window.location.href = url;
    } else {
        window.location.href = url;
    }
}