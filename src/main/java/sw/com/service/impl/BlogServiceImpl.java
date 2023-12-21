package sw.com.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import sw.com.entity.dto.Account;
import sw.com.entity.dto.Blog;
import sw.com.entity.dto.Collect;
import sw.com.entity.dto.Likes;
import sw.com.entity.enums.LinkesModuleEnum;
import sw.com.mapper.BlogMapper;
import sw.com.service.AccountService;
import sw.com.service.BlogService;
import sw.com.service.CollectService;
import sw.com.service.LikesService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Resource
    AccountService accountService;

    @Resource
    LikesService likesService;

    @Resource
    CollectService collectService;

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
        Blog blog = blogMapper.selectById(id);  //获取博客信息
        Account account = accountService.getById(blog.getUserId());  //获取发布人信息
        blog.setAccount(account);  //设置发布人信息
        // 查询当前博客的点赞数据
        int likesCount = likesService.selectByFidAndModule(id, LinkesModuleEnum.BLOG.getValue());
        blog.setLikesCount(likesCount);  //设置点赞数
        Likes likes = likesService.selectUserLikes(id, LinkesModuleEnum.BLOG.getValue());
        blog.setUserLike(likes != null);  //设置当前用户是否点赞

        // 查询当前博客的收藏数据
        int collectCount = collectService.selectByFidAndModule(id, LinkesModuleEnum.BLOG.getValue());
        blog.setCollectCount(collectCount);
        Collect userCollect = collectService.selectUserCollect(id, LinkesModuleEnum.BLOG.getValue());
        blog.setUserCollect(userCollect != null);
        return blog;
    }

    @Override
    public Set<Blog> selectRecommend(Integer blogId) {  //获取推荐博客
        Blog blog = this.selectById(blogId);  //获取当前博客信息
        String tags = blog.getTags();  //获取当前博客标签
        Set<Blog> blogSet = new HashSet<>();  //创建博客集合
        if (ObjectUtil.isNotEmpty(tags)) {  //如果当前博客标签不为空
            List<Blog> blogList = blogMapper.selectAll(null);  //获取所有博客信息
            JSONArray tagsArr = JSONUtil.parseArray(tags);  //将当前博客标签转换为JSON数组
            for (Object tag : tagsArr) {
                // 筛选出包含当前博客标签的其他的博客列表
                Set<Blog> collect = blogList.stream().filter(b -> b.getTags().contains(tag.toString()) && !blogId.equals(b.getId()))
                        .collect(Collectors.toSet());
                blogSet.addAll(collect);  //将筛选出的博客列表添加到博客集合中
            }
        }
        return blogSet.stream().limit(5).collect(Collectors.toSet());  //返回前5条博客信息
    }
}




