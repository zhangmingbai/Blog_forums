package sw.com.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 博客信息
 * @TableName blog
 */
@TableName(value ="blog")
@Data
public class Blog implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 简介
     */
    private String descr;

    /**
     * 封面
     */
    private String cover;

    /**
     * 标签
     */
    private String tags;

    /**
     * 发布人ID
     */
    private Integer userId;

    /**
     * 发布日期
     */
    private String date;

    /**
     * 浏览量
     */
    private Integer readCount;

    /**
     * 分类ID
     */
    private Integer categoryId;  // 分类ID
    private String categoryName;  // 分类名称
    private String userName;  // 发布人姓名
    private Account account;  // 发布人信息

    private Integer likesCount;  // 点赞数

    private Boolean userLike;  // 当前用户是否点赞

    private Integer collectCount;   // 收藏数

    private Boolean userCollect;  // 当前用户是否收藏

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}