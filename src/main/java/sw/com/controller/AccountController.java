package sw.com.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sw.com.entity.RestBean;
import sw.com.service.AccountService;

@Controller
@RequestMapping("/api/auth")
public class AccountController {
    @Resource
    AccountService  service;

    @GetMapping("/ask-code")
    public RestBean<Void> askVerifyCode(@RequestParam @Email String email,
                                        @RequestParam @Pattern(regexp = "(register | reset)") String type,
                                        HttpServletRequest request){
        String message = service.registerEmailVerifyCode(email,type,request.getRemoteAddr());
        return message == null ? RestBean.success() : RestBean.failure(400,message);
    }
}
