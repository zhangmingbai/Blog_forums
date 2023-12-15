package sw.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sw.com.entity.dto.Notice;

import java.util.List;

/**
* @author 张培辉
* @description 针对表【notice(公告信息表)】的数据库操作Mapper
* @createDate 2023-12-12 21:09:09
* @Entity  sw.com.entity.dto.Notice
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    List<Notice> selectAll(Notice notice);
}




