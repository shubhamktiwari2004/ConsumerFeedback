package CustomerFeedback.CF;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class config {


    private final Service service;

    public config(Service service) {
        this.service = service;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(auth->auth
                .requestMatchers( "/adminFeedback","/login").permitAll()
                .anyRequest().authenticated()
        );
        http.formLogin(form->form
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/feedback" , true)
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .permitAll()
        );
        http.logout(logout ->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .permitAll()
        );
        http.httpBasic(Customizer.withDefaults());
        http.authenticationProvider(authenticationProvider()); // Use teacher authentication provider

        return http.build();



    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service); // Teacher's UserDetailsService
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Use bcrypt for encoding passwords
        return provider;
    }
}
