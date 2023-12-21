package sw.com;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import sw.com.entity.vo.AuthorizeVO;

@SpringBootTest
class SpringbootApplicationTests {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Value("${spring.security.jwt.key}")
    String key;


    @Test
    void contextLoads() {
        String userJson = stringRedisTemplate.opsForValue().get(key);
        AuthorizeVO vo = JSON.parseObject(userJson, AuthorizeVO.class);
        if (vo != null) {
            Integer userId = vo.getId();
            System.out.println(userId);
        }
    }
}
