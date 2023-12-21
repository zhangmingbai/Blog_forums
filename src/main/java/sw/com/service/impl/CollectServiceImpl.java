package sw.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import sw.com.entity.dto.Collect;
import sw.com.mapper.CollectMapper;
import sw.com.service.CollectService;

/**
* @author 张培辉
* @description 针对表【collect(收藏)】的数据库操作Service实现
* @createDate 2023-12-21 16:04:56
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
    implements CollectService{

    @Resource
    CollectMapper collectMapper;

    public void set(Collect collect) {
        collect.setUserId(collect.getUserId());
        Collect collect1 = collectMapper.selectUserCollect(collect);
        if (collect1 == null) {
            collectMapper.insert(collect);
        } else {
            collectMapper.deleteById(collect1.getId());
        }
    }

    /**
     * 查询当前用户是否收藏过
     */
    public Collect selectUserCollect(Integer fid, String module) {
        Collect collect = new Collect();
        collect.setFid(fid);
        collect.setModule(module);
        return collectMapper.selectUserCollect(collect);
    }

    public int selectByFidAndModule(Integer fid, String module) {
        return collectMapper.selectByFidAndModule(fid, module);
    }
}




