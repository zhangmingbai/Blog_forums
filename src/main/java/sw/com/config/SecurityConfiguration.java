package sw.com.config;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sw.com.entity.RestBean;
import sw.com.entity.dto.Account;
import sw.com.entity.vo.AuthorizeVO;
import sw.com.filter.JwtAuthenticationFilter;
import sw.com.service.AccountService;
import sw.com.utils.JwtUtils;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfiguration {
    @Resource
    JwtUtils utils;

    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Resource
    AccountService accountService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(conf ->conf
                    .requestMatchers("/api/auth/**","error","/notice/**","/user/**","/files/**",
                            "/category/**","/blog/**","likes/**","collect/**").permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin(conf ->conf
                    .loginProcessingUrl("/api/auth/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure)
                )
                .logout(conf ->conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .exceptionHandling(conf ->conf
                        .authenticationEntryPoint(this::onUnauthorized)
                        .accessDeniedHandler(this::onAccessDeny)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf ->conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");  // 设置响应类型
        User user = (User) authentication.getPrincipal();  // 获取当前用户
        Account account = accountService.findAccountByNameOrEmail(user.getUsername());  // 获取用户信息
        String token = utils.createJwt(user, account.getId(), account.getUsername());
        AuthorizeVO vo = new AuthorizeVO();
        vo.setId(account.getId());
        vo.setExpire(utils.expireTime());
        vo.setUsername(account.getUsername());
        vo.setName(account.getName());
        vo.setAvatar(account.getAvatar());
        vo.setRole(account.getRole());
        vo.setEmail(account.getEmail());
        vo.setPhone(account.getPhone());
        vo.setToken(token);
        response.getWriter().write(RestBean.success(vo).asJsonString());  // 返回用户信息
    }

    /**
     * 退出登录处理，将对应的Jwt令牌列入黑名单不再使用
     */
    private void onLogoutSuccess(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");  // 设置响应类型
        PrintWriter writer = response.getWriter();  // 获取响应输出流
        String authorization = request.getHeader("Authorization");  // 获取请求头中的令牌
        if(utils.invalidateJwt(authorization)) {  // 使令牌失效
            writer.write(RestBean.success("退出登录成功").asJsonString()); // 返回成功信息
            return;
        }
        writer.write(RestBean.failure(400, "退出登录失败").asJsonString()); // 返回失败信息
    }


    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());

    }

    public void onAccessDeny(HttpServletRequest request,
                             HttpServletResponse response,
                             AccessDeniedException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }
    public void onUnauthorized(HttpServletRequest request,
                               HttpServletResponse response,
                               AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }
}
