package sw.com.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import sw.com.entity.dto.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 张培辉
* description 针对表【account】的数据库操作Service
* createDate 2023-11-14 21:29:48
*/
public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String text);
    String registerEmailVerifyCode(String type,String email,String ip);
}
