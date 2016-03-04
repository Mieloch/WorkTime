package topworker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import topworker.model.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan("topworker.model.service.impl")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/*").permitAll().and().formLogin().loginPage("/login").defaultSuccessUrl("/#!calendar", true).usernameParameter("login").passwordParameter("password").and().csrf();
        // TODO Auto-generated method stub
       /* http.csrf().disable().authorizeRequests().antMatchers("*//**").hasRole("USER").and().formLogin()
                .defaultSuccessUrl("/#!calendar", true);*/
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth.userDetailsService(userDetailsService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
