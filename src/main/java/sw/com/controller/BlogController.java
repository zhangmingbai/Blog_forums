package sw.com.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Blog;
import sw.com.service.BlogService;

import java.util.List;

/**
 * 博客信息前端操作接口
 **/
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    //新增
    @PostMapping("/add")
    public RestBean<Blog> add(@RequestBody Blog blog) {
        blog.setDate(DateUtil.today());
        blogService.save(blog);
        return RestBean.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public RestBean<Blog> deleteById(@PathVariable Integer id) {
        blogService.removeById(id);
        return RestBean.success();
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public RestBean<Blog> deleteBatch(@RequestBody List<Integer> ids) {
        blogService.removeBatchByIds(ids);
        return RestBean.success();
    }

    //修改
    @PutMapping("/update")
    public RestBean<Blog> updateById(@RequestBody Blog blog) {
        blogService.update(blog);
        return RestBean.success();
    }

    //根据ID查询
    @GetMapping("/selectById/{id}")
    public RestBean<Blog> selectById(@PathVariable Integer id) {
        return RestBean.success(blogService.selectById(id));
    }

    //查询所有
    @GetMapping("/selectAll")
    public RestBean<List<Blog>> selectAll() {
        return RestBean.success(blogService.list());
    }
    //分页查询
    @GetMapping("/selectPage")
    public RestBean<PageInfo<Blog>> selectByPage(Blog Blog,
                                                     @RequestParam Integer pageNum,
                                                     @RequestParam Integer pageSize) {
        return RestBean.success(blogService.selectPage(Blog,pageNum, pageSize));
    }

    /**
     * 博客榜单
     */
    @GetMapping("/selectTop")
    public RestBean<List<Blog>> selectTop() {
        List<Blog> list = blogService.selectTop();
        return RestBean.success(list);
    }
}
