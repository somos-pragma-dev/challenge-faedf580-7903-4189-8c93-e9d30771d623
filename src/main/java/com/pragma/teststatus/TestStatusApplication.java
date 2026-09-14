package com.pragma.teststatus;

import com.pragma.teststatus.infrastructure.TestStatusRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.validation.Validator;
import java.time.Clock;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = TestStatusRepository.class)
public class TestStatusApplication implements WebMvcConfigurer {
    
    private final TestStatusRepository testStatusRepository;
    
    public TestStatusApplication(TestStatusRepository testStatusRepository) {
        this.testStatusRepository = testStatusRepository;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(TestStatusApplication.class, args);
    }
    
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
    
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
    
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
    }
    
    private static class LoggingInterceptor implements org.springframework.web.servlet.HandlerInterceptor {
        @Override
        public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, 
                                  jakarta.servlet.http.HttpServletResponse response, 
                                  Object handler) {
            long startTime = System.currentTimeMillis();
            request.setAttribute("startTime", startTime);
            return true;
        }
        
        @Override
        public void postHandle(jakarta.servlet.http.HttpServletRequest request,
                               jakarta.servlet.http.HttpServletResponse response,
                               Object handler,
                               org.springframework.web.servlet.ModelAndView modelAndView) {
            long startTime = (Long) request.getAttribute("startTime");
            long duration = System.currentTimeMillis() - startTime;
            if (duration > 5000) {
                System.warn("La solicitud excede el tiempo máximo de 5 segundos: " + duration + "ms para " + request.getRequestURI());
            }
        }
    }
}