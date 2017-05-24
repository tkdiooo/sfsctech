/**
 * <br>
 *
 * @author 张麒
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