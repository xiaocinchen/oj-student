package com.offer.oj.student.interceptor;

import com.offer.oj.common.service.CacheService;
import com.offer.oj.domain.dto.UserIdentityDTO;
import com.offer.oj.domain.enums.CacheEnum;
import com.offer.oj.student.interceptor.common.Utils;
import com.offer.oj.platform.service.UserService;
import com.offer.oj.student.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;
import java.util.Objects;

public class PassInterceptor implements HandlerInterceptor {


    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws AuthenticationException {
        Integer userId = loginService.getUserId(Utils.getUserId(request));
        UserIdentityDTO userIdentityDTO;
        try {
            userIdentityDTO = loginService.getUserIdentity(userId);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
        request.setAttribute("UserIdentityDTO", Objects.requireNonNullElseGet(userIdentityDTO, UserIdentityDTO::new));
        return true;
    }
}
