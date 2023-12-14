package sw.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sw.com.entity.dto.Notice;

/**
* @author 张培辉
* @description 针对表【notice(公告信息表)】的数据库操作Service
* @createDate 2023-12-12 21:09:09
*/
public interface NoticeService extends IService<Notice> {

    PageInfo<Notice> selectPage(Notice notice,Integer pageNum, Integer pageSize);
}
