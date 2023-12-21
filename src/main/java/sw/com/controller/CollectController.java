package sw.com.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Collect;
import sw.com.service.CollectService;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Resource
    CollectService collectService;

    // 收藏和取消
    @PostMapping("/set")
    public RestBean<Collect> set(@RequestBody Collect collect) {
        collectService.set(collect);
        return RestBean.success();
    }
}
