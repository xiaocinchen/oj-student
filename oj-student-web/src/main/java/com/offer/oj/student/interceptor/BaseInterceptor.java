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
import jline.internal.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;

public class BaseInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        Integer userId = loginService.getUserId(Utils.getUserId(request));
        if (userId != null) {
            try {
                UserIdentityDTO userIdentityDTO;
                if ((userIdentityDTO = loginService.getUserIdentity(userId)) != null) {
                    request.setAttribute("UserIdentityDTO", userIdentityDTO);
                    return true;
                }
            } catch (Exception e) {
                throw new AuthenticationException("User authority exception.", e);
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Permission denied.");
        return false;
    }

}