package sw.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import sw.com.entity.dto.Likes;
import sw.com.mapper.LikesMapper;
import sw.com.service.LikesService;
import sw.com.utils.UserIdUtil;

/**
* @author 张培辉
* @description 针对表【likes(点赞)】的数据库操作Service实现
* @createDate 2023-12-20 22:22:55
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService{
    @Resource
    LikesMapper likesMapper;

    @Resource
    UserIdUtil userIdUtil;

    @Override
    public void set(Likes likes) {
        Integer userId = userIdUtil.getUserId();  //获取登录用户的id
        likes.setUserId(userId);  //设置点赞用户的id
        Likes likes1 = likesMapper.selectUserLikes(likes);
        if (likes1 == null) {
            likesMapper.insert(likes);
        } else {
            likesMapper.deleteById(likes1.getId());
        }
    }

    /**
     * 查询当前用户是否点过赞
     */
    public Likes selectUserLikes(Integer fid,String module) {
        Integer userId = userIdUtil.getUserId();
        Likes likes = new Likes();
        likes.setUserId(userId);
        likes.setFid(fid);
        likes.setModule(module);
        return likesMapper.selectUserLikes(likes);
    }

    @Override
    public int selectByFidAndModule(Integer fid, String module) {
        return likesMapper.selectByFidAndModule(fid, module);
    }
}




