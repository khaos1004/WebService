package com.WebProject.WebService.common.login.utils;

import jakarta.servlet.http.Cookie;

public class CookieBuilder {
    private final Cookie cookie;

    private CookieBuilder(String name, String value) {
        this.cookie = new Cookie(name, value);
    }

    public static CookieBuilder of(String name, String value) {
        return new CookieBuilder(name, value);
    }

    public CookieBuilder httpOnly(boolean httpOnly) {
        cookie.setHttpOnly(httpOnly);
        return this;
    }

    public CookieBuilder secure(boolean secure) {
        cookie.setSecure(secure);
        return this;
    }

    public CookieBuilder path(String path) {
        cookie.setPath(path);
        return this;
    }

    public CookieBuilder maxAge(int seconds) {
        cookie.setMaxAge(seconds);
        return this;
    }

    public Cookie build() {
        return cookie;
    }
}
