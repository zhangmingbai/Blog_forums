package sw.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import sw.com.entity.dto.Notice;
import sw.com.mapper.NoticeMapper;
import sw.com.service.NoticeService;

import java.util.List;

/**
* @author 张培辉
* @description 针对表【notice(公告信息表)】的数据库操作Service实现
* @createDate 2023-12-12 21:09:09
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService{
    @Resource
    NoticeMapper noticeMapper;
    @Override
    public PageInfo<Notice> selectPage(Notice notice,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }
}




