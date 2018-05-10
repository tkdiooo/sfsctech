package com.sfsctech.common.core.base.session;


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
