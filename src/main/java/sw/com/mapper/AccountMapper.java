package sw.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sw.com.entity.dto.Account;

import java.util.List;

/**
* @author 张培辉
* description  针对表【account】的数据库操作Mapper
* createDate 2023-11-14 21:29:47
* Entity sw.com.entity.dto.Account
*/

public interface AccountMapper extends BaseMapper<Account> {
    List<Account> selectAll(Account account);
}




