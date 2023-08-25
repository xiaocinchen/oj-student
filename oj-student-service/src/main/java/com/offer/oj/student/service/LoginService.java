package com.offer.oj.student.service;

import com.offer.oj.domain.Result;
import com.offer.oj.domain.dto.ForgetPasswordDTO;
import com.offer.oj.domain.dto.LoginDTO;
import com.offer.oj.domain.dto.ModifyPasswordDTO;
import com.offer.oj.domain.dto.UserDTO;
import com.offer.oj.domain.dto.UserIdentityDTO;
import com.offer.oj.domain.dto.VerificationDTO;
import com.offer.oj.platform.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;

public interface LoginService {

    /**
     * 注册第一步：发验证邮件
     */
    Result<String> registerSendEmail(UserDTO user, HttpServletResponse response);

    /**
     * 重发邮件
     */
    Result resendVerifyEmail(String username, String tempLicence);

    /**
     * 注册第二步：验证邮件
     */
    Result registerVerifyEmail(VerificationDTO verification);

    /**
     * login
     */
    Result login(LoginDTO loginDTO, HttpServletResponse response);

    /**
     * isLogin
     */
    boolean isLogin(Cookie cookie);

    /**
     * logout
     */
    Result logout(Cookie cookie);;

    /**
     * forget password
     */
    Result forgetPassword(ForgetPasswordDTO forgetPasswordDTO);

    Boolean verifyRole(UserIdentityDTO userIdentityDTO, String role);

    UserIdentityDTO getUserIdentity(Integer userId);

    Result modifyPassword(ModifyPasswordDTO modifyPasswordDTO);

    Result checkKaptcha(String kaptchaToken, String code);

    Integer getUserId(String token);

    RenderedImage getKaptchaImage(HttpServletResponse response) throws IOException;



}
