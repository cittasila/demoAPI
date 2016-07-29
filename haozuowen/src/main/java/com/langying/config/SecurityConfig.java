package com.langying.config;



import com.langying.controller.mapper.UUserMapper;
import com.langying.controller.security.AuthenticationProviderCustom;
import com.langying.controller.security.UserSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全认证
 */

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableRedisHttpSession
class SecurityConfig extends WebSecurityConfigurerAdapter{

    private Http401AuthenticationEntryPoint authenticationEntryPoint;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UUserMapper uUserMapper;


    @Bean
    public UserSecurityService userSecurityService(){
       return new UserSecurityService(uUserMapper);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthenticationProviderCustom(userSecurityService());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    Http401AuthenticationEntryPoint getAuth(){
        authenticationEntryPoint=new Http401AuthenticationEntryPoint("Ream");
        return authenticationEntryPoint;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and().logout().and().authorizeRequests()
                .antMatchers( "/msg/","/v2/api-docs","/userManage/*","/common/sendMsg","/common/verMsg","/hzwReport/report","/user/changePswByKey","/common/uuid","/common/getSecurityCode","/pay/qrcode/*","/pay/return","/pay/notify/*"
                ,"/user/checkUniq","/user/register","/lyTask/*","/test/*").permitAll().anyRequest()
                .authenticated()
                .and().httpBasic().authenticationEntryPoint(getAuth())
                .and().csrf()
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
        http.csrf().disable();
        /*
        http.formLogin().and()
                .logout().and().authorizeRequests()
                .antMatchers("/index.html", "/login.html", "/").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf()
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
                */

    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() throws Exception {
        final XBasicAuthenticationEntryPoint ngAuthEntryPoint = new XBasicAuthenticationEntryPoint();
        ngAuthEntryPoint.setRealmName("Ream");

        final BasicAuthenticationEntryPoint basicAuthEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthEntryPoint.setRealmName("Ream");

        /* LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> authMap = [:]
         authMap.put(new RequestHeaderRequestMatcher("X-Request-Auth", "NgBasic"), ngAuthEntryPoint);


         final DelegatingAuthenticationEntryPoint delegatingAuthEntryPoint =
                 new DelegatingAuthenticationEntryPoint(authMap);
         delegatingAuthEntryPoint.setDefaultEntryPoint(basicAuthEntryPoint);*/

        return basicAuthEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }



    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    public class XBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException)
                throws IOException, ServletException {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    authException.getMessage());
        }
    }
}

