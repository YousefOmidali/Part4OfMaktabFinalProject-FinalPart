package com.maktabProject.Part4MaktabFinalProject.Final.part.config;

import com.maktabProject.Part4MaktabFinalProject.Final.part.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(username ->
//                        userDetailsService
//                                .loadUserByUsername(username))
//                .passwordEncoder(passwordEncoder());
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/comment/**").hasAnyAuthority("ADMIN", "CUSTOMER")
                .antMatchers("/order/numberOfCustomerOrder").hasAnyAuthority("ADMIN", "CUSTOMER")
                .antMatchers("/order/allOrdersOfACustomer").hasAnyAuthority("ADMIN", "CUSTOMER")
                .antMatchers("/order/acceptOrder").hasAuthority("CUSTOMER")
                .antMatchers("/order/saveOrder").hasAnyAuthority("CUSTOMER","ADMIN")
                .antMatchers("/order/customerMustPayOrders").hasAuthority("CUSTOMER")
                .antMatchers("/order/finishedOrder").hasAuthority("EXPERT")
                .antMatchers("/order/allOrdersAnExpertCanSee").hasAuthority("EXPERT")
                .antMatchers("/Service/serviceRegister").hasAuthority("ADMIN")
                .antMatchers("/Service/serviceRegister").hasAuthority("ADMIN")
                .antMatchers("/subService/subServiceRegister").hasAuthority("ADMIN")
                .antMatchers("/subService/takeASubService").hasAnyAuthority("ADMIN","EXPERT")
                .antMatchers("/suggestion/save").hasAuthority("EXPERT")
                .antMatchers("/suggestion/ofAnOrder").hasAnyAuthority("CUSTOMER","ADMIN")
                .antMatchers("/wallet/save").hasAnyAuthority("CUSTOMER","ADMIN")
//                .antMatchers("/role/**").hasAuthority("ADMIN")
                .antMatchers("/users/search").hasAuthority("ADMIN")
                .antMatchers("/users/payOrder").hasAuthority("CUSTOMER")
                .antMatchers("/users/payOrderByCard").hasAuthority("CUSTOMER")
                .antMatchers("/users/allAwaitingApprovalUsers").hasAuthority("ADMIN")
                .antMatchers("/users/acceptUser").hasAuthority("ADMIN")
                .antMatchers("/users/signUpTime").hasAuthority("ADMIN")
                .antMatchers("/users/adminMenu").hasAuthority("ADMIN")
                .antMatchers("/users/expertMenu").hasAuthority("EXPERT")
                .antMatchers("/users/customerMenu").hasAuthority("CUSTOMER")
                .antMatchers("/users/verify").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin().and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/users/404");
    }
}
