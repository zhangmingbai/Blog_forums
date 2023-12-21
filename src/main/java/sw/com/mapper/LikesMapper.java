package sw.com.mapper;

import sw.com.entity.dto.Likes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 张培辉
* @description 针对表【likes(点赞)】的数据库操作Mapper
* @createDate 2023-12-20 22:22:55
* @Entity sw.com.entity.dto.Likes
*/
public interface LikesMapper extends BaseMapper<Likes> {

    int selectByFidAndModule(Integer fid, String module);

    Likes selectUserLikes(Likes likes);
}




