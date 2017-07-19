/**
 * @ClassName: UserSessionHolder
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author A18ccms a18ccms_gmail_com
 * @date 2015年8月19日 下午5:43:41
 */

package com.sfsctech.security.session;


/**
 * Session 持有人
 */
public class SessionHolder {

    /**
     * ThreadLocal to hold the UserToken for Threads to access.
     */
    private static final ThreadLocal<SessionInfo> threadLocal = new ThreadLocal<>();

    /**
     * Retrieve the UserToken from the ThreadLocal.
     *
     * @return the Asssertion associated with this thread.
     */
    public static SessionInfo getSessionInfo() {
        return threadLocal.get();
    }

    /**
     * Add the UserToken to the ThreadLocal.
     *
     * @param sessionInfo the UserToken to add.
     */
    public static void setSessionInfo(final SessionInfo sessionInfo) {
        threadLocal.set(sessionInfo);
    }

    /**
     * Clear the ThreadLocal.
     */
    public static void clear() {
        threadLocal.set(null);
    }
}
