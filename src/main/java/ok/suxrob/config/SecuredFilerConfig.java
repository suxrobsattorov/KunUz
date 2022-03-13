package ok.suxrob.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilerConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> filterRegistrationBean() {

        FilterRegistrationBean<JwtTokenFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/article");
        bean.addUrlPatterns("/article/action/*");
        bean.addUrlPatterns("/profile");
        bean.addUrlPatterns("/profile/actoin/*");
        bean.addUrlPatterns("/comment");
        bean.addUrlPatterns("/comment/action/*");
        bean.addUrlPatterns("/comment/commentByAdmin");
        bean.addUrlPatterns("/region");
        bean.addUrlPatterns("/region/*");
        bean.addUrlPatterns("/like");
        bean.addUrlPatterns("/like/action/*");
        bean.addUrlPatterns("/emailHistory");
        bean.addUrlPatterns("/emailHistory/*");

        return bean;
    }

}
