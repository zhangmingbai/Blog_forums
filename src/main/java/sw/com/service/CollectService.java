package sw.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sw.com.entity.dto.Collect;

/**
* @author 张培辉
* @description 针对表【collect(收藏)】的数据库操作Service
* @createDate 2023-12-21 16:04:56
*/
public interface CollectService extends IService<Collect> {

    void set(Collect collect);
    Collect selectUserCollect(Integer fid,String module);

    int selectByFidAndModule(Integer fid, String module);

}
