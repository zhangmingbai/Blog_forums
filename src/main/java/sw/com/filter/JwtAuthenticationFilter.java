package sw.com.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sw.com.utils.Const;
import sw.com.utils.JwtUtils;

import java.io.IOException;

/**
 * 用于对请求头中Jwt令牌进行校验的工具，为当前请求添加用户验证信息
 * 并将用户的ID存放在请求对象属性中，方便后续使用
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    JwtUtils utils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");  // 获取请求头中的令牌
        DecodedJWT jwt = utils.resolveJwt(authorization);  // 解析令牌
        if(jwt != null) {  // 如果令牌不为空
            UserDetails user = utils.toUser(jwt);  // 将令牌转换为用户信息
            System.out.println(user);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user, user.getAuthorities());  // 构建验证令牌
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // 设置验证令牌的详细信息
            SecurityContextHolder.getContext().setAuthentication(authentication);  // 将验证令牌放入安全上下文
            request.setAttribute(Const.ATTR_USER_ID, utils.toId(jwt));  // 将用户ID放入请求属性
        }
        filterChain.doFilter(request, response);
    }
}
