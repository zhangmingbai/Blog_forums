package sw.com.mapper;

import org.apache.ibatis.annotations.Mapper;
import sw.com.entity.dto.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 张培辉
* description  针对表【account】的数据库操作Mapper
* createDate 2023-11-14 21:29:47
* Entity sw.com.entity.dto.Account
*/
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}




