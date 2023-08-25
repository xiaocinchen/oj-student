package com.offer.oj.student.controller;

import com.offer.oj.domain.Result;
import com.offer.oj.domain.dto.ForgetPasswordDTO;
import com.offer.oj.domain.dto.LoginDTO;
import com.offer.oj.domain.dto.ModifyPasswordDTO;
import com.offer.oj.domain.dto.UserDTO;
import com.offer.oj.domain.dto.VerificationDTO;
import com.offer.oj.student.service.LoginService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.awt.image.RenderedImage;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register/send")
    public Result studentRegister(@RequestBody @Validated UserDTO userDTO, HttpServletResponse response) {
        userDTO.setRole("student");
        return loginService.registerSendEmail(userDTO, response);
    }

    @GetMapping("/register/resend")
    public Result studentEmailResend(@CookieValue(value = "TEMP_LICENCE") Cookie cookie, @RequestParam String username) {
        if (cookie == null) {
            return new Result(false, "Cookie miss.", -3);
        }
        return loginService.resendVerifyEmail(username, cookie.getValue());
    }

    @PostMapping("/login")
    public Result login(@CookieValue(value = "KAPTCHA") Cookie cookie, @RequestBody @Validated LoginDTO loginDTO, HttpServletResponse response) {
        if (ObjectUtils.isEmpty(cookie)) {
            return new Result(false, "Cookie miss.", -1);
        }
        Result codeResult = loginService.checkKaptcha(cookie.getValue(), loginDTO.getCode());
        if (codeResult.getCode() != 0){
            return codeResult;
        }
        return loginService.login(loginDTO, response);
    }

    @RequestMapping("/register/verify")
    public Result verifyEmail(@RequestBody @Validated VerificationDTO verificationDTO) {
        return loginService.registerVerifyEmail(verificationDTO);
    }

    @GetMapping("/logout")
    public Result logout(@CookieValue(required = false, value = "TOKEN") Cookie cookie) {
        return loginService.logout(cookie);
    }

    @PostMapping("/password/forget")
    public Result forgetPassword(@RequestBody @Validated ForgetPasswordDTO forgetPasswordDTO) {
        return loginService.forgetPassword(forgetPasswordDTO);
    }


    @PostMapping("/password/modify")
    public Result modifyPassword(@RequestBody @Validated ModifyPasswordDTO modifyPasswordDTO) {
        return loginService.modifyPassword(modifyPasswordDTO);
    }

    @GetMapping("/kaptcha/image")
    public void getKaptchaImage(HttpServletResponse response) {
        RenderedImage image = null;
        try {
            image = loginService.getKaptchaImage(response);
            response.setContentType("image/jpeg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new ImagingOpException("Output Image Exception.");
        }
    }
}
