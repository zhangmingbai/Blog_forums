package sw.com.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * TableName account
 */
@TableName(value ="account")
@Data
@AllArgsConstructor
public class Account implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
    private Date registerTime;
}