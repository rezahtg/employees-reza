package com.mcc53jpa.securities;

import com.mcc53jpa.models.Role;
import com.mcc53jpa.services.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private AppUserDetailService appUserDetailService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                            AppUserDetailService appUserDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailService = appUserDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailService).passwordEncoder(passwordEncoder);
    }


    //Overide Method Config(Web:HTTP):Void
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // untuk menghapus token x-xsrf-token
                /**
                 * HTTP BASIC menggunakan prompt untuk authorize
                 * tidak bisa logout
                 */
                .authorizeRequests() // -> we want authorizeRequest
                /**
                 * Manual Roles & Permission
                 */
//                .antMatchers( "/", "index") // membuat whitelist
//                .permitAll() // -> memberikan izin pada semua
//                .antMatchers("/department")
//                .hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/department")
//                .hasAnyAuthority("DEPARTMENT_READ")
//                .antMatchers("/employee").hasRole(EMPLOYEE.name())
//                .antMatchers("/department").hasRole(ADMIN.name()) // -> memberi permission pada role admin
                /**
                 * Code dibawah jika dibaca:
                 * whitelist Method Delet pada endpoint department
                 * kemudian cek, apakah user memiliki authority untuk melakuakn methode delete atau tdk
                 * jika tidak, makan akan tampil forbiden.
                 */
//                .antMatchers(HttpMethod.DELETE,"/department/**").hasAuthority(DEPARTMENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/department/**").hasAuthority(DEPARTMENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/department/**").hasAuthority(DEPARTMENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/department/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**", "/employee/**","/project/**","/department/**").permitAll()
                .anyRequest() // -> anyrequest
                .authenticated() // -> must be authenticated with spesify username & password
                .and() // -> and mechanism we want use is
                .httpBasic(); // -> httpbasic
    }

//    @Override
//    @Bean
//    /**
//     * userDetailService method ini digunakan untuk
//     * menunjukkan bagaimana kita akan mengambil user dari database
//     */
//    protected UserDetailsService userDetailsService() {
//        //mendefinisikan user
//        UserDetails rezaUser = User.builder()
//                .username("reza")
//                .password(passwordEncoder.encode("admin"))
////                .roles(EMPLOYEE.name()) //ROLE_EMPLOYEE
//                .authorities(EMPLOYEE.getGrantedAuthorities())
//                .build();
//
//        UserDetails adminUser = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin123"))
////                .roles(ADMIN.name()) //ROLE_ADMIN
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails adminMagangUser = User.builder()
//                .username("admin2")
//                .password(passwordEncoder.encode("admin123"))
////                .roles(ADMINMAGANG.name()) //ROLE_ADMIN_MAGANG
//                .authorities(ADMINMAGANG.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                rezaUser,
//                adminUser,
//                adminMagangUser
//        );
//    }
}
