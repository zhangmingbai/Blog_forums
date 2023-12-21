package sw.com.service;

import com.github.pagehelper.PageInfo;
import sw.com.entity.dto.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
* @author 张培辉
* @description 针对表【blog(博客信息)】的数据库操作Service
* @createDate 2023-12-15 23:21:23
*/
public interface BlogService extends IService<Blog> {

    PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize);
    //修改
    void update(Blog blog);

    List<Blog> selectTop();

    Blog selectById(Integer id);

    Set<Blog> selectRecommend(Integer blogId);
}
