package com.expansion.server.global.config;

import com.expansion.server.domain.user.repository.UserRepository;
import com.expansion.server.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // 소셜 로그인 성공 후 리다이렉트할 프론트엔드 URL
    private static final String REDIRECT_URI = "http://localhost:5173/oauth2/callback";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // OAuth2 사용자 이메일로 DB 조회 → userId, role 획득
        String email = oAuth2User.getAttribute("email");

        userRepository.findByEmail(email).ifPresent(user -> {
            String accessToken = jwtUtil.generateAccessToken(user.getUserId(), user.getRole());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

            try {
                String redirectUrl = REDIRECT_URI
                        + "?accessToken=" + accessToken
                        + "&refreshToken=" + refreshToken;
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } catch (IOException e) {
                log.error("OAuth2 redirect failed", e);
            }
        });
    }
}
