package pl.qbala.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.qbala.converter.LocalDateConverter;

@Configuration
public class FormaterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(getLocalDateConverter());
    }

    @Bean
    public LocalDateConverter getLocalDateConverter() {
        return new LocalDateConverter();
    }

}
