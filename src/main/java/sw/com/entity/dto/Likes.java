package sw.com.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 点赞
 * @TableName likes
 */
@TableName(value ="likes")
@Data
public class Likes implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关联ID
     */
    private Integer fid;

    /**
     * 点赞人ID
     */
    private Integer userId;

    /**
     * 模块
     */
    private String module;
}