package com.dalew

import grails.util.Holders

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CookieUtils {
    /** 30 days in seconds */
    static final int DEFAULT_COOKIE_AGE = 30 * 24 * 60 * 60
    static final String COOKIE_DEFAULT_PATH = '/'
    static final boolean COOKIE_DEFAULT_SECURE = false
    static final boolean COOKIE_DEFAULT_HTTP_ONLY = false
    static final int COOKIE_AGE_TO_DELETE = 0

    static void extendReqResp() {
        HttpServletRequest.metaClass.findCookie = { String name ->
            return Holders.applicationContext.cookieService.findCookie(name)
        }
        HttpServletRequest.metaClass.getCookie = { String name ->
            return Holders.applicationContext.cookieService.getCookie(name)
        }
        HttpServletResponse.metaClass.setCookie = { ...args ->
            if (args.size() == 1 && ((args[0] instanceof List) || (args[0] instanceof Map) || (args[0] instanceof Cookie))) {
                Holders.applicationContext.cookieService.setCookie(args[0])
            } else {
                Holders.applicationContext.cookieService.setCookie(args as List)
            }
            return null
        }
        HttpServletResponse.metaClass.deleteCookie = { ...args ->
            if (args.size() == 1 && ((args[0] instanceof List) || (args[0] instanceof Cookie))) {
                Holders.applicationContext.cookieService.deleteCookie(args[0])
            } else {
                Holders.applicationContext.cookieService.deleteCookie(args as List)
            }
            return null
        }
    }
}
