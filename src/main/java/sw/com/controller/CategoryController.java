package sw.com.controller;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Category;
import sw.com.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    //新增
    @PostMapping("/add")
    public RestBean<Category> add(@RequestBody Category category) {
        categoryService.save(category);
        return RestBean.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public RestBean<Category> deleteById(@PathVariable Integer id) {
        categoryService.removeById(id);
        return RestBean.success();
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public RestBean<Category> deleteBatch(@RequestBody List<Integer> ids) {
        categoryService.removeBatchByIds(ids);
        return RestBean.success();
    }

    //修改
    @PutMapping("/update")
    public RestBean<Category> updateById(@RequestBody Category category) {
        categoryService.updateById(category);
        return RestBean.success();
    }

    //根据ID查询
    @GetMapping("/selectById/{id}")
    public RestBean<Category> selectById(@PathVariable Integer id) {
        return RestBean.success(categoryService.getById(id));
    }

    //查询所有
    @GetMapping("/selectAll")
    public RestBean<List<Category>> selectAll() {
        return RestBean.success(categoryService.list());
    }

    //分页查询
    @GetMapping("/selectPage")
    public RestBean<PageInfo<Category>> selectByPage(Category category,
                                                   @RequestParam Integer pageNum,
                                                   @RequestParam Integer pageSize) {
        return RestBean.success(categoryService.selectPage(category,pageNum, pageSize));
    }
}
