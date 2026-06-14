package br.com.oboticariorevenda.oboticario_revenda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.oboticariorevenda.oboticario_revenda.model.SocialAnalytics;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private SocialAnalytics socialAnalytics;

    public AnalyticsController(SocialAnalytics socialAnalytics) {
        this.socialAnalytics = socialAnalytics;
    }

    @PostMapping(value = "/instagram", consumes = "*/*")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void instagramClickCount() {
        socialAnalytics.instagramClicksAdd();
    }
    
    @PostMapping(value = "/whatsapp", consumes = "*/*")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void whatsappClickCount() {
        socialAnalytics.whatsappClicksAdd();
    }
}
