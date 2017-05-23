package com.sfsctech.common.constants;


import com.sfsctech.common.inf.IEnum;

/**
 * I18N Constants
 *
 * @author 张麒 2016/4/11.
 * @version Description:
 */
public class I18NConstants {

    public enum Tips implements IEnum<String, String> {

        /**
         * 系统错误，请稍后再尝试
         */
        ExceptionSys("tips.exception.sys"),

        /**
         * 服务器错误，请稍后再尝试
         */
        ExceptionService("tips.exception.service"),

        /**
         * 网络连接错误，请稍后再尝试
         */
        ExceptionNetwork("tips.exception.network"),

        /**
         * 上传文件过大，文件最大支持{0}
         */
        ExceptionUpload("tips.exception.upload"),

        /**
         * 请求的页面不存在
         */
        Exception404("tips.exception.404"),

        /**
         * 操作成功
         */
        OperateSuccess("tips.operate.success"),

        /**
         * 操作失败
         */
        OperateFailure("tips.operate.failure"),

        /**
         * 账号或密码错误
         */
        LoginWrong("tips.login.wrong"),

        /**
         * 当前账号已经登录，不能重复登录
         */
        LoginUnrepeatable("tips.login.unrepeatable"),

        /**
         * 账号不存在
         */
        LoginAccountNoExist("tips.login.account.noexist"),

        /**
         * 账号已删除
         */
        LoginAccountRemove("tips.login.account.remove"),

        /**
         * 账号已被禁用
         */
        LoginAccountDisabled("tips.login.account.disabled"),

        /**
         * 账号已被锁定
         */
        LoginAccountLocked("tips.login.account.locked"),

        /**
         * 锁定账号{0}成功
         */
        AccountLockedSuccess("tips.account.locked.success"),

        /**
         * 锁定账号{0}失败
         */
        AccountLockedFailure("tips.account.locked.failure"),

        /**
         * 解锁账号{0}成功
         */
        AccountUnlockedSuccess("tips.account.unlocked.success"),

        /**
         * 解锁账号{0}失败
         */
        AccountUnlockedFailure("tips.account.unlocked.failure"),

        /**
         * 启用账号{0}成功
         */
        AccountEnableSuccess("tips.account.enable.success"),

        /**
         * 启用账号{0}失败
         */
        AccountEnableFailure("tips.account.enable.failure"),

        /**
         * 禁用账号{0}成功
         */
        AccountUnenableSuccess("tips.account.unenable.success"),

        /**
         * 禁用账号{0}失败
         */
        AccountUnenableFailure("tips.account.unenable.failure"),
        /**
         * 请填写账号
         */
        LoginEmptyAccount("tips.login.empty.account"),

        /**
         * 请填写密码
         */
        LoginEmptyPassword("tips.login.empty.password"),

        /**
         * 验证码错误
         */
        WrongVerificationNotmatch("tips.wrong.verification.notmatch"),

        /**
         * 请确认两次密码相同
         */
        WrongPasswordConfirm("tips.wrong.password.confirm"),

        /**
         * {0}对象为空
         */
        EmptyObject("tips.empty.object"),

        /**
         * {0}集合为空
         */
        EmptyCollection("tips.empty.collection");

        Tips(String value) {
            this.value = value;
        }

        private String value;

        @Override
        public String getKey() {
            return "";
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}
