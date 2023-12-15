package sw.com.service;

import com.github.pagehelper.PageInfo;
import sw.com.entity.dto.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 张培辉
* @description 针对表【category(博客分类)】的数据库操作Service
* @createDate 2023-12-15 21:26:32
*/
public interface CategoryService extends IService<Category> {

    PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize);
}
