package com.vintage.infrastructure.configuration;

import org.springdoc.core.converters.models.MonetaryAmount;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public SpringDocUtils springDocUtils() {
        return SpringDocUtils.getConfig()
                .replaceWithClass(MonetaryAmount.class, CustomMonetaryAmount.class);
    }
}