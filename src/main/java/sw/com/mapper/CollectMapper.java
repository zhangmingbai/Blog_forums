package sw.com.mapper;

import sw.com.entity.dto.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 张培辉
* @description 针对表【collect(收藏)】的数据库操作Mapper
* @createDate 2023-12-21 16:04:56
* @Entity sw.com.entity.dto.Collect
*/
public interface CollectMapper extends BaseMapper<Collect> {

    Collect selectUserCollect(Collect collect);

    int selectByFidAndModule(Integer fid, String module);
}




