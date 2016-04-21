package net.bilberry.config;

import net.bilberry.spring.SolrQueryFromSearchArgumentsMapResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableSpringDataWebSupport
@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        SolrQueryFromSearchArgumentsMapResolver personResolver = new SolrQueryFromSearchArgumentsMapResolver();
        argumentResolvers.add(personResolver);
    }
}
