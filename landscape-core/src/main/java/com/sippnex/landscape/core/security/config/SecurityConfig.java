package com.sippnex.landscape.core.security.config;

import com.sippnex.landscape.core.security.service.LocalUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LocalUserDetailsService localUserDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(LocalUserDetailsService localUserDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.localUserDetailsService = localUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth.userDetailsService(localUserDetailsService).passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.headers().contentTypeOptions().disable();
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/core/**", "/fileblade/download/**")
                .authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/api/admin/**", "/fileblade/browse/**", "/fileblade/upload/**" )
                .hasRole("ADMIN")
                .and()
                .formLogin()
                .disable()
                .httpBasic();

    }

}
