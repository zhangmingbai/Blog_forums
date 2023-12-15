package sw.com.controller;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Account;
import sw.com.service.AccountService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountController {
    @Resource
    AccountService accountService;

    //新增
    @PostMapping("/add")
    public RestBean<Void> add(@RequestBody Account account) {
        Account acc = new Account(null,account.getUsername(),new BCryptPasswordEncoder().encode("123456"),account.getName(),account.getAvatar(),
                "user",account.getPhone(),account.getEmail(),new Date());
        accountService.save(acc);
        return RestBean.success();
    }

    //删除
    @DeleteMapping("/delete")
    public RestBean<Void> delete(@RequestParam Integer id) {
        accountService.removeById(id);
        return RestBean.success();
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public RestBean<Void> deleteBatch(@RequestParam List<Integer> ids) {
        accountService.removeBatchByIds(ids);
        return RestBean.success();
    }

    //修改
    @PutMapping("/update")
    public RestBean<Void> update(@RequestBody Account account) {
        accountService.updateById(account);
        return RestBean.success();
    }

    //分页查询所有
    @GetMapping("/selectPage")
    public RestBean<PageInfo<Account>> selectPage(Account account,
                                                  @RequestParam Integer pageNum,
                                                  @RequestParam Integer pageSize) {
        PageInfo<Account> page = accountService.selectPage(account,pageNum, pageSize);
        return RestBean.success(page);
    }
}
