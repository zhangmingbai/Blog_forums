package sw.com.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Likes;
import sw.com.service.LikesService;

@RestController
@RequestMapping("/likes")
public class LikesController {
    @Resource
    LikesService likesService;

    // 点赞和取消点赞
    @PostMapping("/set")
    public RestBean<Likes> setLikes(@RequestBody Likes likes) {
        likesService.set(likes);
        return RestBean.success();
    }
}
