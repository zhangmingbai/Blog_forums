package sw.com.utils;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import sw.com.entity.vo.AuthorizeVO;

@Component
public class UserIdUtil {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Value("${spring.security.jwt.key}")
    String key;

    public Integer getUserId() {
        //获取登录用户的id
        String userJson = stringRedisTemplate.opsForValue().get(key);  //获取登录用户的信息
        AuthorizeVO vo = JSON.parseObject(userJson, AuthorizeVO.class);  //将json转换为对象
        if (vo != null) {  //如果登录用户不为空
            return vo.getId();
        }
        return null;
    }
}
