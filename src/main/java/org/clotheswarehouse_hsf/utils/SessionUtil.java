package org.clotheswarehouse_hsf.utils;

import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.User;

public class SessionUtil {

    private static final String USER_SESSION_KEY = "user";
    private static final String ERROR_MESSAGE_KEY = "errorMessage";
    private static final String SUCCESS_MESSAGE_KEY = "successMessage";

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    public static User getUser(HttpSession session) {
        Object obj = session.getAttribute(USER_SESSION_KEY);
        return (obj instanceof User) ? (User) obj : null;
    }

    public static boolean isUserLoggedIn(HttpSession session) {
        return getUser(session) != null;
    }

    public static void removeUser(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }


    public static void setErrorMessage(HttpSession session, String message) {
        session.setAttribute(ERROR_MESSAGE_KEY, message);
    }

    public static String getErrorMessage(HttpSession session) {
        return (String) session.getAttribute(ERROR_MESSAGE_KEY);
    }

    public static String getAndRemoveErrorMessage(HttpSession session) {
        String msg = getErrorMessage(session);
        session.removeAttribute(ERROR_MESSAGE_KEY);
        return msg;
    }


    public static void setSuccessMessage(HttpSession session, String message) {
        session.setAttribute(SUCCESS_MESSAGE_KEY, message);
    }

    public static String getSuccessMessage(HttpSession session) {
        return (String) session.getAttribute(SUCCESS_MESSAGE_KEY);
    }

    public static String getAndRemoveSuccessMessage(HttpSession session) {
        String msg = getSuccessMessage(session);
        session.removeAttribute(SUCCESS_MESSAGE_KEY);
        return msg;
    }

}
