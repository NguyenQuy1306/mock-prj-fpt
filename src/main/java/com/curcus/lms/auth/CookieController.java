package com.curcus.lms.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cookie")
public class CookieController {
    @GetMapping("/set-cookie")
    public String setCookie(
            @RequestParam String name,
            HttpServletResponse response) {
        Cookie cookie = new Cookie("username", name);
        cookie.setMaxAge(7 * 24 * 60 * 60); // Cookie sẽ tồn tại trong 7 ngày
        cookie.setHttpOnly(true); // Chỉ truy cập được qua HTTP, bảo mật hơn
        response.addCookie(cookie);
        return "Cookie đã được tạo!";
    }

    @GetMapping("/get-cookie")
    public String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    return "Hello, " + cookie.getValue();
                }
            }
        }
        return "Cookie không tồn tại";
    }

    @GetMapping("/delete-cookie")
    public String deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", null);
        cookie.setMaxAge(0); // Đặt thời gian sống của Cookie về 0
        response.addCookie(cookie);
        return "Cookie đã bị xóa!";
    }
}
