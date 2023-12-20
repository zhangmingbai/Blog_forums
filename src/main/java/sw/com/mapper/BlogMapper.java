package sw.com.mapper;

import sw.com.entity.dto.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 张培辉
* @description 针对表【blog(博客信息)】的数据库操作Mapper
* @createDate 2023-12-15 23:21:23
* @Entity sw.com.entity.dto.Blog
*/
public interface BlogMapper extends BaseMapper<Blog> {

    List<Blog> selectAll(Blog blog);

    void updateBlog(Blog blog);

    Blog selectById(Integer id);
}




