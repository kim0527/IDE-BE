package api.v1.auth.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieTokenHandler {
    private static final String KEY = "Refresh-Token";

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long expireTime;

    public void setCookieToken(HttpServletResponse response, String refreshToken) {

        Cookie cookie = new Cookie(KEY, refreshToken);

        cookie.setMaxAge((int) expireTime);
        cookie.setPath("/");
        // optional properties
//        cookie.setSecure(true); localhost test를 위해서 잠시 주석
//        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void expireCookieToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(KEY, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}