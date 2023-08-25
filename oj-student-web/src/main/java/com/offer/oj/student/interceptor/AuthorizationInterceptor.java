package com.offer.oj.student.interceptor;

import com.offer.oj.domain.dto.UserIdentityDTO;
import com.offer.oj.domain.enums.RoleEnum;
import com.offer.oj.platform.service.UserService;
import com.offer.oj.student.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        UserIdentityDTO userIdentityDTO;
        if ((userIdentityDTO = (UserIdentityDTO) request.getAttribute("UserIdentityDTO")) != null){
            if (loginService.verifyRole(userIdentityDTO, RoleEnum.TEACHER.getValue())){
                return true;
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not a teacher.");
        return false;
    }

}