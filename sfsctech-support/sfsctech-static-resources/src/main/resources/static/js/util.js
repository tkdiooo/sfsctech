/**
 * 工具类
 */

/**
 * 创建css/js文件
 */
function createjscssfile(filename, filetype) {
    var file;
    if (filetype === "js") {
        file = document.createElement('script');
        file.setAttribute("type", "text/javascript");
        file.setAttribute("src", filename);
    } else if (filetype === "css") {
        file = document.createElement("link");
        file.setAttribute("rel", "stylesheet");
        file.setAttribute("type", "text/css");
        file.setAttribute("href", filename);
    }
    return file;
}

/**
 * 动态加载一个js/css文件
 *
 * @param filename
 * @param filetype
 */
function loadjscssfile(filename, filetype) {
    var file = createjscssfile(filename, filetype);
    if (typeof file !== "undefined") document.getElementsByTagName("head")[0].appendChild(file);
}

/**
 * 替换已经加载的js/css文件
 *
 * @param oldfilename
 * @param newfilename
 * @param filetype
 */
function replacejscssfile(oldfilename, newfilename, filetype) {
    var targetelement = (filetype === "js") ? "script" : (filetype === "css") ? "link" : "none";
    var targetattr = (filetype === "js") ? "src" : (filetype === "css") ? "href" : "none";
    // 获取所有的script/link标签
    var allsuspects = document.getElementsByTagName(targetelement);
    for (var i = allsuspects.length; i >= 0; i--) {
        // 如果不等于空，并且匹配
        if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)
            && allsuspects[i].getAttribute(targetattr).indexOf(oldfilename) !== -1) {
            // 创建新的文件
            var newelement = createjscssfile(newfilename, filetype);
            // 替换
            allsuspects[i].parentNode.replaceChild(newelement, allsuspects[i]);
        }
    }
}

/**
 * 删除已经加载过的js/css文件
 *
 * @param filename
 * @param filetype
 */
function removejscssfile(filename, filetype) {
    var targetelement = (filetype === "js") ? "script" : (filetype === "css") ? "link" : "none";
    var targetattr = (filetype === "js") ? "src" : (filetype === "css") ? "href" : "none";
    var allsuspects = document.getElementsByTagName(targetelement);
    for (var i = allsuspects.length; i >= 0; i--) {
        if (allsuspects[i] && allsuspects[i].getAttribute(targetattr)
            && allsuspects[i].getAttribute(targetattr).indexOf(filename) !== -1)
            allsuspects[i].parentNode.removeChild(allsuspects[i]);
    }
}

/**
 * 替换域，跨域调用方法使用
 */
function domain() {
    var _surl = String(document.domain);
    if (_surl.lastIndexOf(".") !== -1) {
        var _surl1 = _surl.substring(0, _surl.lastIndexOf("."));
        if (_surl1.lastIndexOf(".") !== -1) {
            _surl1 = _surl1.substring(_surl1.lastIndexOf(".") + 1, _surl1.length);
        }
        _surl = _surl1 + _surl.substring(_surl.lastIndexOf("."), _surl.length);
    }
    document.domain = _surl;
}

/**
 * 执行方法
 */
function invoke(callback, data) {
    if (callback !== null) {
        if (typeof callback === "function") {
            callback(data);
        } else {
            var func = eval(callback);
            new func(data);
        }
    }
}

/**
 * Date对象添加Format方法
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) {
    //author: meizz
    var o =
        {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};