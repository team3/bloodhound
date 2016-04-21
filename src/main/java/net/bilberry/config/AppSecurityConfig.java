package net.bilberry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder security) throws Exception {
        security.inMemoryAuthentication().withUser("user").password("user").roles("USER");
        security.inMemoryAuthentication().withUser("recruiter").password("recruiter").roles("RECRUITER", "MANAGER");
        security.inMemoryAuthentication().withUser("manager").password("manager").roles("MANAGER");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/search/**").access("hasRole('ROLE_RECRUITER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_MANAGER')")
                .and().formLogin().defaultSuccessUrl("/", false);
    }

}
