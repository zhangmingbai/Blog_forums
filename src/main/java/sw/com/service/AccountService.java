package sw.com.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import sw.com.entity.dto.Account;
import sw.com.entity.vo.ConfirmResetVO;
import sw.com.entity.vo.EmailRegisterVO;
import sw.com.entity.vo.EmailResetVO;

public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String text);
    String registerEmailVerifyCode(String type, String email, String address);
    String registerEmailAccount(EmailRegisterVO info);
    String resetEmailAccountPassword(EmailResetVO info);
    String resetConfirm(ConfirmResetVO info);
}
