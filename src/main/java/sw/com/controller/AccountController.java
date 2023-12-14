package sw.com.controller;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Account;
import sw.com.service.AccountService;

@RestController
@RequestMapping("/user")
public class AccountController {
    @Resource
    AccountService accountService;

    //新增
    @PostMapping("/add")
    public RestBean<Void> add(@RequestBody Account account) {
        accountService.save(account);
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
