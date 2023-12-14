package sw.com.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import sw.com.entity.dto.Account;
import sw.com.entity.vo.ConfirmResetVO;
import sw.com.entity.vo.EmailRegisterVO;
import sw.com.entity.vo.EmailResetVO;

public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String text);  // 通过用户名或邮箱查找账户
    String registerEmailVerifyCode(String type, String email, String address); // 发送邮箱验证码
    String registerEmailAccount(EmailRegisterVO info);  // 注册邮箱账户
    String resetEmailAccountPassword(EmailResetVO info);  // 重置邮箱账户密码
    String resetConfirm(ConfirmResetVO info);  // 重置确认

    PageInfo<Account> selectPage(Account account, Integer pageNum, Integer pageSize); // 分页查询账户信息
}
