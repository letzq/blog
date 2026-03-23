package com.xy.blog.system.service;
import com.xy.blog.system.dto.BlogLoginDto;
import com.xy.blog.system.dto.EmailCodeSendDto;
import com.xy.blog.system.dto.EmailLoginDto;
import com.xy.blog.system.dto.RegisterDto;
import com.xy.blog.system.vo.BlogLoginVo;
import com.xy.blog.system.vo.BlogUserVo;
import com.xy.blog.system.vo.CaptchaVo;
/**
 * ???????
 */
public interface IBlogAuthService {
    /**
     * ????????????
     */
    CaptchaVo generateLoginCaptcha();
    /**
     * ???????
     */
    BlogLoginVo loginByPassword(BlogLoginDto dto);
    /**
     * ????????
     */
    void sendEmailCode(EmailCodeSendDto dto);
    /**
     * ??????????
     */
    BlogLoginVo loginByEmail(EmailLoginDto dto);
    /**
     * ??????
     */
    BlogUserVo register(RegisterDto dto);
}