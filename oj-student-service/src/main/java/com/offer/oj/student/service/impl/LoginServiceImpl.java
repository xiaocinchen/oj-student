package com.offer.oj.student.service.impl;

import com.offer.oj.common.util.JsonUtils;
import com.offer.oj.domain.Result;
import com.offer.oj.domain.dto.ForgetPasswordDTO;
import com.offer.oj.domain.dto.LoginDTO;
import com.offer.oj.domain.dto.ModifyPasswordDTO;
import com.offer.oj.domain.dto.UserDTO;
import com.offer.oj.domain.dto.UserIdentityDTO;
import com.offer.oj.domain.dto.VerificationDTO;
import com.offer.oj.platform.service.KaptchaService;
import com.offer.oj.platform.service.UserService;
import com.offer.oj.student.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private KaptchaService kaptchaService;

    @Override
    public Result<String> registerSendEmail(UserDTO user, HttpServletResponse response) {
        return userService.registerSendEmail(user, response);
    }

    @Override
    public Result resendVerifyEmail(String username, String tempLicence) {
        return userService.resendVerifyEmail(username, tempLicence);
    }

    @Override
    public Result registerVerifyEmail(VerificationDTO verification) {
        return userService.registerVerifyEmail(verification);
    }

    @Override
    public Result login(LoginDTO loginDTO, HttpServletResponse response) {
        return userService.login(loginDTO, response);
    }

    @Override
    public boolean isLogin(Cookie cookie) {
        return userService.isLogin(cookie);
    }

    @Override
    public Result logout(Cookie cookie) {
        return userService.logout(cookie);
    }

    @Override
    public Result forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        return userService.forgetPassword(forgetPasswordDTO);
    }

    @Override
    public Boolean verifyRole(UserIdentityDTO userIdentityDTO, String role) {
        return userService.verifyRole(userIdentityDTO, role);
    }

    @Override
    public UserIdentityDTO getUserIdentity(Integer userId) {
        return userService.getUserIdentity(userId);
    }

    @Override
    public Result modifyPassword(ModifyPasswordDTO modifyPasswordDTO) {
        return userService.modifyPassword(modifyPasswordDTO);
    }

    @Override
    public Result checkKaptcha(String kaptchaToken, String code) {
        return kaptchaService.checkKaptcha(kaptchaToken, code);
    }

    @Override
    public Integer getUserId(String token) {
        return userService.getUserIdFromToken(token);
    }

    @Override
    public RenderedImage getKaptchaImage(HttpServletResponse response) {
        Map<String, Object> result;
        try {
            result = kaptchaService.getKaptchaImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String name = (String) result.get("cookieName");
        String value = (String) result.get("cookieValue");
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
        try {
            return JsonUtils.imageFromJson((byte[]) result.get("kaptcha"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
