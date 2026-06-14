package br.com.oboticariorevenda.oboticario_revenda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.oboticariorevenda.oboticario_revenda.model.SocialAnalytics;

@Configuration
public class SocialAnalyticsConfig {
    @Bean
    SocialAnalytics createSocialAnalyticsBean() {
        return new SocialAnalytics();
    }
}
