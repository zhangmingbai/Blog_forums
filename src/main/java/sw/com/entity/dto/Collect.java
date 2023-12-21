package sw.com.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 收藏
 * @TableName collect
 */
@TableName(value ="collect")
@Data
public class Collect implements Serializable {
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
     * 收藏人ID
     */
    private Integer userId;

    /**
     * 模块
     */
    private String module;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}