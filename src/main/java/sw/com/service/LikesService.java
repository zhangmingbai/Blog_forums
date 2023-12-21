package sw.com.service;


import sw.com.entity.dto.Likes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 张培辉
* @description 针对表【likes(点赞)】的数据库操作Service
* @createDate 2023-12-20 22:22:55
*/
public interface LikesService extends IService<Likes> {

    void set(Likes likes);
    Likes selectUserLikes(Integer fid,String module);

    int selectByFidAndModule(Integer fid, String module);
}
