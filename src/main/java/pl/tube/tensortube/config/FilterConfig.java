package pl.tube.tensortube.config;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tube.tensortube.filter.LoginFilter;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("/secured/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration(Filter fileUploadFilter) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(fileUploadFilter);
        registration.addUrlPatterns("/*");
        registration.addInitParameter("thresholdSize", "2147483647");
        registration.setName("PrimeFaces FileUpload Filter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public Filter fileUploadFilter() {
        return new FileUploadFilter();
    }
}
