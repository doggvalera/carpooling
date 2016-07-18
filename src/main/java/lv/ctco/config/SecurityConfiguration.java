package lv.ctco.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/signupform.html", "/js/**").permitAll()
                .antMatchers("/offers/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.html").permitAll()
                .and()
                .httpBasic().and()
                .logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()
                .csrf().disable();
                httpSecurity.headers().frameOptions().disable();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select EMAIL, PASSWORD, 1 from USERS where EMAIL=?")
                .authoritiesByUsernameQuery(
                        "select EMAIL, user_role " +
                                "from users u " +
                                "INNER JOIN user_roles ur ON ur.userfk = u.id " +
                                "where u.EMAIL=?");
    }
}

