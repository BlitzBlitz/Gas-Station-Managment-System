package com.ikubinfo.Internship.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
                .authorizeRequests()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/*").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/authenticate").permitAll()
                .anyRequest().authenticated().and().httpBasic()

                .and()
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .antMatchers("/admins").hasRole("ADMIN")
//                .antMatchers("/financiers").hasRole("ADMIN")
//                .antMatchers("/fuels").hasRole("ADMIN")
//                .antMatchers("/workers").hasRole("ADMIN")
//
//                .antMatchers("/financiers/{financierName}").hasRole("FINANCIER")
//                .antMatchers("/financiers/{financierName}/collectDailyTotal").hasRole("FINANCIER")
//                .antMatchers("/financiers/{financierName}/invest").hasRole("FINANCIER")
//                .antMatchers("/statistics").hasRole("FINANCIER")
//                .antMatchers("/workers/balances").hasRole("FINANCIER")
//                .antMatchers("/fuels/{fuelType}").hasRole("FINANCIER")
//
//
//                .antMatchers("/workers/{workerName}").hasRole("WORKER")
//                .antMatchers("/workers/{workerName}/processOrder").hasRole("WORKER")
//                .antMatchers("/workers/{workerName}/processOrder").hasRole("WORKER")
               ;
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
