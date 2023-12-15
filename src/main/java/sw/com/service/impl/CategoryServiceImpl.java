package sw.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import sw.com.entity.dto.Category;
import sw.com.service.CategoryService;
import sw.com.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 张培辉
* @description 针对表【category(博客分类)】的数据库操作Service实现
* @createDate 2023-12-15 21:26:32
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(categoryMapper.selectAll(category));
    }
}




