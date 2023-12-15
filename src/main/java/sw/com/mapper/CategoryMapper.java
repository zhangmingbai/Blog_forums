package sw.com.mapper;

import sw.com.entity.dto.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 张培辉
* @description 针对表【category(博客分类)】的数据库操作Mapper
* @createDate 2023-12-15 21:26:32
* @Entity sw.com.entity.dto.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> selectAll(Category category);
}




