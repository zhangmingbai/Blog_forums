package sw.com.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用于处理Jwt令牌的工具类
 */
@Component
public class JwtUtils {

    //用于给Jwt令牌签名校验的秘钥
    @Value("${spring.security.jwt.key}")
    private String key;
    //令牌的过期时间，以小时为单位
    @Value("${spring.security.jwt.expire}")
    private int expire;

    @Resource
    StringRedisTemplate template;

    /**
     * 让指定Jwt令牌失效
     * @param headerToken 请求头中携带的令牌
     * @return 是否操作成功
     */
    public boolean invalidateJwt(String headerToken){  // 使令牌失效
        String token = this.convertToken(headerToken); // 转换令牌
        Algorithm algorithm = Algorithm.HMAC256(key);  // 签名算法
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();  // 构建JWT验证器
        try {
            DecodedJWT verify = jwtVerifier.verify(token);  // 验证令牌
            return deleteToken(verify.getId(), verify.getExpiresAt());  // 将令牌列入黑名单
        } catch (JWTVerificationException e) {
            return false;  // 验证失败
        }
    }

    /**
     * 根据配置快速计算过期时间
     * @return 过期时间
     */
    public Date expireTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expire);
        return calendar.getTime();
    }

    /**
     * 根据UserDetails生成对应的Jwt令牌
     * @param user 用户信息
     * @return 令牌
     */
    public String createJwt(UserDetails user,int userId, String username ) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        Date expire = this.expireTime();
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("id", userId)
                .withClaim("name", username)
                .withClaim("authorities", user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .withExpiresAt(expire)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    /**
     * 校验并转换请求头中的Token令牌
     * @param headerToken 请求头中的Token
     * @return 转换后的令牌
     */
    public String convertToken(String headerToken){
        if(headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }

    /**
     * 解析Jwt令牌
     * @param headerToken 请求头中携带的令牌
     * @return DecodedJWT
     */
    public DecodedJWT resolveJwt(String headerToken){  // 解析令牌
        String token = this.convertToken(headerToken);  // 转换令牌
        if(token == null) return null;  // 令牌为空
        Algorithm algorithm = Algorithm.HMAC256(key);  // 签名算法
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();  // 构建JWT验证器
        try {  // 验证令牌
            DecodedJWT verify = jwtVerifier.verify(token);  // 验证令牌
            if(this.isInvalidToken(verify.getId())) return null;   // 令牌已失效
            Date expiresAt = verify.getExpiresAt();  // 过期时间
            return new Date().after(expiresAt) ? null : verify;  // 令牌已过期
        } catch (JWTVerificationException e) {  // 验证失败
            return null;  // 验证失败
        }
    }

    /**
     * 将jwt对象中的内容封装为UserDetails
     * @param jwt 已解析的Jwt对象
     * @return UserDetails
     */
    public UserDetails toUser(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("name").asString())
                .password("******")
                .authorities(claims.get("authorities").asArray(String.class))
                .build();
    }

    /**
     * 将jwt对象中的用户ID提取出来
     * @param jwt 已解析的Jwt对象
     * @return 用户ID
     */
    public Integer toId(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return claims.get("id").asInt();
    }

    /**
     * 将Token列入Redis黑名单中
     * @param uuid 令牌ID
     * @param time 过期时间
     * @return 是否操作成功
     */
    private boolean deleteToken(String uuid, Date time){
        if(this.isInvalidToken(uuid))
            return false;
        Date now = new Date();
        long expire = Math.max(time.getTime() - now.getTime(), 0);
        template.opsForValue().set(Const.JWT_BLACK_LIST + uuid, "", expire, TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * 验证Token是否被列入Redis黑名单
     * @param uuid 令牌ID
     * @return 是否操作成功
     */
    private boolean isInvalidToken(String uuid){  // 验证令牌是否失效
        return Boolean.TRUE.equals(template.hasKey(Const.JWT_BLACK_LIST + uuid));  // 令牌已失效
    }
}
