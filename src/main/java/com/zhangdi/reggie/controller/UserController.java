package com.zhangdi.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhangdi.reggie.common.R;
import com.zhangdi.reggie.entity.User;
import com.zhangdi.reggie.service.UserService;
import com.zhangdi.reggie.utils.SMSUtils;
import com.zhangdi.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;



import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 短信验证码
     * @param user
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) throws Exception {
        //获取手机号
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成随机4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用阿里云的短信服务API完成发送短信
            //SMSUtils.sendMessage("OutdoorOasis","SMS_460850084",phone,code);
            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);

            return R.success("SMS verification code has been successfully sent to your mobile phone.");
        }
        return R.error("SMS delivery failed");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {

            log.info(map.toString());
            //获取手机号
            String phone = map.get("phone").toString();
            //获取验证码
            String code = map.get("code").toString();
            //从session中获取保存的验证码
            Object codeInSession = session.getAttribute(phone);
            //进行验证码的比对（页面提交的验证码和session中保存的验证码对比）
            if(codeInSession!=null&&codeInSession.equals(code)){
                //如果能够比对成功，说明登录成功
                LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(User::getPhone,phone);
                User user = userService.getOne(queryWrapper);
                if(user==null){
                    //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                    user=new User();
                    user.setPhone(phone);
                    user.setStatus(1);
                    userService.save(user);
                }
                session.setAttribute("user",user.getId());
                return R.success(user);


            }

            return R.error("Login failed");
    }

}





