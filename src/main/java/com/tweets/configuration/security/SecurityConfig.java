//package com.tweets.configuration.security;
//
//import com.tweets.service.SecUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.csrf.CsrfFilter;
//import org.springframework.security.web.csrf.CsrfTokenRepository;
//import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private LogoutSuccess logoutSuccess;
//
//    @Autowired
//    private AuthenticationSuccess authSuccess;
//
//    @Autowired
//    private EntryPointConfig entryPointConfig;
//
//    @Autowired
//    private AuthenticationFailedHandler authFailHandler;
//
//    @Autowired
//    SecUserDetailsService userDetailsService ;
//
//    @Autowired
//    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(userDetailsService);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling()
//                .authenticationEntryPoint(entryPointConfig)
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/static/app/**").authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/static/login.html")
//                .permitAll()
//                .successHandler(authSuccess)
//                .failureHandler(authFailHandler)
//                .loginProcessingUrl("/auth/login")
//                .defaultSuccessUrl("/static/index1", true)
//                .and()
//                .logout()
//                .logoutUrl("/auth/logout")
//                .logoutSuccessHandler(logoutSuccess)
//                .and()
//                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
//    }
//
//    private CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
