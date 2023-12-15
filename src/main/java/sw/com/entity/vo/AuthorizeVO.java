package sw.com.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * 登录验证成功的用户信息响应
 */
@Data
public class AuthorizeVO {
    Integer id;
    String username;
    String name;
    String avatar;
    String role;
    String phone;
    String email;
    String token;
    Date expire;
}
