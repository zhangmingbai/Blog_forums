package sw.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import sw.com.entity.dto.Blog;
import sw.com.mapper.BlogMapper;
import sw.com.service.BlogService;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 张培辉
* @description 针对表【blog(博客信息)】的数据库操作Service实现
* @createDate 2023-12-15 23:21:23
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{
    @Resource
    BlogMapper blogMapper;

    @Override
    public PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(blogMapper.selectAll(blog));
    }

    @Override
    public void update(Blog blog) {
        blogMapper.updateBlog(blog);
    }

    //榜单查询
    @Override
    public List<Blog> selectTop() {
        List<Blog> blogList = blogMapper.selectAll(null);
        blogList = blogList.stream().sorted((b1, b2) -> b2.getReadCount().compareTo(b1.getReadCount()))
                .limit(10)
                .collect(Collectors.toList());
        return blogList;
    }

    @Override
    public Blog selectById(Integer id) {
        return blogMapper.selectById(id);
    }
}




